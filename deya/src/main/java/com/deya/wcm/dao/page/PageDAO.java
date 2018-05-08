package com.deya.wcm.dao.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.server.ServerManager;

/**
 *  页面管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 页面管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */


public class PageDAO {
	@SuppressWarnings("unchecked")
	public static List<PageBean> getPageListBySiteID(String site_id)
	{
		return DBManager.queryFList("getPageListBySiteID", site_id);
	}
	
	@SuppressWarnings("unchecked")
	public static List<PageBean> getAllPageList()
	{
		return DBManager.queryFList("getAllPageList", "");
	}
	
	@SuppressWarnings("unchecked")
	public static List<PageBean> getTimerPageList(String current_time)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("current_time", current_time);
		String ip = ServerManager.LOCAL_IP;
		if(ip != null && !"".equals(ip) && !"127.0.0.1".equals(ip))
			m.put("server_ip", ServerManager.LOCAL_IP);
		return DBManager.queryFList("getTimerPageList", m);
	}
	
	@SuppressWarnings("unchecked")
	public static List<PageBean> getAllPageListBySiteID(String app_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("app_id", app_id);
		m.put("site_id", site_id);
		return DBManager.queryFList("getAllPageListBySiteID", m);
	}
	
	public static PageBean getPageBean(int id)
	{
		return (PageBean)DBManager.queryFObj("getAllPageList", id);
	}
	
	public static boolean clonePage(PageBean pb)
	{		
		return DBManager.insert("insert_page", pb);
	}
	
	public static boolean insertPage(PageBean pb,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_page", pb))
		{
			PublicTableDAO.insertSettingLogs("添加","页面",pb.getId()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean updatePage(PageBean pb,SettingLogsBean stl)
	{
		if(DBManager.update("update_page", pb))
		{
			PublicTableDAO.insertSettingLogs("修改","页面",pb.getId()+"",stl);
			return true;
		}else
			return false;
	}
	
	public static boolean updatePageTime(Map<String,String> m)
	{
		return DBManager.update("update_page_time", m);
	}
	
	public static boolean deletePage(int id,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_page", id))
		{
			PublicTableDAO.insertSettingLogs("删除","页面",id+"",stl);
			return true;
		}else
			return false;
	}
}
