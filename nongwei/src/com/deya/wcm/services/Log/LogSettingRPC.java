/**
 * 审计日志的RPC
 */
package com.deya.wcm.services.Log;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.LoginLogsBean;
import com.deya.wcm.bean.logs.SettingLogsBean;

/**
 * @author wanglei
 * 
 */
public class LogSettingRPC {

	/**
	 * 分页获取审核日志信息
	 * 
	 * @return
	 */
	public static List<SettingLogsBean> getLogSetting(Map<String, String> m) {
		return LogManager.searchSettingLog(m);
	}

	/**
	 * 得到所有审计日志总数
	 * 
	 * @param m
	 * @return
	 */
	public static String getLogSettingCount(Map<String, String> m) {
		return LogManager.searchSettingLogCnt(m);
	}
	
	
	/**
	 * 根据条件取得登录日志信息
	 * @param mp	取得登录日志的条件
	 * @return
	 */
	public static List<LoginLogsBean> searchLoginLogs(Map<String, String> mp) {
		return LogManager.searchLoginLogs(mp);
	}
	
	/**
	 * 根据条件取得登录日志信息条数
	 * @param mp	登录日志条数
	 * @return
	 */
	public static String searchLoginLogsCnt(Map<String, String> mp) {
		return LogManager.searchLoginLogsCnt(mp);
	}
}
