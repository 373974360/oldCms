package com.deya.wcm.dao.zwgk.appcatalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.appcatalog.AppCatalogBean;
import com.deya.wcm.bean.zwgk.appcatalog.RegulationBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
/**
 *  公开应用目录数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 公开应用目录数据处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */
public class AppCatalogDAO {
	/**
	 * 得到所有的公开应用目录节点
	 * @param 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<AppCatalogBean> getGKAppCatalogList()
	{
		return DBManager.queryFList("getGKAppCatalogList", "");
	}
	
	/**
	 * 根据ID得到对象
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static AppCatalogBean getAppCatalogBean(int cata_id)
	{
		return (AppCatalogBean)DBManager.queryFObj("getAppCatalogBean", cata_id);
	}
	
	/**
	 * 插入公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean insertGKAppCatelog(AppCatalogBean acb)
	{
		return DBManager.insert("insert_gk_app_catalog", acb);
	}
	
	/**
	 * 修改公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean updateGKAppCatelog(AppCatalogBean acb,SettingLogsBean stl)
	{
		if(DBManager.update("update_gk_app_catalog", acb))
		{
			PublicTableDAO.insertSettingLogs("修改", "公开应用目录", acb.getCata_id()+"", stl);
			return true;
		}else
			return false;
	}
	
	/**
	 * 保存排序
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean sortGKAppCatelog(String ids,SettingLogsBean stl)
	{
		try{
			Map<String,Object> m =new HashMap<String,Object>();
			String[] tempA = ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				m.put("cat_sort", i+1);
				m.put("cata_id", tempA[i]);
				DBManager.update("sort_gk_app_catalog", m);
			}
			PublicTableDAO.insertSettingLogs("保存排序","公开应用目录",ids,stl);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * 移动公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean moveGKAppCatelog(Map<String,String> m)
	{
		return DBManager.update("move_gk_app_catalog", m);
	}
	
	/**
	 * 修改目录sql
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean updateGKAppCatelogSQL(Map<String,String> m)
	{
		return DBManager.update("update_gk_app_cata_sql", m);
	}
	
	/**
	 * 删除公开应用目录节点
	 * @param AppCatalogBean acb
	 * @return boolean
	 */
	public static boolean deleteGKAppCatelog(String cata_ids,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("cata_ids", cata_ids);
		if(DBManager.delete("delete_gk_app_catalog", m))
		{
			PublicTableDAO.insertSettingLogs("删除", "公开应用目录", cata_ids, stl);
			return true;
		}else
			return false;
	}
	
	/***************************** 规则管理　开始  ****************************************/
	/**
	 * 根据目录节点ID得到所有规则
	 * @param 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<RegulationBean> getAppCataReguList(int cata_id)
	{
		return DBManager.queryFList("getAppCataReguList", cata_id+"");
	}
	
	/**
	 * 插入公开应用目录节点同步规则
	 * @param RegulationBean rub
	 * @return boolean
	 */
	public static boolean insertAppCateRegu(RegulationBean rub)
	{
		return DBManager.insert("insert_gk_app_regu", rub);
	}
	
	/**
	 * 根据应用目录节点ID删除同步规则
	 * @param RegulationBean rub
	 * @return boolean
	 */
	public static boolean deleteAppCateRegu(String cata_ids)	
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("cata_ids", cata_ids);
		return DBManager.insert("delete_gk_app_regu", m);
	}
}
