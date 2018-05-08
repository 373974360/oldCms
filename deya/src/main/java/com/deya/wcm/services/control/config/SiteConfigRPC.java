package com.deya.wcm.services.control.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.control.SiteConfigBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

/**
 *  站点配置管理逻辑处理类 js调用.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点配置管理逻辑处理类 js调用</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class SiteConfigRPC {

	
	/**
	 * 根据站点ID获取配置列表
	 * @param String site_id
	 * @return List
	 */ 
	@SuppressWarnings("unchecked")
	public static List getConfigListBySiteID(String site_id)
	{
		return SiteConfigManager.getConfigListBySiteID(site_id);
	}
	
	
	/**
     * 插入站点配置信息
     * @param SiteConfigBean scb
     * @return boolean
     * */
	public static boolean insertSiteConfig(List<SiteConfigBean> l,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{ 
			if(SiteConfigManager.insertSiteConfig(l, stl)){
				return true;
			}else
				return false;
		}else{  
			return false;
		}
	}
	
	
	/**
     * 删除站点配置信息
     * @param String config_ids
     * @return boolean
     * */
	public static boolean deleteSiteConfig(String config_ids)
	{
		return SiteConfigManager.deleteSiteConfig(config_ids);
	}
	
	
	/**
	 * 根据主键ID获取配置
	 * @param String site_id
	 * @return List
	 */ 
	public static SiteConfigBean getConfigByConfigID(String config_id)
	{
		return SiteConfigManager.getConfigByConfigID(config_id);
	}
	
	
	/**
     * 修改站点配置信息
     * @param SiteConfigBean scb
     * @return boolean
     * */
	public static boolean updateSiteConfig(SiteConfigBean scb)
	{
		return SiteConfigManager.updateSiteConfig(scb);
	}
	
	/**
     * 插入站点配置信息
     * @param SiteConfigBean scb
     * @return boolean
     * */
	public static boolean insertSiteConfig(SiteConfigBean scb)
	{
		return SiteConfigManager.insertSiteConfig(scb);
	}
	
	/**
	 * 根据站点ID和key值得到value
	 * @param String site_id
	 * @param String key
	 * @return SiteConfigBean
	 */ 
	public static SiteConfigBean getConfigValues(String site_id,String key)
	{
		return SiteConfigManager.getConfigValues(site_id, key);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
