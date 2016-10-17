package com.deya.wcm.services.control.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.control.SiteServerBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

/**
 *  服务器管理逻辑处理类  js调用.
 * <p>Title: CicroDate</p>
 * <p>Description: 服务器管理逻辑处理类  js调用</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class SiteServerRPC {

	
	/**
     * 得到所有网站服务器列表 -- 按分页
     * @param 
     * @return List
     * */
	public static List<SiteServerBean> getSiteServerListByPage(Map<String,String> m){
		return SiteServerManager.getSiteServerListByPage(m);
	}
	
	/**
	 * 得到所有服务器信息列表
	 * @return List<SiteServerBean>
	 */
	public static List<SiteServerBean> getServerList(){
		return SiteServerManager.getServerList(); 
	}
	
	/**
     * 插入站群服务器信息
     * @param SiteServerBean ssb
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean insertSiteServer(SiteServerBean ssb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteServerManager.insertSiteServer(ssb, stl)){
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	/**
	 * 根据ID得到服务器信息
	 * @param String id
	 * @return SiteServerBean
	 */
	public static SiteServerBean getServerBeanByID(String server_id){
		return SiteServerManager.getServerBeanByID(server_id);
	}
	
	/**
     * 修改站群服务器信息
     * @param SiteGroupBean sgb
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean updateSiteServer(SiteServerBean ssb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteServerManager.updateSiteServer(ssb, stl)){
				return true;
			}else
				return false;
		}else{
			return false;
		}  
	}
	
	
	/**
     * 删除网站服务器信息
     * @param String server_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSiteServer(String server_ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteServerManager.deleteSiteServer(server_ids, stl)){
				return true;
			}else
				return false;
		}else{
			return false;
		}  
	}
	
}
