package com.deya.wcm.services.system.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-7-18 下午02:40:18
 */
public class ConfigRPC {

	public static boolean add(Map<String, String> map, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return ConfigManager.adddConfigs(map,stl);
		}else
			return false;	
	}
	
	public static boolean update(Map<String, String> map, HttpServletRequest request){
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null){
			return ConfigManager.updateConfigs(map,stl);
		}else
			return false;	
	}
	
	public static Map<String, String> getValues(Map<String, String> map){
		return ConfigManager.getConfigMap(map);
	}
	
	
	/**
	 * 设置整个站群页面为灰色
	 * @return	boolean
	 */
	public static boolean setGroupWebPageGrey(){
		return WebPageColorService.setGroupWebPageGrey();
	}
	     
	/**
	 * 设置整个站群页面不为灰色
	 * @return	boolean
	 */
	public static boolean setGroupWebPageNoGrey(){
		return WebPageColorService.setGroupWebPageNoGrey();
	}
	
	/**
	 * 通过站点id设置该站点页面为灰色
	 * @param String site_id
	 * @return	boolean
	 */
	public static boolean setSiteWebPageGrey(String site_id){
		return WebPageColorService.setSiteWebPageGrey(site_id);
	}
	
	/**
	 * 通过站点id设置该站点页面不为灰色
	 * @return	boolean
	 */
	public static boolean setSiteWebPageNoGrey(String site_id){
		return WebPageColorService.setSiteWebPageNoGrey(site_id);
	}
	
	//整个站群用
	//如果页面中包含的是 已经置灰的代码  就设置为不 置灰
	//如果页面中包含的是 不置灰的代码  就设置为 置灰
	public static boolean setGroupWebPageGreyNoGrey(){
		return WebPageColorService.setGroupWebPageGreyNoGrey();
	}
	
	//单个站点用
	//如果页面中包含的是 已经置灰的代码  就设置为不 置灰
	//如果页面中包含的是 不置灰的代码  就设置为 置灰
	public static boolean setSiteWebPageGreyORNoGrey(String site_id){
		return WebPageColorService.setSiteWebPageGreyORNoGrey(site_id);
	}
	
}
