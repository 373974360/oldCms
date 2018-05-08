package com.deya.wcm.services.zwgk.appcatalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.appcatalog.AppCatalogBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.appcatalog.AppCatalogDAO;
import com.deya.wcm.services.cms.category.CategoryManager;

/**
 *  公开应用目录逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 公开应用目录逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class AppCatalogManager implements ISyncCatch{
	public static Map<Integer,AppCatalogBean> cata_map = new HashMap<Integer,AppCatalogBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		cata_map.clear();
		List<AppCatalogBean> l = AppCatalogDAO.getGKAppCatalogList();
		if(l != null && l.size() > 0)
		{
			for(AppCatalogBean acb : l)
			{
				cata_map.put(acb.getCata_id(), acb);
			}
		}
	}
	
	public static void reloadAppCatalog()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.zwgk.appcatalog.AppCatalogManager");
	}
	
	public static int getNewAppCatalogID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.GK_APPCATALOG_TABLE_NAME);
	}
	
	/**
	 * 根据ID得到对象
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static AppCatalogBean getAppCatalogBean(int cata_id)
	{
		if(cata_map.containsKey(cata_id))
		{
			return cata_map.get(cata_id);
		}else
		{
			AppCatalogBean acb = AppCatalogDAO.getAppCatalogBean(cata_id);
			if(acb != null)
			{
				cata_map.put(acb.getCata_id(), acb);
				return acb;
			}else
				return null;
		}
	}
	
	/**
     * 根据ID得到它的子级列表(deep+1)
     * @param int cata_id
     * @return List
     * */
	public static List<AppCatalogBean> getChildCatalogList(int cata_id)
	{
		Set<Integer> set = cata_map.keySet();
		List<AppCatalogBean> l = new ArrayList<AppCatalogBean>();
		for(int i : set){
			AppCatalogBean acb =  cata_map.get(i);
			if(acb.getParent_id() == cata_id)
			{
				l.add(acb);
			}
		}
		if( l != null && l.size() > 0)
			 Collections.sort(l,new AppCatalogComparator());
		return l;
	}
	
	/**
     * 根据cat_ID得到它所有的子级ID(deep+n)
     * @param int cat_ids
     * @return String
     * */
	public static String getAllChildCatalogIDS(int cata_id)
	{
		String ids = "";
		AppCatalogBean acb = getAppCatalogBean(cata_id);
		if(acb != null)
		{
			String tree_position = acb.getTree_position();
			Set<Integer> set = cata_map.keySet();
			for(int i : set){
				acb =  cata_map.get(i);
				if(acb.getTree_position().startsWith(tree_position))
				{
					ids += ","+acb.getCata_id();
				}
			}
			if(ids.length() > 0)
				ids = ids.substring(1);
		}
		return ids;
	}
	
	/**
     * 根据cat_ID得到它所有的子级ID(deep+n)(可用多个ID)
     * @param String cat_ids
     * @return String
     * */
	public static String getAllChildCatalogIDS(String cata_ids)
	{
		String ids = "";
		String[] tempA = cata_ids.split(",");
		for(int i=0;i<tempA.length;i++)
		{
			String temp_id = getAllChildCatalogIDS(Integer.parseInt(tempA[i]));
			if(temp_id != null && !"".equals(temp_id))
				ids += ","+temp_id;
		}
		if(ids.length() > 0)
			ids = ids.substring(1);
		
		return ids;
	}
	
	/**
	 * 插入公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean insertGKAppCatelog(AppCatalogBean acb,SettingLogsBean stl)
	{
		if(acb.getParent_id() == 0)
		{
			acb.setTree_position("$"+acb.getCata_id()+"$");
		}else
		{
			acb.setTree_position(getAppCatalogBean(acb.getParent_id()).getTree_position()+acb.getCata_id()+"$");
		}
		if(AppCatalogDAO.insertGKAppCatelog(acb))
		{			
			reloadAppCatalog();
			PublicTableDAO.insertSettingLogs("添加", "公开应用目录", acb.getCata_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 修改公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean updateGKAppCatelog(AppCatalogBean acb,SettingLogsBean stl)
	{
		if(AppCatalogDAO.updateGKAppCatelog(acb,stl))
		{
			reloadAppCatalog();
			return true;
		}else
			return false;
	}
	
	/**
	 * 保存排序
	 * @param String ids
	 * @return boolean
	 */
	public static boolean sortGKAppCatelog(String ids,SettingLogsBean stl)
	{
		if(AppCatalogDAO.sortGKAppCatelog(ids,stl))
		{
			reloadAppCatalog();
			return true;
		}else
			return false;
	}
	
	/**
	 * 移动公开应用目录节点
	 * @param int parent_id
	 * @param int String ids
	 * @return boolean
	 */
	public static boolean moveGKAppCatelog(int parent_id,String ids,SettingLogsBean stl)
	{
		AppCatalogBean acb = getAppCatalogBean(parent_id);
		if(acb != null)
		{
			String tree_position = acb.getTree_position();
			if(ids != null && !"".equals(ids))
			{
				try{
					String[] tempA = ids.split(",");
					for(int i=0;i<tempA.length;i++)
					{					
						moveGKAppCatelogHandl(tempA[i],parent_id+"",tree_position);
					}
					PublicTableDAO.insertSettingLogs("移动","公开应用目录",ids,stl);
					reloadAppCatalog();
					return true;
				}catch(Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}else
			return false;
	}
	
	public static void moveGKAppCatelogHandl(String cata_id,String parent_id,String parent_tree_position)
	{
		String position = parent_tree_position+cata_id+"$";
		Map<String,String> new_m = new HashMap<String,String>();
		new_m.put("cata_id", cata_id);
		new_m.put("parent_id", parent_id);
		new_m.put("tree_position", position);
		if(AppCatalogDAO.moveGKAppCatelog(new_m))
		{
			List<AppCatalogBean> l = getChildCatalogList(Integer.parseInt(cata_id));
			if(l != null && l.size() > 0)
			{
				for(AppCatalogBean acb : l)
				{
					moveGKAppCatelogHandl(acb.getCata_id()+"",cata_id,position);
				}
			}
		}
	}
	
	/**
	 * 修改目录sql
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean updateGKAppCatelogSQL(String sql,String cata_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("c_sql", sql);
		m.put("cata_id", cata_id);
		if(AppCatalogDAO.updateGKAppCatelogSQL(m))
		{
			reloadAppCatalog();
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean deleteGKAppCatelog(String cata_ids,SettingLogsBean stl)
	{
		String all_child_ids = getAllChildCatalogIDS(cata_ids);
		if(AppCatalogDAO.deleteGKAppCatelog(all_child_ids,stl))
		{
			//删除规则表
			AppCatalogDAO.deleteAppCateRegu(cata_ids);
			reloadAppCatalog();
			return true;
		}else
			return false;
	}
	
	/**
     * 拷贝共享目录
     * @param int parent_id
     * @param String selected_cat_ids 要拷贝的节点ID
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean copyShareCategory(int parent_id,String selected_cat_ids,SettingLogsBean stl)
	{
		AppCatalogBean parent_acb = getAppCatalogBean(parent_id);
		String[] tempA = selected_cat_ids.split(",");
		try{
			for(int i=0;i<tempA.length;i++)
			{
				//此处是从共享目录中选择节点，所以站点ID的参数为空
				CategoryBean cgb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(tempA[i]),"");
				if(cgb != null)
				{
					copyCatalogHandl(parent_id,parent_acb.getTree_position(),cgb);
				}
			}
			PublicTableDAO.insertSettingLogs("拷贝目录","公开应用目录",selected_cat_ids,stl);
			reloadAppCatalog();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static void copyCatalogHandl(int parent_id,String parent_tree_position,CategoryBean cgb)
	{
		AppCatalogBean acb = new AppCatalogBean();
		int id = getNewAppCatalogID();
		acb.setId(id);
		acb.setCata_id(id);
		acb.setTree_position(parent_tree_position+id+"$");
		acb.setParent_id(parent_id);		
		acb.setCat_sort(cgb.getCat_sort());
		acb.setCata_cname(cgb.getCat_cname());
		acb.setCat_description(cgb.getCat_description());
		acb.setCat_keywords(cgb.getCat_keywords());
		acb.setCat_memo(cgb.getCat_memo());
		acb.setTemplate_index(cgb.getTemplate_index());
		acb.setTemplate_list(cgb.getTemplate_list());
		if(AppCatalogDAO.insertGKAppCatelog(acb))
		{
			//插入子节点
			List<CategoryBean> child_list = CategoryManager.getChildCategoryList(cgb.getCat_id(),"");//此处是从共享目录中选择节点，所以站点ID的参数为空
			if(child_list != null && child_list.size() > 0)
			{
				for(CategoryBean cb : child_list)
				{
					copyCatalogHandl(id,acb.getTree_position(),cb);
				}
			}
		}
		
	}
	
	static class AppCatalogComparator implements Comparator<Object>{
		public int compare(Object o1, Object o2) {
		    
			AppCatalogBean cgb1 = (AppCatalogBean) o1;
			AppCatalogBean cgb2 = (AppCatalogBean) o2;
		    if (cgb1.getCat_sort() > cgb2.getCat_sort()) {
		     return 1;
		    } else {
		     if (cgb1.getCat_sort() == cgb2.getCat_sort()) {
		      return 0;
		     } else {
		      return -1;
		     }
		    }
		}
	}
	
	/**
     * 根据目录ID得到应用目录树
     * @param cata_id
     * @return String
     * */
	public static String getAppCatalogTree(int cata_id)
	{
		String json_str = "";
		AppCatalogBean acb = getAppCatalogBean(cata_id);
		if(acb != null)
		{
			json_str = "[{\"id\":"+acb.getCata_id()+",\"text\":\""+acb.getCata_cname()+"\",\"attributes\":{\"url\":\""
				+acb.getJump_url()+"\",\"is_mutilpage\":\""+acb.getIs_mutilpage()+"\"}";
			List<AppCatalogBean> l = getChildCatalogList(cata_id);
			if(l != null && l.size() > 0)
			{
				json_str += ",\"children\":["+getAppCatalogTreeHandl(l)+"]";
			}
			json_str += "}]";
		}else
		{
			json_str = "[]";
		}
		return json_str;
	}
	
	public static String getAppCatalogTreeHandl(List<AppCatalogBean> l)
	{
		String json_str = "";
		for(AppCatalogBean acb : l)
		{
			json_str += ",{\"id\":"+acb.getCata_id()+",\"text\":\""+acb.getCata_cname()+"\",\"attributes\":{\"url\":\""
				+acb.getJump_url()+"\",\"is_mutilpage\":\""+acb.getIs_mutilpage()+"\"}";
			List<AppCatalogBean> child_l = getChildCatalogList(acb.getCata_id());
			if(child_l != null && child_l.size() > 0)
			{
				json_str += ",\"state\":'closed',\"children\":["+getAppCatalogTreeHandl(child_l)+"]";
			}
			json_str += "}";
		}
		if(json_str != null && json_str.length() > 0)
			json_str = json_str.substring(1);
		return json_str;
	}
}
