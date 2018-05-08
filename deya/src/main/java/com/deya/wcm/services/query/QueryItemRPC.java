package com.deya.wcm.services.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.query.QueryItemBean;
import com.deya.wcm.services.Log.LogManager;

public class QueryItemRPC {

	/**
     * 根据条件得到查询信息总数
     * @param Map<String,String> m
     * @return String
     * */
	public static List<List<QueryItemBean>> getQueryItemValueList(String conf_id,int page_size)
	{
		return QueryItemManager.getQueryItemValueList(conf_id,page_size);
	} 
	/**
     * 根据条件得到查询信息总数
     * @param Map<String,String> m
     * @return String
     * */
	public static String getQueryItemCount(Map<String,String> m)
	{
		return QueryItemManager.getQueryItemCount(m);
	}
	
	
	/**
     * 根据条件得到查询信息总数
     * @param Map<String,String> m
     * @return String
     * */
	public static List<QueryItemBean> getQueryItemLists(Map m)
	{
		return QueryItemManager.getQueryItemLists(m);
	}
	
	public static String getQueryItemCounts(Map m)
	{
		return QueryItemManager.getQueryItemCounts(m);
	}
	
	/**
	 * 根据信息ID返回对象
	 * @param Model_id
	 * @return Bean
	*/ 
	public static List<QueryItemBean> getQueryItemBeans(int item_id,int conf_id)
	{ 
		return QueryItemManager.getQueryItemBeans(item_id,conf_id);
	}

	/**
     * 新增信息
     * @param QueryItemBean ob
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean insertQueryItemByConf_id(int conf_id,List<QueryItemBean> l,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return QueryItemManager.insertQueryItemByConf_id(conf_id,l,stl);
		}else
			return false;
	}

	/**
     * 修改信息
     * @param QueryItemBean ob
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean updateQueryItem(List<QueryItemBean> l,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
			return QueryItemManager.updateQueryItem(l,stl);
		}else
			return false;
	}
	/**
	 * 删除信息
	 * @param String conf_ids
	 * @param request
	 * @return
	 */
	public static boolean deleteQueryItem(Map m,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
		    return QueryItemManager.deleteQueryItem(m,stl);
		}else{
		    return false;
		}
	}
	
	/**
	 * 写Excle信息
	 * @param String conf_ids
	 * @param request
	 * @return
	 */
	public static boolean writeExcelItems(String file_name,int conf_id,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(null !=stl)
		{
		    return QueryItemManager.writeExcel(file_name,conf_id,site_id,stl);
		}else{
		    return false;
		}
	}
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		Map m = new HashMap();
		m.put("conf_id", "24");
		m.put("site_id", "HIWCMdemo");
		System.out.println(getQueryItemCount(m));
	}
}