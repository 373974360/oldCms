package com.deya.wcm.dao.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.query.QueryConfBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class queryConfDao {
	
	/**
     * 得到所有查询业务列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<QueryConfBean> getAllQueryConfList()
	{
		return DBManager.queryFList("getAllQueryConfList", "");
	}
	
	/**
	 * 得到所有查询业务总数
	 * @return
	*/
	@SuppressWarnings("unchecked")
	public static String  getAllQueryConfCounts()
	{
		return DBManager.getString("getAllQueryConfCounts","");
	}
	
	
	/**
     * 根据条件得到总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getQueryConfCount(Map<String,String> m)
	{
		return DBManager.getString("getQueryConfCount", m);
	}
	
	/**
     * 根据条件得到列表
     * @param Map<String,String> m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<QueryConfBean> getQueryConfList(Map<String,String> m)
	{
		return DBManager.queryFList("getQueryConfList", m);
	}
	
	/**
	 * 根据业务ID得到对象
	 * @param conf_id
	 * @return  QueryConfBean
	 */
	public static QueryConfBean getQueryConfBeanById(int conf_id)
	{
		return (QueryConfBean)DBManager.queryFObj("getQueryConfBean", conf_id);
	}
	
	
	/**
	 * 新增查询业务
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean insertQueryConf(QueryConfBean ob,SettingLogsBean stl)
	{
		System.out.println("insertQueryConf dao ========="+ob);
		if(DBManager.insert("insert_QueryConf",ob)){
			PublicTableDAO.insertSettingLogs("添加","查询业务",ob.getConf_id()+"",stl);
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 修改查询业务
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean updateQueryConf(QueryConfBean ob,SettingLogsBean stl)
	{
		if(DBManager.update("update_QueryConf", ob))
		{
		  PublicTableDAO.insertSettingLogs("修改", "查询业务", ob.getConf_id()+"", stl);
	      return true;
		}else{
		  return false;
		}
	}
	
	/**
	 * 删除查询业务
	 * @param ob
	 * @param stl
	 * @return boolean
	 */
	public static boolean deleteQueryConf(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_QueryConf", m))
		{
			PublicTableDAO.insertSettingLogs("删除", "查询业务", m.get("conf_id"), stl);
			return true;
		}else
			return false;
	}
	
}