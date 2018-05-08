package com.deya.wcm.services.system.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.config.ConfigBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.config.ConfigDAO;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-7-18 上午10:14:47
 */
public class ConfigManager implements ISyncCatch{
	
	private static Map<String, Map<String, String>> configMap = new HashMap<String, Map<String, String>>();

	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		configMap.clear();
	}
	
	public static void clearMap()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.system.config.ConfigManager");
	}
	
	public static Map<String, String> getConfigMap(Map<String,String> map){
		Map<String, String> key_value = configMap.get(map.get("group")+"_"+map.get("site_id")+"_"+map.get("app_id"));
		if(key_value != null){
			return key_value;
		}else{
			key_value = new HashMap<String, String>();
			List<ConfigBean> cbList = ConfigDAO.getConfigList(map);
			if(cbList != null && cbList.size() > 0)
			{
				for(ConfigBean cb : cbList){
					key_value.put(cb.getKey(), cb.getValue());
				}
			}else
			{
				key_value.put("upload_allow", "gif,jpg,jpeg,bmp,png,txt,zip,rar,doc,docx,xls,ppt,pdf,swf,flv");
				key_value.put("water_pic", "");
				key_value.put("water_width", "300");
				key_value.put("water_height", "150");
				key_value.put("water_transparent", "0.8");
				key_value.put("water_height", "150");
				key_value.put("normal_width", "600");
				key_value.put("thumb_width", "100");
				key_value.put("is_compress", "1");
			}
			configMap.put(map.get("group")+"_"+map.get("site_id")+"_"+map.get("app_id"), key_value);
		}
		
		return key_value;
	}
	
	public static boolean adddConfigs(Map<String, String> map, SettingLogsBean stl){
		configMap.remove(map.get("group")+"_"+map.get("site_id")+"_"+map.get("app_id"));
		Map<String, String> mapTemp = new HashMap<String, String>();
		mapTemp.put("group", map.get("group"));
		mapTemp.put("site_id", map.get("site_id"));
		mapTemp.put("app_id", map.get("app_id"));
		if(ConfigDAO.deleteConfig(map, stl)){
			Set<String> set = map.keySet();
			for(String key : set){
				if(!key.startsWith("key_")){
					continue;
				}
				try{
				String insertSQL = "@ID,'@GROUP','@KEY','@VALUE','@SITEID','@APPID'";
				insertSQL = insertSQL.replace("@ID", PublicTableDAO.getIDByTableName(PublicTableDAO.SYS_CONFIG_TABLE_NAME)+"");
				insertSQL = insertSQL.replace("@GROUP", map.get("group") == null ? "" : map.get("group"));
				insertSQL = insertSQL.replace("@KEY", key.replaceAll("key_", ""));
				String value = map.get(key);
				if(value == null || value.equals("")){
					continue;
				}
				insertSQL = insertSQL.replace("@VALUE", value);
				insertSQL = insertSQL.replace("@SITEID", map.get("site_id") == null ? "" : map.get("site_id"));
				insertSQL = insertSQL.replace("@APPID", map.get("app_id") == null ? "" : map.get("app_id"));
				ConfigDAO.addConfig(insertSQL, stl);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		clearMap();
		return true;
	}
	
	public static boolean updateConfigs(Map<String, String> map, SettingLogsBean stl){
		configMap.remove(map.get("group")+"_"+map.get("site_id")+"_"+map.get("app_id"));
		Set<String> set = map.keySet();
		for(String key : set){
			if(!key.startsWith("key_")){
				continue;
			}
			Map<String, String> updateMap = new HashMap<String, String>();
			updateMap.put("group", map.get("group"));
			updateMap.put("key", key.replaceAll("key_", ""));
			String value = map.get(key);
			if(value == null || value.equals("")){
				ConfigDAO.deleteConfig(updateMap, stl);
				continue;
			}
			updateMap.put("value", value);
			updateMap.put("site_id", map.get("site_id"));
			updateMap.put("app_id", map.get("app_id"));
			ConfigDAO.updateConfig(updateMap, stl);
		}
		clearMap();
		return true;
	}
	
	
}
