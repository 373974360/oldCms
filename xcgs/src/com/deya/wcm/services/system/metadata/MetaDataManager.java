package com.deya.wcm.services.system.metadata;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.metadata.MetaDataBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.system.metadata.MetaDataDAO;

/**
 *  元数据管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 元数据管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class MetaDataManager implements ISyncCatch{
	
	private static TreeMap<Integer,MetaDataBean> md_map = new TreeMap<Integer, MetaDataBean>();	//元数据缓存
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		List<MetaDataBean> md_list = MetaDataDAO.getAllMetaDataList();
		md_map.clear();
		if(md_list != null && md_list.size() > 0)
		{
			for(int i=0;i<md_list.size();i++)
			{
				md_map.put(md_list.get(i).getMeta_id(),md_list.get(i));
			}
		}
	}
	
	/**
	 * 初始加载元数据
	 * 
	 * @param
	 * @return
	 */
	public static void reloadMetaData(){
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.metadata.MetaDataManager");
	}
	
	/**
	 * 返回Map
	 * 
	 * @param
	 * @return
	 */
	public static Map<Integer,MetaDataBean> getMetaDataMap()
	{
		return md_map;
	}
	
	/**
	 * 得到元数据总数
	 * 
	 * @param 
	 * @return String
	 */
	public static String getMetaDataCount()
	{
		return md_map.size()+"";
	}
	
	/**
	 * 从数据库中根据条件查到总数
	 * 
	 * @param Map m(con_name,con_value)
	 * @return String
	 */	
	public static String getMetaDataCountForDB(Map<String,String> m)
	{
		return MetaDataDAO.getMetaDataCountForDB(m);
	}
	
	/**
	 * 从数据库中根据条件查到元数据列表
	 * 
	 * @param Map m(start_num,page_size,sort_name,sort_type,con_name,con_value)
	 * @return List
	 */		
	public static List<MetaDataBean> getMetaDataListForDB(Map<String,String> m){
		return MetaDataDAO.getMetaDataListForDB(m);
	}
	
	/**
	 * 得到元数据对象
	 * 
	 * @param int metaData_id
	 * @return MetaDataBean 
	 */
	public static MetaDataBean getMetaDataBean(int metaData_id)
	{
		if(md_map.containsKey(metaData_id))
			return md_map.get(metaData_id);
		else
		{
			MetaDataBean mdb = MetaDataDAO.getMetaDataBean(metaData_id);
			if(mdb != null)
				md_map.put(metaData_id,mdb);
			return mdb;
		}
	}
	
	/**
     * 插入角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMetaData(MetaDataBean mdb,SettingLogsBean stl){
		if(MetaDataDAO.insertMetaData(mdb, stl))
		{
			reloadMetaData();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMetaData(MetaDataBean mdb,SettingLogsBean stl){
		if(MetaDataDAO.updateMetaData(mdb, stl))
		{
			reloadMetaData();
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除元数据信息
     * @param String metadata_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMetaData(String meta_id,SettingLogsBean stl){
		if(MetaDataDAO.deleteMetaData(meta_id, stl))
		{
			reloadMetaData();
			return true;
		}
		else
			return false;
	}
}
