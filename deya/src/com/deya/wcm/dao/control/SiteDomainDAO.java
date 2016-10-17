package com.deya.wcm.dao.control;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.control.SiteDomainBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  站点域名管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点域名管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteDomainDAO {
	/**
     * 得到站点域名列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getSiteDomainList()
	{
		return DBManager.queryFList("getSiteDomainList", "");
	}
	
	/**
     * 插入站点域名信息
     * @param SiteDomainBean sdb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSiteDomain(SiteDomainBean sdb,SettingLogsBean stl)
	{
		int domain_id = PublicTableDAO.getIDByTableName(PublicTableDAO.SITEDOMAIN_TABLE_NAME);
		sdb.setDomain_id(domain_id);
		if(DBManager.insert("insert_site_domain", sdb))
		{
			PublicTableDAO.insertSettingLogs("添加","域名",sdb.getDomain_id()+"",stl);
			return true;
		}else
		{
			return false;
		}		
	}
	
	/**
     * 修改站点域名信息
     * @param SiteDomainBean sdb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSiteDomain(SiteDomainBean sdb,SettingLogsBean stl)
	{
		if(DBManager.update("update_site_domain", sdb))
		{
			PublicTableDAO.insertSettingLogs("修改","域名",sdb.getDomain_id()+"",stl);
			return true;
		}else
		{
			return false;
		}		
	}
	
	/**
     * 修改站点域名信息(根据域名名称修改)
     * @param String new_site_domain
     * @param String site_domain
     * @return boolean
     * */
	public static boolean updateSiteDomainByName(Map<String, String> m)
	{		
		return DBManager.update("update_site_domain_byName", m);
	}
	
	/**
     * 修改站点域名状态
     * @param Map
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSDomainStatus(String domain_id,String site_id,SettingLogsBean stl)
	{
		//首先按site_id撤消默认选中的域名
		if(DBManager.update("cancel_sitedomain_default", site_id))
		{
			if(DBManager.update("update_site_domain_status", domain_id))
			{
				PublicTableDAO.insertSettingLogs("修改","域名状态",domain_id,stl);
				return true;
			}else
			{
				return false;
			}
		}
		return false;
		
	}
	
	/**
     * 根据域名ID删除域名
     * @param Map m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSiteDomainByID(Map<String, String> m,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_site_domain", m))
		{
			PublicTableDAO.insertSettingLogs("删除","域名",(String)m.get("domain_id"),stl);
			return true;
		}else
		{
			return false;
		}
	}
	
	/**
     * 根据站点ID删除域名
     * @param String site_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSiteDomainBySiteID(String site_id,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_site_domainBySiteID", site_id))
		{
			PublicTableDAO.insertSettingLogs("删除","域名 站点ID",site_id,stl);
			return true;
		}else
		{
			return false;
		}
	}
}
