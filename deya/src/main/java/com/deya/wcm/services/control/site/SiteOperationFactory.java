package com.deya.wcm.services.control.site;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.rmi.ISiteRmi;
import com.deya.wcm.services.control.domain.SiteDomainManager;
import com.deya.wcm.services.control.server.SiteServerManager;

/**
 *  站点操作管理工厂类
 * <p>Title: CicroDate</p>
 * <p>Description: 站点操作管理工厂类，此类为前台提供方法，需要判断服务器的IP地址，按IP来调用相应服务器上的RMI接口方法.</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteOperationFactory {	
	
	/**
	 * 根据站点ID返回该站点所在服务器对应的RMI类接口
	 * @param String site_id
	 * @return ISiteRmi
	 */
	public static ISiteRmi getSiteRMIForSiteID(String site_id)
	{
		SiteBean sb = SiteManager.getSiteBeanBySiteID(site_id);	
		if(sb != null)
		{			
			return getSiteRmigetSiteRMIForServerID(sb.getServer_id()+"");
		}else
			return null;
	}
	
	/**
	 * 根据server_id返回服务器对应的RMI类接口
	 * @param String server_id
	 * @return ISiteRmi
	 */
	public static ISiteRmi getSiteRmigetSiteRMIForServerID(String server_id)
	{
		String server_ip = SiteServerManager.getServerBeanByID(server_id).getServer_ip();	
		//System.out.println("getSiteRmigetSiteRMIForServerID-------------"+server_ip);
		return getSiteRMIForServerIP(server_ip);
	}
	
	/**
	 * 根据服务器IP返回该服务器对应的RMI类接口
	 * @param String server_ip
	 * @return ISiteRmi
	 */
	public static ISiteRmi getSiteRMIForServerIP(String server_ip)
	{
		try{			
			Context namingContext=new InitialContext();
			String path = "rmi://"+server_ip+":"+JconfigUtilContainer.bashConfig().getProperty("port", "", "rmi_config")+"/siteRmi";
			//System.out.println("getSiteRMIForServerIP-------------"+path);
			return (ISiteRmi)namingContext.lookup(path);
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 添加站点操作
	 * @param SiteBean sb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean addSite(SiteBean sb,SettingLogsBean stl)
	{
		try{
			boolean is_add = false;
			if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
			{
				is_add = SiteOperation.addSite(sb, stl);
			}
			else
			{
				ISiteRmi siteRmi = getSiteRmigetSiteRMIForServerID(sb.getServer_id()+"");
				is_add = siteRmi.addSite(sb, stl);
			}
			
			if(is_add)
			{
				//在素材库服务器中添加站点
				addSiteInResourceServer(sb.getSite_id());
				SiteManager.reloadSiteList();
				SiteDomainManager.reloadSiteDomainList();
				return true;
			}
			else
				return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 在素材库服务器上添加站点
	 * @param String site_id
	 * @return boolean
	 */
	public static boolean addSiteInResourceServer(String site_id)
	{
		try{
			String resource_server_ip = SiteServerManager.getResourceServerIP();
			if(resource_server_ip != null && !"".equals(resource_server_ip))
			{
				boolean is_add = false;
				if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
				{
					is_add = SiteOperation.addSiteInResourceServer(site_id);
				}
				else
				{
					ISiteRmi siteRmi=getSiteRMIForServerIP(resource_server_ip);
					is_add = siteRmi.addSiteInResourceServer(site_id);
				}
				return is_add;
			}else
				return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			//System.out.println(" resource server ip is null ");
			return false;
		}
	}
	
	/**
	 * 修改站点操作
	 * @param SiteBean sb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSite(SiteBean sb,SettingLogsBean stl)
	{
		try{
			boolean is_update = false;
			if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
			{
				is_update = SiteOperation.updateSite(sb, stl);
			}
			else
			{
				ISiteRmi siteRmi=getSiteRMIForServerIP(sb.getServer_id()+"");
				is_update = siteRmi.updateSite(sb, stl);
			}
			
			return is_update;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 暂停,恢复站点
	 * @param String site_id
	 * @param String site_status 站点类型,1 停止,0 恢复
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateSiteStatus(String site_id,int site_status,SettingLogsBean stl)
	{
		try{
			boolean is_update = false;
			if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
			{
				is_update = SiteOperation.updateSiteStatus(site_id,site_status, stl);
			}
			else
			{
				ISiteRmi siteRmi=getSiteRMIForSiteID(site_id);
				is_update = siteRmi.updateSiteStatus(site_id,site_status, stl);
			}				
			return is_update;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除站点
	 * @param String site_id
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteSite(String site_id,SettingLogsBean stl)
	{
		try{
			boolean is_delete = false;
			if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
			{
				is_delete = SiteOperation.deleteSite(site_id, stl);
			}
			else
			{
				ISiteRmi siteRmi=getSiteRMIForSiteID(site_id);
				is_delete = siteRmi.deleteSite(site_id, stl);
			}
			
			
			if(is_delete)
			{
				SiteManager.reloadSiteList();
				SiteDomainManager.reloadSiteDomainList();
				return true;
			}else
				return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 添加站点多域名操作
	 * @param SiteDomainBean sdb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean addAlias(SiteDomainBean sdb,SettingLogsBean stl)
	{
		try{			
			boolean is_add = false;
			if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
			{
				is_add = SiteOperation.addAlias(sdb, stl);
			}
			else
			{
				ISiteRmi siteRmi=getSiteRMIForSiteID(sdb.getSite_id());
				is_add = siteRmi.addAlias(sdb, stl);
			}
			
			
			if(is_add)
			{
				SiteDomainManager.reloadSiteDomainList();
				return true;
			}else
				return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 修改站点多域名操作
	 * @param SiteDomainBean sdb
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean updateAlias(SiteDomainBean sdb,SettingLogsBean stl)
	{
		try{			
			boolean is_update = false;
			if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
			{
				is_update = SiteOperation.updateAlias(sdb, stl);
			}
			else
			{
				ISiteRmi siteRmi=getSiteRMIForSiteID(sdb.getSite_id());
				is_update = siteRmi.updateAlias(sdb, stl);
			}
			
			if(is_update)
			{
				SiteDomainManager.reloadSiteDomainList();
				return true;
			}else
				return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除站点多域名操作
	 * @param String site_id
	 * @param String domain_ids
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean deleteAlias(String site_id,String domain_ids,SettingLogsBean stl)
	{
		try{	
			boolean is_delete = false;
			if(SiteServerManager.IS_MUTILPUBLISHSERVER == false)
			{
				is_delete = SiteOperation.deleteAlias(domain_ids, stl);
			}
			else
			{
				ISiteRmi siteRmi=getSiteRMIForSiteID(site_id);
				is_delete = siteRmi.deleteAlias(domain_ids, stl);
			}
			
			if(is_delete)
			{
				SiteDomainManager.reloadSiteDomainList();
				return true;
			}else
				return false;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据站点修改总点击次数
	 * @param String hit_type
	 * @param HttpServletRequest request
	 * @return int
	 */
	public static boolean updateHitForSite(String site_id,int count)
	{
		try{
			if(SiteServerManager.IS_MUTILPUBLISHSERVER == false || SiteServerManager.isTheSameServer(site_id))
			{
				//如果不是多台服务器,或者就是在本服务器上操作的，直接执行了
				return SiteVisitCountManager.updateHitForSite(site_id, count);
			}else
			{
				ISiteRmi siteRmi=getSiteRMIForSiteID(site_id);
				return siteRmi.updateHitForSite(site_id, count);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args)
	{
		Context namingContext;
		try {
			namingContext = new InitialContext();
			System.out.println(namingContext.lookup("rmi://192.168.0.150:1102/siteRmi"));
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
