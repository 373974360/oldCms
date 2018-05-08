package com.deya.wcm.services.control.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.control.SiteServerBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.control.SiteServerDAO;
import com.deya.wcm.server.ServerManager;
import com.deya.wcm.services.control.site.SiteManager;

/**
 *  网站群服务器管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 网站群服务器管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteServerManager implements ISyncCatch{
	private static List<SiteServerBean> server_list = new ArrayList<SiteServerBean>();
	public static boolean IS_MUTILPUBLISHSERVER = false;//是否是多台服务器
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	@SuppressWarnings({ "unchecked"})
	public static void reloadCatchHandl()
	{
		server_list.clear();
		server_list = SiteServerDAO.getSiteServerList();
		
		IS_MUTILPUBLISHSERVER = isMultiPublishServer();		
	}
	
	/**
	 * 初始加载站群信息
	 * 
	 * @param
	 * @return
	 */	
	public static void reloadServerList()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.control.server.SiteServerManager");
	}
	
	/**
     * 得到所有网站服务器列表 -- 按分页
     * @param 
     * @return List
     * */
	public static List<SiteServerBean> getSiteServerListByPage(Map<String,String> m){
         return SiteServerDAO.getSiteServerListByPage(m);
	}
	
	
	/**
	 * 得到服务器信息列表
	 * 
	 * @param String 
	 *            id
	 * @return List
	 */
	public static List<SiteServerBean> getServerList()
	{
		return server_list;
	}
	
	/**
	 * 根据类型ID得到该类型的服务器列表
	 * 
	 * @param int 
	 *            server_type
	 * @return List
	 */
	public static List<SiteServerBean> getServerListBySType(int server_type)
	{
		List<SiteServerBean> l = new ArrayList<SiteServerBean>();
		if(server_list != null && server_list.size() > 0)
		{
			for(int i=0;i<server_list.size();i++)
			{
				if(server_type == server_list.get(i).getServer_type())
					l.add(server_list.get(i));
			}
		}
		return l;
	}
	
	/**
	 * 得到站群是否有多个发布服务器（用于判断发布服务器的个数，如果是多个，不使用rmi调用）
	 *  
	 * @return boolean
	 */
	public static boolean isMultiPublishServer()
	{
		List<SiteServerBean> l = getServerListBySType(2);
		if( l!= null && l.size() > 0)
		{
			return l.size() != 1;
		}
		return false;
	}
	
	/**
	 * 得到素材库服务器ip
	 * 
	 *
	 * @return String
	 */
	public static String getResourceServerIP()
	{
		List<SiteServerBean> l = getServerListBySType(4);
		if(l != null && l.size() > 0)
		{
			return l.get(0).getServer_ip();
		}else
			return "";
	}
	
	/**
	 * 根据ID得到服务器信息
	 * 
	 * @param String 
	 *            id
	 * @return SiteServerBean
	 */
	public static SiteServerBean getServerBeanByID(String server_id)
	{
		SiteServerBean sb = new SiteServerBean();
		if(server_list != null && server_list.size() > 0)
		{
			for(int i=0;i<server_list.size();i++)
			{
				if(server_id.equals(server_list.get(i).getServer_id()+""))
					sb = server_list.get(i);
			}
			
		}
		return sb;
	}
	
	/**
     * 插入站群服务器信息
     * @param SiteServerBean ssb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSiteServer(SiteServerBean ssb,SettingLogsBean stl)
	{
		if(SiteServerDAO.insertSiteServer(ssb, stl))
		{
			reloadServerList();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改站群服务器信息
     * @param SiteGroupBean sgb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSiteServer(SiteServerBean ssb,SettingLogsBean stl)
	{
		if(SiteServerDAO.updateSiteServer(ssb, stl))
		{
			reloadServerList();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除网站服务器信息
     * @param String server_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSiteServer(String server_ids,SettingLogsBean stl)
	{
		if(SiteServerDAO.deleteSiteServer(server_ids, stl))
		{
			reloadServerList();
			return true;
		}else
			return false;
	}
	
	/**
     * 判断站点是否在同一服务器上（站点所属服务器IP和本机IP是否一致）
     * @param String site_id
     * @return boolean
     * */
	public static boolean isTheSameServer(String site_id)
	{
		SiteBean si = SiteManager.getSiteBeanBySiteID(site_id);
		if(si == null)
			return true;
		
		return SiteServerManager.getServerBeanByID(si.getServer_id()+"").getServer_ip().equals(ServerManager.LOCAL_IP);
	}
	
	public static void main(String[] args)
	{
		//insertSiteServer();
		//System.out.println(server_list);
		//deleteSiteServer("1",new SettingLogsBean());
		reloadServerList();
	}
	
	public static void insertSiteServer()
	{
		SiteServerBean ssb = new SiteServerBean();
		ssb.setServer_id(1);
		ssb.setServer_ip("192.168.12.18");
		ssb.setServer_memo("resource");
		ssb.setServer_name("resource");
		ssb.setServer_type(4);
		insertSiteServer(ssb,new SettingLogsBean());
		//updateSiteServer(ssb,new SettingLogsBean());
		
	}
	
}
