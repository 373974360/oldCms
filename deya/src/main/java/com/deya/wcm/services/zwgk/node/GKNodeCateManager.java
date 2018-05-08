package com.deya.wcm.services.zwgk.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.node.GKNodeCategory;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.node.GKNodeCategoryDAO;

/**
 *  场景式服务主题（导航）逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 场景式服务主题（导航）逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class GKNodeCateManager implements ISyncCatch{
	private static Map<Integer,GKNodeCategory> gnc_map = new HashMap<Integer,GKNodeCategory>();
	private static int ROOT_ID = 0;
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		gnc_map.clear();
		List<GKNodeCategory> l = GKNodeCategoryDAO.getAllNodeCategoryList();
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				gnc_map.put(l.get(i).getNodcat_id(), l.get(i));
			}
		}
	}
	
	public static void reloadGKNodeCategory()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.zwgk.node.GKNodeCateManager");
	}
	
	/**
	 * 得到节点分类ID
	 * @return int
	 */
	public static int getNewNodCatID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.GK_NODE_CATEGORY);
	}
	
	/**
	 * 根据分类ID得到分类对象
	 * @param id 分类ID
	 * @return GKNodeCategory
	 */
	public static GKNodeCategory getNodeCategoryBean(int id)
	{
		if(id == ROOT_ID)
		{
			GKNodeCategory gnc = new GKNodeCategory();
			gnc.setNodcat_id(ROOT_ID);
			gnc.setNod_position("$"+ROOT_ID+"$");
			return gnc;
		}
		if(gnc_map.containsKey(id))
		{
			return gnc_map.get(id);
		}else
		{
			GKNodeCategory gnc = GKNodeCategoryDAO.getNodeCategoryBean(id);
			if(gnc != null)
			{
				gnc_map.put(id, gnc);
				return gnc;
			}
			return null;
		}
	}
	
	/**
	 * 插入分类节点
	 * @param GKNodeCategory gnc
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertGKNodeCategory(GKNodeCategory gnc,SettingLogsBean stl)
	{
		if(gnc.getParent_id() == ROOT_ID)
		{
			gnc.setNod_position("$0$"+gnc.getNodcat_id()+"$");
		}
		else
		{
			GKNodeCategory parentB = getNodeCategoryBean(gnc.getParent_id());
			gnc.setNod_position(parentB.getNod_position()+gnc.getNodcat_id()+"$");			
		}
		
		if(GKNodeCategoryDAO.insertGKNodeCategory(gnc, stl))
		{
			reloadGKNodeCategory();
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改分类节点
	 * @param GKNodeCategory gnc
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNodeCategory(GKNodeCategory gnc,SettingLogsBean stl)
	{
		if(GKNodeCategoryDAO.updateGKNodeCategory(gnc, stl))
		{
			reloadGKNodeCategory();
			return true;
		}else
			return false;
	}
	
	/**
	 * 移动节点分类
	 * @param String nodcat_ids
	 * @param int parent_id
	 * @return boolean
	 */
	public static boolean moveGKNodeCategory(String nodcat_ids,int parent_id,SettingLogsBean stl)
	{
		GKNodeCategory gnc = getNodeCategoryBean(parent_id);
		if(gnc != null)
		{			
			String[] tempA = nodcat_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				moveGKNodeCategoryHandl(Integer.parseInt(tempA[i]),parent_id,gnc.getNod_position());
			}		
			reloadGKNodeCategory();	
			return true;
		}else
		{
			return false;
		}
	}
	
	public static void moveGKNodeCategoryHandl(int nodcat_id,int parent_id,String nod_position)
	{
		String position = nod_position+nodcat_id+"$";
		Map<String,String> m = new HashMap<String,String>();
		m.put("parent_id", parent_id+"");
		m.put("nodcat_id", nodcat_id+"");
		m.put("nod_position", position);
		if(GKNodeCategoryDAO.moveGKNodeCategory(m))
		{
			List<GKNodeCategory> l =  getChildCategoryList(nodcat_id);
			if(l != null && l.size() > 0)
			{
				for(int i=0;i<l.size();i++)
				{
					moveGKNodeCategoryHandl(l.get(i).getNodcat_id(),nodcat_id,position);
				}
			}
		}
	}
	

	/**
	 * 保存分类节点排序
	 * @param String nodcat_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean sortGKNodeCategory(String nodcat_ids,SettingLogsBean stl)
	{
		if(GKNodeCategoryDAO.sortGKNodeCategory(nodcat_ids, stl))
		{
			reloadGKNodeCategory();
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除分类节点
	 * @param String nodcat_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteGKNodeCategory(String nodcat_ids,SettingLogsBean stl)
	{
		String child_ids = getAllChildCategoryIDS(nodcat_ids);
		if(child_ids != null && !"".equals(child_ids))
			nodcat_ids = nodcat_ids+","+child_ids;
		if(GKNodeCategoryDAO.deleteGKNodeCategory(nodcat_ids, stl))
		{
			reloadGKNodeCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 得到公开分类树字符串,从根节点开始
     * @return String
     * */
	public static String getGKNodeCategroyJSONROOTTreeStr()
	{
		String json_str = "";
		json_str = "[{\"id\":"+ROOT_ID+",\"text\":\"公开节点分类\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
		String child_str = getGKNodeCategroyJSONTreeStr();
		
		if(child_str != null && child_str.length() > 3)
			json_str += ",\"children\":"+child_str+"";
		
		json_str += "}]";
		return json_str;
	}
	
	
	/**
     * 得到公开分类树字符串
     * @return String
     * */
	public static String getGKNodeCategroyJSONTreeStr()
	{
		String json_str = "";
		json_str = "[";
		List<GKNodeCategory> list = getChildCategoryList(ROOT_ID);
		if(list != null && list.size() > 0)
		{		
			json_str += getGKNodeCategroyJSONTreeStrHandl(list);
		}
		json_str += "]";
		return json_str;
	}
	
	/**
     * 根据目录列表得目录树节字符串
     * @param CategoryBean cbg
     * @return String
     * */
	public static String getGKNodeCategroyJSONTreeStrHandl(List<GKNodeCategory> child_list)
	{
		String json_str = "";
		if(child_list != null && child_list.size() > 0)
		{
			for(int i=0;i<child_list.size();i++)
			{				
				json_str += "{";
				List<GKNodeCategory> child_m_list = getChildCategoryList(child_list.get(i).getNodcat_id());
				if(child_m_list != null && child_m_list.size() > 0)
				{
					json_str += "\"id\":"+child_list.get(i).getNodcat_id()+",\"text\":\""+child_list.get(i).getNodcat_name()+"\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
					//json_str += ",\"state\":'closed'";
					json_str += ",\"children\":["+getGKNodeCategroyJSONTreeStrHandl(child_m_list)+"]";
				}else
					json_str += "\"id\":"+child_list.get(i).getNodcat_id()+",\"text\":\""+child_list.get(i).getNodcat_name()+"\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
				json_str += "}";
				if(i+1 != child_list.size())
					json_str += ",";				
			}
		}
		return json_str;
	}
	
	/**
     * 根据nodcat_id得公开节点的树
     * @param int nodcat_id
     * @return String
     * */
	public static String getGKNodeTreebyCateID(int nodcat_id)
	{
		String json_str = "";
		json_str = "[";
		List<GKNodeCategory> list = getChildCategoryList(nodcat_id);
		if(list != null && list.size() > 0)
		{		
			json_str += getGKNodeTreebyCateIDHandl(list);
		}
		json_str += "]";
		return json_str;
	}
	
	/**
     * 根据目录列表得目录树节字符串
     * @param CategoryBean cbg
     * @return String
     * */
	public static String getGKNodeTreebyCateIDHandl(List<GKNodeCategory> child_list)
	{
		String json_str = "";
		if(child_list != null && child_list.size() > 0)
		{
			for(int i=0;i<child_list.size();i++)
			{				
				json_str += "{";
				String node_str = GKNodeManager.getGKNodeJSONStrByCateID(child_list.get(i).getNodcat_id());
				//System.out.println(node_str);
				List<GKNodeCategory> child_m_list = getChildCategoryList(child_list.get(i).getNodcat_id());
				if(child_m_list != null && child_m_list.size() > 0)
				{
					json_str += "\"id\":"+child_list.get(i).getNodcat_id()+",\"text\":\""+child_list.get(i).getNodcat_name()+"\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
					//json_str += ",\"state\":'closed'";
					json_str += ",\"children\":["+getGKNodeTreebyCateIDHandl(child_m_list);
					if(node_str != null && !"".equals(node_str))
						json_str += ","+node_str;
					json_str += "]";
				}else
				{
					json_str += "\"id\":"+child_list.get(i).getNodcat_id()+",\"text\":\""+child_list.get(i).getNodcat_name()+"\",\"attributes\":{\"url\":\"\",\"handl\":\"\"}";
					if(node_str != null && !"".equals(node_str))
						json_str += ",\"children\":["+node_str+"]";
				}
				json_str += "}";
				if(i+1 != child_list.size())
					json_str += ",";				
			}
		}
		return json_str;
	}
	
	/**
     * 根据ID判断分类是否有叶节点,用于删除时判断
     * @param int nodcat_id
     * @return boolean
     * */
	public static boolean hasChildNodeByCategory(int nodcat_id)
	{
		Set<Integer> set = gnc_map.keySet();
		List<GKNodeCategory> list = new ArrayList<GKNodeCategory>();
		for(int i : set){
			GKNodeCategory gnc = gnc_map.get(i);
			if(gnc.getParent_id() == nodcat_id)
				return true;
		}
		return false;	
	}
	
	/**
     * 根据分类ID得到它有层级的所有子分类对象，并包含所有节点ID(用于在前台循环展示所有的部门)
     * @param int nodcat_id
     * @return boolean
     * */
	public static List<GKNodeCategory> getNodeListForCatID(int nodcat_id)
	{
		List<GKNodeCategory> cat_list = getChildCategoryList(nodcat_id);
		if(cat_list != null && cat_list.size() > 0)
		{
			for(GKNodeCategory cat_bean : cat_list)
			{
				cat_bean.setNode_list(GKNodeManager.getGKNodeListByCateID(cat_bean.getNodcat_id()));
				cat_bean.setClass_list(getNodeListForCatID(cat_bean.getNodcat_id()));
			}
		}
		return cat_list;
	}
	
	/**
     * 根据ID得到它的子级列表(deep+1)
     * @param int nodcat_id
     * @return List
     * */
	public static List<GKNodeCategory> getChildCategoryList(int nodcat_id)
	{
		Set<Integer> set = gnc_map.keySet();
		List<GKNodeCategory> list = new ArrayList<GKNodeCategory>();
		for(int i : set){
			GKNodeCategory gnc = gnc_map.get(i);
			if(gnc.getParent_id() == nodcat_id && gnc.getNodcat_id() != nodcat_id)
				list.add(gnc_map.get(i));
		}
		if(list != null && list.size() > 0)
			Collections.sort(list,new GKNodeCategoryComparator());
		return list;
	}
	
	/**
     * 根据cat_ID得到它所有的子级ID(deep+n)(可用多个ID)
     * @param String nodcat_ids
     * @return String
     * */
	public static String getAllChildCategoryIDS(String nodcat_ids)
	{
		String ids = "";
		String[] tempA = nodcat_ids.split(",");
		for(int i=0;i<tempA.length;i++)
		{
			String c_ids = getAllChildCategoryIDS(Integer.parseInt(tempA[i]));
			if(c_ids != null && !"".equals(c_ids))
				ids += ","+c_ids;
		}
		if(ids.length() > 0)
			ids = ids.substring(1);
		
		return ids;
	}
	
	/**
     * 根据nodcat_id得到它所有的子级nodcat_id(deep+n)
     * @param int nodcat_id
     * @return String
     * */
	public static String getAllChildCategoryIDS(int nodcat_id)
	{
		GKNodeCategory gnc = getNodeCategoryBean(nodcat_id);
		if(gnc != null)
		{
			String cat_position = gnc.getNod_position();
			Set<Integer> set = gnc_map.keySet();
			String cat_ids = "";
			for(int i : set){
				gnc = gnc_map.get(i);
				if(gnc.getNod_position().startsWith(cat_position) && !cat_position.equals(gnc.getNod_position()))
					cat_ids += ","+gnc.getNodcat_id();
			}			
			if(cat_ids.length() > 0)
				cat_ids = cat_ids.substring(1);
			return cat_ids;
		}else
			return null;
	}
	
	
	
	static class GKNodeCategoryComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			GKNodeCategory cgb1 = (GKNodeCategory) o1;
			GKNodeCategory cgb2 = (GKNodeCategory) o2;
		    if (cgb1.getSort_id() > cgb2.getSort_id()) {
		     return 1;
		    } else {
		     if (cgb1.getSort_id() == cgb2.getSort_id()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(getGKNodeTreebyCateID(0));
		//deleteGKNodeCategory("3",new SettingLogsBean());
	}
}
