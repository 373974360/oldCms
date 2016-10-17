package com.deya.wcm.services.org.app;

import java.util.List;

import com.deya.wcm.bean.org.app.AppBean;
import com.deya.wcm.dao.PublicTableDAO;

public class AppRPC {
	/**
	 * 得到应用系统列表（用于后台页面管理，列出所有的应用系统）
	 * 
	 * @param
	 * @return List
	 */	
	public static List<AppBean> getAppList()
	{
		return AppManager.getAppList();
	}
	
	/**
	 * 根据应用ID判断此应用是否是内容管理应用或政务公开应用（这两个是特殊的，需要单独对待）
	 * 
	 * @param String app_ids
	 * @return boolean
	 */
	public static boolean appIsPeculiar(String app_id)
	{
		return AppManager.appIsPeculiar(app_id);
	}
	/************************** 工具类方法 ************************************/
	/**
	 * 
	 * 执行动态sql查询
	 * 
	 * @param String sql
	 * 
	 * @return String
	 */	
	@SuppressWarnings("unchecked")
	public static List executeSearchSql(String sql)
	{
		return PublicTableDAO.executeSearchSql(sql);
	}
	
	/**
	 * 
	 * 执行动态sql
	 * 
	 * @param String sql
	 * 
	 * @return String
	 */
	public static boolean executeDynamicSql(String sql)
	{
		return PublicTableDAO.executeDynamicSql(sql);
	}
}
