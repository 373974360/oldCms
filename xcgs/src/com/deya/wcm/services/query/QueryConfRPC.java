package com.deya.wcm.services.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.deya.wcm.bean.query.QueryConfBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
 

public class QueryConfRPC {
	
	
	/**
     * 得到所有查询业务列表
     * @param 
     * @return List
     * */
	public static List<QueryConfBean> getAllQueryConfList(){
		return QueryConfManager.getAllQueryConfList();
	}
	
	/**
     * 根据条件得到查询业务总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getQueryConfCount(Map<String,String> m)
	{
		return QueryConfManager.getQueryConfCount(m);
	}
	
	/**
     * 根据条件得到查询业务总数
     * @param Map<String,String> m
     * @return String
     * */
	public static List<QueryConfBean> getQueryConfLists(Map<String,String> m)
	{
		return QueryConfManager.getQueryConfLists(m);
	}
	
	/**
	 * 根据业务ID返回对象
	 * @param Model_id
	 * @return Bean
	 */
	public static QueryConfBean getQueryConfBean(int conf_id)
	{ 
		return QueryConfManager.getQueryConfBean(conf_id);
	}
	 
	/**
     * 得到业务ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getQueryConfID()
	{
		return QueryConfManager.getQueryConfID();
	} 
	
	
	/**
     * 新增业务信息
     * @param QueryConfBean ob
     * @param String dept_ids
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean insertQueryConf(QueryConfBean ob,HttpServletRequest request)
	{
		System.out.println("insertQueryConf==="+ob);
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return QueryConfManager.insertQueryConf(ob,stl);
		}else
			return false;
	}
	
	
	/**
     * 修改业务信息
     * @param QueryConfBean ob
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean updateQueryConf(QueryConfBean ob,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return QueryConfManager.updateQueryConf(ob,stl);
		}else
			return false;
	}
	/**
	 * 删除业务信息
	 * @param String conf_ids
	 * @param request
	 * @return
	 */
	public static boolean deleteQueryConf(Map<String,String> m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
		    return QueryConfManager.deleteQueryConf(m,stl);
		}else{
		    return false;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map m= new HashMap();
		getQueryConfLists(m);
	}
}