package com.deya.wcm.dao.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.control.SiteAppBean;
import com.deya.wcm.bean.control.SiteConfigBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  站点配置管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点配置管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteConfigDAO {
	/**
     * 得到站点配置列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SiteConfigBean> getAllSiteConfigList()
	{
		return DBManager.queryFList("getAllSiteConfigList", "");
	}
	
	/**
     * 插入站点配置信息
     * @param SiteConfigBean scb
     * @return boolean
     * */
	public static boolean insertSiteConfig(List<SiteConfigBean> l,SettingLogsBean stl)
	{
		if(l != null && l.size() > 0)
		{
			try{
				//deleteSiteConfigHandl(l.get(0).getSite_id());
				for(int i=0;i<l.size();i++)
				{
					insertSiteConfigHandl(l.get(i));
				}
				PublicTableDAO.insertSettingLogs("添加","站点配置 站点",l.get(0).getSite_id(),stl);
				return true;
			}catch(Exception e)
			{
				return false;
			}
		}else
			return true;
	}
	
	/**
     * 插入站点配置信息
     * @param SiteConfigBean scb
     * @return boolean
     * */
	public static boolean insertSiteConfigHandl(SiteConfigBean scb)
	{		
		scb.setConfig_id(PublicTableDAO.getIDByTableName(PublicTableDAO.SITECONFIG_TABLE_NAME));
		return DBManager.insert("insert_site_config", scb);			
	}
	
	/**
     * 删除站点配置信息
     * @param String site_id
     * @return boolean
     * */
	public static boolean deleteSiteConfigHandl(String site_id)
	{
		return DBManager.delete("delete_site_config", site_id);
	}
	
	
	/**
     * 删除站点配置信息
     * @param String config_ids
     * @return boolean
     * */
	public static boolean deleteSiteConfig(String config_ids)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("config_ids", config_ids);
		return DBManager.delete("delete_site_configbyids", m); 
	}
	
	/**
     * 修改站点配置信息
     * @param SiteConfigBean scb
     * @return boolean
     * */
	public static boolean updateSiteConfig(SiteConfigBean scb)
	{
		return DBManager.update("update_site_configbyid", scb); 
	}
	
	/**
     * 得到站点与应用的关联关系
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SiteAppBean> getSiteAppReleList()
	{
		return DBManager.queryFList("getSiteAppReleList", "");
	}
	
	/**
     * 插入站点与应用的关联关系
     * @param 
     * @return List
     * */
	public static boolean insertSiteReleApp(String site_id,String app_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("site_id", site_id);
		m.put("app_id", app_id);
		m.put("sa_id", PublicTableDAO.getIDByTableName(PublicTableDAO.SITEAPP_TABLE_NAME)+"");
		if(DBManager.delete("delete_site_rele_app", app_id))
			return DBManager.insert("insety_site_rele_app", m);
		else
			return false;
	}
	
	/**
     * 插入站点与应用的关联关系
     * @param 
     * @return List
     * */
	public static boolean insertSiteReleApp(SiteAppBean sab)
	{		
		sab.setSa_id(PublicTableDAO.getIDByTableName(PublicTableDAO.SITEAPP_TABLE_NAME));
		if(DBManager.delete("delete_site_rele_app", sab.getApp_id()))
			return DBManager.insert("insert_site_rele_app", sab);
		else
			return false;
	}
}
