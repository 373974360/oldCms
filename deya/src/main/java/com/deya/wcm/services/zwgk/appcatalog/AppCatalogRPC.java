package com.deya.wcm.services.zwgk.appcatalog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.appcatalog.AppCatalogBean;
import com.deya.wcm.bean.zwgk.appcatalog.RegulationBean;
import com.deya.wcm.services.Log.LogManager;

public class AppCatalogRPC {
	/**
     * 根据目录ID得到公开目录树
     * @param int parent_id
     * @param String selected_cat_ids 要拷贝的节点ID
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static String getAppCatalogTree(int cata_id)
	{
		return AppCatalogManager.getAppCatalogTree(cata_id);
	}
	
	public static int getNewAppCatalogID()
	{
		return AppCatalogManager.getNewAppCatalogID();
	}
	
	/**
	 * 根据ID得到对象
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static AppCatalogBean getAppCatalogBean(int cata_id)
	{
		return AppCatalogManager.getAppCatalogBean(cata_id);
	}
	
	/**
     * 根据ID得到它的子级列表(deep+1)
     * @param int cata_id
     * @return List
     * */
	public static List<AppCatalogBean> getChildCatalogList(int cata_id)
	{
		return AppCatalogManager.getChildCatalogList(cata_id);
	}
	
	/**
	 * 插入公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean insertGKAppCatelog(AppCatalogBean acb,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AppCatalogManager.insertGKAppCatelog(acb,stl);
		}else
			return false;
	}
	
	/**
	 * 修改公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean updateGKAppCatelog(AppCatalogBean acb,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AppCatalogManager.updateGKAppCatelog(acb,stl);
		}else
			return false;
	}
	
	/**
	 * 保存排序
	 * @param String ids
	 * @return boolean
	 */
	public static boolean sortGKAppCatelog(String ids,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AppCatalogManager.sortGKAppCatelog(ids,stl);
		}else
			return false;
	}
	
	/**
	 * 移动公开应用目录节点
	 * @param int parent_id
	 * @param int String ids
	 * @return boolean
	 */
	public static boolean moveGKAppCatelog(int parent_id,String ids,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AppCatalogManager.moveGKAppCatelog(parent_id,ids,stl);
		}else
			return false;
	}
	
	/**
	 * 删除公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean deleteGKAppCatelog(String ids,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AppCatalogManager.deleteGKAppCatelog(ids,stl);
		}else
			return false;
	}
	
	/**
     * 拷贝共享目录
     * @param int parent_id
     * @param String selected_cat_ids 要拷贝的节点ID
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean copyShareCategory(int parent_id,String selected_cat_ids,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return AppCatalogManager.copyShareCategory(parent_id,selected_cat_ids,stl);
		}else
			return false;
	}
	
	/**
	 * 根据目录节点ID得到所有规则
	 * @param int cata_id
	 * @return List
	 */
	public static List<RegulationBean> getAppCataReguList(int cata_id)
	{
		return RegulationManager.getAppCataReguList(cata_id);
	}
	
	/**
	 * 插入公开应用目录节点同步规则
	 * @param List<RegulationBean> l
	 * @param int cata_id
	 * @param SettingLogsBean stl
	 * @return boolean
	 */
	public static boolean insertAppCateRegu(List<RegulationBean> l,String cata_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return RegulationManager.insertAppCateRegu(l,cata_id,stl);
		}else
			return false;
	}
	
	public static void main(String args[])
	{
		System.out.println(getAppCatalogTree(3));
	}
}
