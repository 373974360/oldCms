package com.deya.wcm.services.Log;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.LoginLogsBean;

/**
 * 登录日志RPC类
 * @author liqi
 *
 */
public class LoginLogsRPC {
	
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
