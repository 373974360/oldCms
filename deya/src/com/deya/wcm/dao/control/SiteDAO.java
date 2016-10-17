package com.deya.wcm.dao.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.control.SiteBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  站点管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 站点管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteDAO {
	/**
     * 得到所有站点列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SiteBean> getAllSiteList()
	{
		return DBManager.queryFList("getAllSiteList", "");
	}
	
	/**
     * 得到站点对象
     * @param String site_id
     * @return SiteBean
     * */
	public static SiteBean getSiteBean(String site_id)
	{
		return (SiteBean)DBManager.queryFObj("getSiteBean", site_id);
	}
	
	/**
     * 插入站点信息
     * @param SiteBean sb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSite(SiteBean sb,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_site", sb))
		{
			PublicTableDAO.insertSettingLogs("添加","站点",sb.getSite_id(),stl);
			return true;
		}else
		{
			return false;
		}		
	}
	
	/**
     * 修改站点信息（此处不包含对站点状态的修改，修改站点状态要做很多apache或tomcat的处理）
     * @param SiteBean sb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSite(SiteBean sb,SettingLogsBean stl)
	{		
		if(DBManager.update("update_site", sb))
		{
			PublicTableDAO.insertSettingLogs("修改","站点",sb.getSite_id(),stl);
			return true;
		}else
		{
			return false;
		}		
	}
	
	/**
     * 修改站点状态信息
     * @param SiteBean sb
     * @param 
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean updateSiteStatus(String site_id,int site_status,SettingLogsBean stl)
	{
		Map<String, Comparable> m = new HashMap<String, Comparable>();
		m.put("site_id", site_id);
		m.put("site_status", site_status);
		if(site_status == 1)
		{
			m.put("site_pausetime", DateUtil.getCurrentDateTime());
		}else
		{
			m.put("site_pausetime", "");
		}
		if(DBManager.update("update_site_status", m))
		{
			PublicTableDAO.insertSettingLogs("修改","站点",site_id,stl);
			return true;
		}else
		{
			return false;
		}		
	}
	
	/**
     * 站点排序
     * @param String site_ids
     * @param 
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean saveSiteSort(String site_ids,SettingLogsBean stl)
	{
		if(site_ids != null && !"".equals(site_ids))
		{
			try{
				Map<String, Comparable> m = new HashMap<String, Comparable>();
				String[] tempA = site_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("site_sort", (i+1));
					m.put("site_id", tempA[i]);
					DBManager.update("save_site_sort", m);				
				}
				PublicTableDAO.insertSettingLogs("保存排序","站点","",stl);
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
			return true;
	}
	
	/**
     * 删除站点(只能一个一个删除)
     * @param String site_id
     * @param 
     * @param SettingLogsBean stl
     * @return boolean
     * */
     public static boolean deleteSite(String site_id,SettingLogsBean stl)
     {
     	if(DBManager.update("delete_site", site_id))
     	{
     		PublicTableDAO.insertSettingLogs("删除","站点",site_id,stl);
			return true;
     	}
     	else
     		return false;
     }
}
