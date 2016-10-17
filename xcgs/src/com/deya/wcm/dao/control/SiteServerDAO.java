package com.deya.wcm.dao.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.control.SiteServerBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  网站服务器管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 网站服务器管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteServerDAO {
	/**
     * 得到所有网站服务器列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getSiteServerList()
	{
		return DBManager.queryFList("getSiteServerList", "");
	}
	
	/**
     * 插入网站服务器信息
     * @param SiteServerBean ssb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSiteServer(SiteServerBean ssb,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.SITESERVER_TABLE_NAME);
		ssb.setServer_id(id);		
		if(DBManager.insert("insert_site_server", ssb))
		{
			PublicTableDAO.insertSettingLogs("添加","网站服务器",id+"",stl);
			return true;
		}else
		{
			return false;
		}
		
	}
	
	/**
     * 修改网站服务器信息
     * @param SiteServerBean ssb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSiteServer(SiteServerBean ssb,SettingLogsBean stl)
	{
		if(DBManager.update("update_site_server", ssb))
		{
			PublicTableDAO.insertSettingLogs("修改","网站服务器",ssb.getServer_id()+"",stl);
			return true;
		}else
		{
			return false;
		}		
	}
	
	/**
     * 删除网站服务器信息
     * @param String server_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSiteServer(String server_ids,SettingLogsBean stl)
	{
		Map<String, String> m = new HashMap<String, String>();
		m.put("server_id", server_ids);
		if(DBManager.delete("delete_site_server", m))
		{
			PublicTableDAO.insertSettingLogs("删除","网站服务器",server_ids,stl);
			return true;
		}else
		{
			return false;
		}		
	}
	
	/**
     * 得到所有网站服务器列表 -- 按分页
     * @param 
     * @return List
     * */
	public static List<SiteServerBean> getSiteServerListByPage(Map<String,String> m)
	{ 
		return DBManager.queryFList("getServerList",m);
	}
}
