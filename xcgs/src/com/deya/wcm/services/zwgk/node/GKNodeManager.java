package com.deya.wcm.services.zwgk.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.node.GKNodeBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.node.GKNodeDAO;
import com.deya.wcm.services.cms.category.CategoryManager;

/**
 *  公开节点逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 公开节点逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class GKNodeManager implements ISyncCatch{
	private static Map<String,GKNodeBean> node_map = new HashMap<String,GKNodeBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		node_map.clear();
		List<GKNodeBean> l = GKNodeDAO.getAllGKNodeList();
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				node_map.put(l.get(i).getNode_id(), l.get(i));
			}
		}
	}
	
	public static void reloadGKNode()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.zwgk.node.GKNodeManager");
	}
	
	/**
	 * 返回所有节点列表
	 * @param 
	 * @return List
	 */
	public static List<GKNodeBean> getAllGKNodeList()
	{
		List<GKNodeBean> l = new ArrayList<GKNodeBean>();
		Set<String> s = node_map.keySet();
		if(s != null && s.size() > 0)
		{
			for(String i : s)
			{
				l.add(node_map.get(i));
			}
		}
		return l;
	}
	
	/**
	 * 根据分类ID得到节点列表
	 * @param int nodcat_id
	 * @return List
	 */
	public static List<GKNodeBean> getGKNodeListByCateID(int nodcat_id)
	{
		List<GKNodeBean> l = new ArrayList<GKNodeBean>();	
		Set<String> s = node_map.keySet();
		for(String i : s)
		{
			GKNodeBean gnb = node_map.get(i);
			if(nodcat_id == gnb.getNodcat_id())
			{
				l.add(gnb);
			}
		}
		if(l != null && l.size() > 0)
			Collections.sort(l,new GKNodeComparator());
		return l;
	}
	
	/**
	 * 根据分类ID得到节点json字符串
	 * @param int nodcat_id
	 * @return String
	 */
	public static String getGKNodeJSONStrByCateID(int nodcat_id)
	{
		String json_str = "";
		List<GKNodeBean> l = getGKNodeListByCateID(nodcat_id);
		
		if(l != null && l.size() > 0)
		{
			
			int tree_node_id = Integer.parseInt(nodcat_id+""+10000);//因为节点ID是字符串的，树中不支持，所以这里初始化一个
			for(int i=0;i<l.size();i++)
			{
				if(i > 0)
					json_str += ",";
				json_str += "{\"id\":"+tree_node_id+",\"iconCls\":\"icon-gknode\",\"text\":\""+l.get(i).getNode_name()+"\",\"attributes\":{\"t_node_id\":\""+l.get(i).getNode_id()+"\",\"url\":\"\",\"handl\":\"\"}}";
				tree_node_id ++;
			}
			
		}
		return json_str;
	}
	
	/**
	 * 根据分类ID判断此分类下是否有节点，用于分类删除判断
	 * @param String nodcat_id
	 * @return boolean
	 */
	public static boolean hasNodeByCatID(int nodcat_id)
	{
		Set<String> s = node_map.keySet();
		for(String i : s)
		{
			GKNodeBean gnb = node_map.get(i);
			if(nodcat_id == gnb.getNodcat_id())
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据node_id判断是否已存在
	 * @return int
	 */
	public static boolean nodeIdIsExist(String node_id)
	{
		return node_map.containsKey(node_id);
	}

	
	/**
	 * 根据ID得到节点对象
	 * @param int id 
	 * @return GKNodeCategory
	 */
	public static GKNodeBean getGKNodeBeanByID(int id)
	{
		Set<String> s = node_map.keySet();
		for(String i : s)
		{
			GKNodeBean gnb = node_map.get(i);
			if(gnb.getId() == id)
				return gnb;
		}
		GKNodeBean gnb = GKNodeDAO.getGKNodeBeanByID(id);
		if(gnb != null)
		{
			node_map.put(gnb.getNode_id(), gnb);
			return gnb;
		}else
			return null;
	}
	
	/**
	 * 根据node_id得到节点对象
	 * @param String node_id
	 * @return GKNodeCategory
	 */
	public static GKNodeBean getGKNodeBeanByNodeID(String node_id)
	{
		if(node_map.containsKey(node_id))
		{
			return node_map.get(node_id);
		}else
		{
			GKNodeBean gnb = GKNodeDAO.getGKNodeBeanByNodeID(node_id);
			if(gnb != null)
			{
				node_map.put(node_id, gnb);
				return gnb;
			}else
				return null;
		}
	}
	
	/**
	 * 根据node_id得到节点名称
	 * @param String node_id
	 * @return String
	 */
	public static String getNodeNameByNodeID(String node_id)
	{
		GKNodeBean gb = getGKNodeBeanByNodeID(node_id);
		if(gb != null)
			return gb.getNode_name();
		else
			return "";
	}
	
	/**
	 * 根据多个公开节点得到它的公开名称
	 * @param int cata_id
	 * @return List
	 */
	public static String getMutilNodeNames(String site_ids)
	{
		String names = "";
		if(site_ids != null && !"".equals(site_ids))
		{
			String[] tempA = site_ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				String s = GKNodeManager.getNodeNameByNodeID(tempA[i]);
				if(s != null && !"".equals(s))
					names += ","+s;
			}
			if(names != null && !"".equals(names))
				names = names.substring(1);
		}
		return names;
	}
	
	/**
	 * 插入节点
	 * @param GKNodeBean gbn
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertGKNode(GKNodeBean gbn,SettingLogsBean stl)
	{
		gbn.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.GK_NODE));
		if(GKNodeDAO.insertGKNode(gbn, stl))
		{
			CategoryManager.insertGKDefaultCategory(gbn.getNode_id());
			reloadGKNode();
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改节点
	 * @param GKNodeBean gbn
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNode(GKNodeBean gbn,SettingLogsBean stl)
	{
		if(GKNodeDAO.updateGKNode(gbn, stl))
		{
			reloadGKNode();
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改节点(用于公开节点中的资料信息修改)
	 * @param GKNodeBean gbn
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNodeSimple(GKNodeBean gbn,SettingLogsBean stl)
	{
		if(GKNodeDAO.updateGKNodeSimple(gbn, stl))
		{
			reloadGKNode();
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改节点状态
	 * @param String ids
	 * @param int node_status
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateGKNodeStatus(String ids,int node_status,SettingLogsBean stl)
	{
		if(GKNodeDAO.updateGKNodeStatus(ids,node_status, stl))
		{
			reloadGKNode();
			return true;
		}else
			return false;
	}
	
	/**
	 * 移动节点
	 * @param String ids
	 * @param int nodcat_id
	 * @return boolean
	 */
	public static boolean moveGKNode(String ids,int nodcat_id,SettingLogsBean stl)
	{
		if(GKNodeDAO.moveGKNode(ids,nodcat_id, stl))
		{
			reloadGKNode();
			return true;
		}else
			return false;
	}
	
	/**
	 * 保存节点排序
	 * @param String ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean sortGKNode(String ids,SettingLogsBean stl)
	{
		if(GKNodeDAO.sortGKNode(ids,stl))
		{
			reloadGKNode();
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除节点
	 * @param String ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteGKNode(String ids,SettingLogsBean stl)
	{
		if(GKNodeDAO.deleteGKNode(ids,stl))
		{
			reloadGKNode();
			return true;
		}else
			return false;
	}
	
	static class GKNodeComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			GKNodeBean cgb1 = (GKNodeBean) o1;
			GKNodeBean cgb2 = (GKNodeBean) o2;
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
	
	public static void main(String args[])
	{
		System.out.println(getGKNodeJSONStrByCateID(0));
	}
}
