package com.deya.wcm.dao.system.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.config.ConfigBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-7-18 上午09:52:38
 */
public class ConfigDAO {
	/**
	 * 根据站点ID得到配置信息，用于克隆站点	 
	 * @param String site_id
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public static List<ConfigBean> getSystemConfigListBySiteID(String site_id){
		return DBManager.queryFList("getSystemConfigListBySiteID", site_id);
	}
	
	@SuppressWarnings("unchecked")
	public static List<ConfigBean> getConfigList(Map<String,String> map){
		return DBManager.queryFList("getSystemConfigList", map);
	}

	public static boolean addConfig(String insertSql, SettingLogsBean stl){
		//insertSql
		Map<String, String> map = new HashMap<String, String>();
		map.put("insertSql", insertSql);
		if(DBManager.insert("insertSystemConfig", map)){
			//PublicTableDAO.insertSettingLogs("添加","系统配置","",stl);
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean updateConfig(Map<String, String> map, SettingLogsBean stl){
		if(DBManager.update("updateSystemConfig", map)){
			//PublicTableDAO.insertSettingLogs("修改","系统配置","",stl);
			return true;
		}
		return false;
	}
	
	public static boolean deleteConfig(Map<String, String> map, SettingLogsBean stl){
		if(DBManager.delete("deleteSystenConfig", map)){
			//PublicTableDAO.insertSettingLogs("删除","系统配置","",stl);
			return true;
		}
		return false;
	}
	
}
