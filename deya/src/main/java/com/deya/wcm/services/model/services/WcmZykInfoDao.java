package com.deya.wcm.services.model.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.model.WcmZykFile;

/**
 *  资源库同步信息时的数据库操作处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 资源库同步信息时的数据库操作处理类</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 * * 
 */
public class WcmZykInfoDao {
	
	private static String FIELD_TABLE_NAME = "cs_wcminfo_zykinfo";
	
	/**
     * 插入信息
     * @param String id,String info_id
     * @return boolean
     * */
	public static boolean addWcminfo_zykinfo(String id,String info_id,String site_id) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("info_id", info_id);
		map.put("site_id", site_id);
		return DBManager.insert("model.wcm_zykinfo.addWcminfo_zykinfo", map);
	}  
	
	
	/**
     * 通过id 得到info_id
     * @param String id
     * @return String
     * */
	public static String getWcminfo_zykinfoById(String id,String site_id) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("site_id", site_id);
		return (String)DBManager.getString("model.wcm_zykinfo.getWcminfo_zykinfoById", map);
	}
	
	
	/**
     * 通过id 得到info_ids
     * @param String id
     * @return String
     * */
	public static List<Map> getWcminfo_zykinfosById(String id) {
		Map map = new HashMap();
		map.put("id", id);
		return (List<Map>)DBManager.queryFList("model.wcm_zykinfo.getWcminfo_zykinfosById", map);
	}
	
	
	/**
     * 通过id删除记录
     * @param String id
     * @return boolean
     * */
	public static boolean deleteWcminfo_zykinfoById(String id,String site_id) {
		Map map = new HashMap();
		map.put("id", id);  
		map.put("site_id", site_id);  
		return DBManager.insert("model.wcm_zykinfo.deleteWcminfo_zykinfoById", map);
	}
	
	
	/**
     * 插入信息
     * @param Map map
     * @return boolean
     * */
	public static boolean addZykinfoFile(Map map) {
		map.put("id",PublicTableDAO.getIDByTableName(FIELD_TABLE_NAME));
		return DBManager.insert("model.wcm_zykinfo.addZykinfoFile", map);
	}  
	  
	/**
     * 通过id删除记录
     * @param String id
     * @return boolean
     * */
	public static boolean deleteZykinfoFileById(String id) {
		Map map = new HashMap();
		map.put("info_id", id);  
		return DBManager.insert("model.wcm_zykinfo.deleteZykinfoFileById", map);
	}
	
	
	//通过信息info_id和字段ename 得到附件列表
	public static List<WcmZykFile> getZykinfoFileListByInfoId(String info_id,String fieldName) {
		Map map = new HashMap();
		map.put("info_id", info_id);
		map.put("fieldName", fieldName);
		return (List<WcmZykFile>)DBManager.queryFList("model.wcm_zykinfo.getZykinfoFileListByInfoId", map);
	}
	
	//通过id得到附件详细信息
	public static WcmZykFile getZykinfoFileByInfoId(String id) {
		Map map = new HashMap();
		map.put("id", id);
		return (WcmZykFile)DBManager.queryFObj("model.wcm_zykinfo.getZykinfoFileByInfoId", map);
	}
	
}
