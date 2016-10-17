package com.deya.wcm.dao.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.query.QueryItemBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class queryItemDao {
		
	/**
     * 根据条件得到总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getQueryItemCount(Map<String,String> m)
	{
		return DBManager.getString("getQueryItemCount",m);
	}
	
	/**
     * 根据条件得到列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<QueryItemBean> getQueryItemList(Map<String,String> m)
	{
		return DBManager.queryFList("getQueryItemList",m);
	}
	
	public static String getQueryItemCounts(Map m)
	{
		return DBManager.getString("getQueryItemCounts",m);
	}
	/**
     * 根据条件得到列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<QueryItemBean> getQueryItemLists(Map m)
	{
		return DBManager.queryFList("getQueryItemLists",m);
	}
	
	
	/**
	 * 根据业务ID得到对象
	 * @param conf_id
	 * @return  QueryItemBean
	 */
	public static  List<QueryItemBean> getQueryItemBeans(Map m)
	{
		return DBManager.queryFList("getQueryItemBeans",m);
	}

	/**
	 * 新增查询信息
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean insertQueryItem(int conf_id,QueryItemBean ob,SettingLogsBean stl)
	{   
		if(DBManager.insert("insert_QueryItem", ob))
		{
			PublicTableDAO.insertSettingLogs("添加","添加信息",ob.getItem_id()+"",stl);
			return true;
		}else
			return false;
	}
	/**
	 * 修改查询信息
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateQueryItem(QueryItemBean ob,SettingLogsBean stl)
	{
		if(DBManager.update("update_QueryItem",ob))
		{
		  PublicTableDAO.insertSettingLogs("修改", "修改信息", ob.getItem_id()+"",stl);
	      return true;
		}else{
		  return false;
		}
	}
	/**
	 * 删除查询信息
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean deleteQueryItemByConfId(int conf_id,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_QueryItem_ByConfId",conf_id))
		{
			PublicTableDAO.insertSettingLogs("删除", "查询信息", conf_id+"", stl);
			return true;
		}else
			return false;
	}
	/**
	 * 删除查询信息
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean deleteQueryItemByItem(Map m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_QueryItem", m))
		{
			PublicTableDAO.insertSettingLogs("删除", "查询信息", m.get("item_id")+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 删除查询信息
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean deleteQueryItemByConf_id(int conf_id,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_QueryItem_ByConfId", conf_id))
		{
			PublicTableDAO.insertSettingLogs("删除", "查询信息",conf_id+"", stl);
			return true;
		}else
			return false;
	}
	
	
	public static String getQueryCellCount(String conf_id)
	{
		return DBManager.getString("getQueryCellCount", conf_id);
	}
	
	public static List<QueryItemBean> getQueryListBrowser(Map m)
	{
		return DBManager.queryFList("getQueryList_Browser",m);
	}
	
	public static String getQueryListCountBrowser(Map m)
	{
		return DBManager.getString("getQueryListCount_Browser",m);
	}
	
	public static void main(String[] args){
		Map m =new HashMap();
		m.put("con", "from cs_dz_cx_24 where 1=1 and item_2='1' and item_3='90'");
		m.put("page_size", "10");
		m.put("start_num", "0");
		System.out.println(getQueryListBrowser(m));
	}
}