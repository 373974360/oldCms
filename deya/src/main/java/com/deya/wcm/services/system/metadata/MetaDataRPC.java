package com.deya.wcm.services.system.metadata;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.metadata.MetaDataBean;
import com.deya.wcm.services.Log.LogManager;

public class MetaDataRPC {
	/**
	 * 得到元数据总数
	 * 
	 * @param 
	 * @return String
	 */
	public static String getMetaDataCount()
	{
		return MetaDataManager.getMetaDataCount();
	}
	
	/**
	 * 返回Map
	 * 
	 * @param
	 * @return
	 */
	public static Map<Integer,MetaDataBean> getMetaDataMap(){
		return MetaDataManager.getMetaDataMap();
	}
	
	/**
	 * 从数据库中根据条件查到总数
	 * 
	 * @param Map m(con_name,con_value)
	 * @return String
	 */	
	public static String getMetaDataCountForDB(Map<String,String> m)
	{
		return MetaDataManager.getMetaDataCountForDB(m);
	}
	
	/**
	 * 从数据库中根据条件查到元数据列表
	 * 
	 * @param Map m(start_num,page_size,sort_name,sort_type,con_name,con_value)
	 * @return List
	 */		
	public static List<MetaDataBean> getMetaDataListForDB(Map<String,String> m){
		return MetaDataManager.getMetaDataListForDB(m);
	}
	
	/**
	 * 得到元数据对象
	 * 
	 * @param int metaData_id
	 * @return MetaDataBean 
	 */
	public static MetaDataBean getMetaDataBean(int metaData_id){
		return MetaDataManager.getMetaDataBean(metaData_id);
	}
	
	/**
     * 插入角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMetaData(MetaDataBean mdb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MetaDataManager.insertMetaData(mdb,stl);
		}else
			return false;
	}
	
	/**
     * 修改角色信息
     * @param RoleBean rb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMetaData(MetaDataBean mdb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MetaDataManager.updateMetaData(mdb,stl);
		}else
			return false;
	}
	/**
     * 删除元数据信息
     * @param String metadata_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMetaData(String meta_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return MetaDataManager.deleteMetaData(meta_id,stl);
		}else
			return false;
	}
	
	public static void main(String args[])
	{
		System.out.println(getMetaDataCount());
	}
}
