package com.deya.wcm.services.org.app;

import java.util.ArrayList;
import java.util.List;

import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.org.operate.OperateDAO;
import com.deya.wcm.server.LicenseCheck;

/**
 *  应用系统管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 组织机构角色管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class AppManager implements ISyncCatch{
	private static List<AppBean> app_List = new ArrayList<AppBean>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		app_List.clear();		
		List<AppBean> l = OperateDAO.getAppList();
		if( l != null && l.size() > 0)
		{
			for(AppBean ab : l)
			{
				if(LicenseCheck.isHaveApp(ab.getApp_id()))
				{
					app_List.add(ab);
				}
			}
		}
	}
	
	/**
	 * 初始加载应用信息
	 * 
	 * @param
	 * @return
	 */
	public static void reloadApp()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.org.app.AppManager");
	}
	
	/**
	 * 得到应用系统列表（用于后台页面管理，列出所有的应用系统）
	 * 
	 * @param
	 * @return List
	 */	
	public static List<AppBean> getAppList()
	{
		return app_List;
	}
	

	/**
	 * 根据应用ID得到列表（可多个ID）
	 * 
	 * @param String app_ids
	 * @return List
	 */
	public static List<AppBean> getAppListByIDS(String app_ids)
	{
		app_ids = ","+app_ids+",";
		List<AppBean> l = new ArrayList<AppBean>();
		if(app_List != null && app_List.size() > 0)
		{
			for(int i=0;i<app_List.size();i++)
			{
				if(app_ids.contains(","+app_List.get(i).getApp_id()+","))
					l.add(app_List.get(i));
			}
		}
		return l;
	}
	
	/**
	 * 根据应用ID得到应用对象
	 * 
	 * @param String app_ids
	 * @return List
	 */
	public static AppBean getAppBean(String app_id)
	{
		if(app_List != null && app_List.size() > 0)
		{
			AppBean ab = new AppBean();
			for(int i=0;i<app_List.size();i++)
			{
				if(app_id.equals(app_List.get(i).getApp_id()))
					ab = app_List.get(i);
			}
			return ab;
		}else
			return null;
	}
	
	/**
	 * 根据应用ID判断此应用是否是内容管理应用或政务公开应用（这两个是特殊的，需要单独对待）
	 * 
	 * @param String app_ids
	 * @return boolean
	 */
	public static boolean appIsPeculiar(String app_id)
	{
		return ("cms".equals(app_id) || "zwgk".equals(app_id));
	}
	
	public static void main(String args[])
	{
		/*
		List<AppBean> l = getAppListByIDS("statistics,zwgk,cms");
		for(int i=0;i<l.size();i++)
		{
			System.out.println(l.get(i).getApp_id() + "-----" +l.get(i).getApp_sort());
		}*/
		
	}
}
