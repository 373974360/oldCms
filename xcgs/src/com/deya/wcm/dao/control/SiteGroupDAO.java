package com.deya.wcm.dao.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.control.SiteGroupBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  网站群管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 网站群管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SiteGroupDAO {
	/**
     * 得到所有网站群列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getSiteGroupList()
	{
		return DBManager.queryFList("getSiteGroupList", "");
	}
	
	/**
     * 插入网站群信息
     * @param SiteGroupBean sgb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertSiteGroup(SiteGroupBean sgb,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_site_group", sgb))
		{
			PublicTableDAO.insertSettingLogs("添加","网站群",sgb.getSgroup_id(),stl);
			return true;
		}else
		{
			return false;
		}
		
	}
	
	/**
     * 修改网站群信息
     * @param SiteGroupBean sgb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateSiteGroup(SiteGroupBean sgb,SettingLogsBean stl)
	{
		if(DBManager.update("update_site_group", sgb))
		{
			PublicTableDAO.insertSettingLogs("修改","网站群",sgb.getSgroup_id(),stl);
			return true;
		}else
		{
			return false;
		}		
	}
	
	/**
     * 保存网站群排序
     * @param String sgroup_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	@SuppressWarnings("unchecked")
	public static boolean saveSGroupSort(String sgroup_ids,SettingLogsBean stl)
	{
		if(sgroup_ids != null && !"".equals(sgroup_ids))
		{
			try{
				Map<String, Comparable> m = new HashMap<String, Comparable>();
				String[] tempA = sgroup_ids.split(",");
				for(int i=0;i<tempA.length;i++)
				{
					m.put("sgroup_sort", (i+1));
					m.put("sgroup_id", tempA[i]);
					DBManager.update("save_site_group_sort", m);
				}
				PublicTableDAO.insertSettingLogs("保存排序","网站群",sgroup_ids,stl);
				return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		else
			return true;
	}
	
	/**
     * 删除网站群信息
     * @param String sgroup_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteSiteGroup(String sgroup_id,SettingLogsBean stl)
	{
		if(DBManager.delete("delete_site_group", sgroup_id))
		{
			PublicTableDAO.insertSettingLogs("删除","网站群",sgroup_id,stl);
			return true;
		}else
		{
			return false;
		}
		
	}
}
