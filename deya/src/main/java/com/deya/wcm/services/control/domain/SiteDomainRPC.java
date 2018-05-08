package com.deya.wcm.services.control.domain;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.control.site.SiteOperationFactory;

public class SiteDomainRPC {
	/**
	 * 根据站点ID得到它的默认域名名称()
	 * 
	 * @param String site_id
	 * @return String
	 */
	public static String getDefaultSiteDomainBySiteID(String site_id)
	{
		return SiteDomainManager.getDefaultSiteDomainBySiteID(site_id);
	}
	
	/**
	 * 根据url得到站点ID
	 * 
	 * @param String url
	 * @return String site_id
	 */
	public static String getSiteIDByUrl(HttpServletRequest request)
	{
		return SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	}
	
	/**
	 * 根据站点ID得到域名列表
	 * 
	 * @param String domain_id
	 * @return SiteDomainBean
	 */
	public static List<SiteDomainBean> getDomainListBySiteID(String site_id)
	{
		return SiteDomainManager.getDomainListBySiteID(site_id);
	}
	
	/**
	 * 根据域名ID得到域名对象
	 * 
	 * @param String domain_id
	 * @return SiteDomainBean
	 */
	public static SiteDomainBean getSiteDomainBeanByID(String domain_id)
	{
		return SiteDomainManager.getSiteDomainBeanByID(domain_id);
	}
	
	/**
	 * 判断该域名是否存在
	 * @param String site_domain
	 * @return boolean 如果存在返回true 否则返回false
	 */
	public static boolean siteDomainISExist(String site_domain)
	{
		return SiteDomainManager.siteDomainISExist(site_domain);
	}
	
	
	/**
	 * 根据站点ID得到它的主域名列表名称(用于在修改站点信息时,列出它域名名称)
	 * 
	 * @param String site_id
	 * @return String
	 */
	public static String getSiteDomainBySiteID(String site_id)
	{
		return SiteDomainManager.getSiteDomainBySiteID(site_id);
	}
	
	/**
	 * 判断该域名是否在多域名中存在(不包括主域名)
	 * @param String site_domain
	 * @return boolean 如果存在返回true 否则返回false
	 */
	public static boolean siteDomainISExistNoHost(String site_domain)
	{
		return SiteDomainManager.siteDomainISExistNoHost(site_domain);
	}
	
	/**
     * 插入站点域名信息
     * @param SiteDomainBean sdb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSiteDomain(SiteDomainBean sdb,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteOperationFactory.addAlias(sdb, stl)){
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	/**
     * 插入站点域名信息
     * @param SiteDomainBean sdb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSiteDomain(SiteDomainBean sdb,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteOperationFactory.updateAlias(sdb, stl)){
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	/**
     * 修改站点域名状态
     * @param String 域名ID
     * @param String site_id 站点ID
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSDomainStatus(String domain_id,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteDomainManager.updateSDomainStatus(domain_id,site_id, stl)){
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
	/**
     * 删除站点域名
     * @param String 域名ID
     * @param String site_id 站点ID
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSiteDomain(String domain_ids,String site_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteOperationFactory.deleteAlias(site_id, domain_ids, stl)){
				return true;
			}else
				return false;
		}else{
			return false;
		}
	}
	
}
