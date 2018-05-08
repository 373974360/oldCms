package com.deya.wcm.dao.system.metadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.metadata.MetaDataBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  元数据管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 元数据管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class MetaDataDAO {
	/**
     * 得到所有元数据信息
     * @param 
     * @return List
     * */	
	@SuppressWarnings("unchecked")
	public static List<MetaDataBean> getAllMetaDataList()
	{
		return DBManager.queryFList("getAllMetaDataList", "");
	}
	
	/**
	 * 从数据库中根据条件查到总数
	 * 
	 * @param Map m(con_name,con_value)
	 * @return String
	 */	
	public static String getMetaDataCountForDB(Map<String,String> m)
	{
		return DBManager.getString("getMetaDataCountForDB", m);
	}
	
	/**
	 * 从数据库中根据条件查到元数据列表
	 * 
	 * @param Map m(start_num,page_size,sort_name,sort_type,con_name,con_value)
	 * @return List
	 */	
	@SuppressWarnings("unchecked")
	public static List<MetaDataBean> getMetaDataListForDB(Map<String,String> m)
	{
		return DBManager.queryFList("getMetaDataListForDB", m);
	}
	
	/**
	 * 得到元数据对象
	 * 
	 * @param int metaData_id
	 * @return MetaDataBean 
	 */
	public static MetaDataBean getMetaDataBean(int metaData_id)
	{
		return (MetaDataBean)DBManager.queryFObj("getMetaDataBean", metaData_id+"");
	}
	
	/**
     * 插入元数据信息
     * @param MetaDataBean mdb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertMetaData(MetaDataBean mdb,SettingLogsBean stl){
		int meta_id = PublicTableDAO.getIDByTableName(PublicTableDAO.METADATA_TABLE_NAME);
		mdb.setMeta_id(meta_id);
		if(DBManager.insert("insert_metadata", mdb))
		{
			PublicTableDAO.insertSettingLogs("添加","元数据",meta_id+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 修改元数据信息
     * @param MetaDataBean mdb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean updateMetaData(MetaDataBean mdb,SettingLogsBean stl){
		if(DBManager.update("update_metadata", mdb))
		{
			PublicTableDAO.insertSettingLogs("修改","元数据",mdb.getMeta_id()+"",stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除元数据信息
     * @param String metadata_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteMetaData(String meta_id,SettingLogsBean stl){
		Map<String,String> m = new HashMap<String,String>();
		m.put("meta_id", meta_id);
		if(DBManager.delete("delete_metadata", m))
		{
			PublicTableDAO.insertSettingLogs("修改","元数据",meta_id,stl);
			return true;
		}else
			return false;
	}
}
