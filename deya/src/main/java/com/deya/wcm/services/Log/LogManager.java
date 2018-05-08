package com.deya.wcm.services.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.LoginLogsBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.dao.LogUtil;
import com.deya.wcm.dao.logs.CsLogDAO;
import com.deya.wcm.dao.logs.CsLoginLogDAO;
import com.deya.wcm.services.org.user.UserLogin;
import com.mysql.jdbc.log.LogUtils;

/**
 *  日志管理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 日志管理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class LogManager {
	
	/**
	 * 根据request得到登录对象,并返回日志对象
	 * 	 *   
     * @param HttpServletRequest request     
	 * @return SettingLogsBean
	 */
	public static SettingLogsBean getSettingLogsByRequest(HttpServletRequest request)
	{
		LoginUserBean lub = UserLogin.getUserBySession(request);
		if(lub == null)
		{
			return null;
		}else
		{
			SettingLogsBean slb = new SettingLogsBean();
			slb.setIp(lub.getIp());
			slb.setUser_id(lub.getUser_id());
			slb.setUser_name(lub.getUser_realname());
			return slb;
		}
	}
	
	/**
	 * 取得登录日志对象
	 * @param lub	登录用户信息
	 * @return
	 */
	public static LoginLogsBean getLoginLogsBean(LoginUserBean lub)
	{
		LoginLogsBean llb = new LoginLogsBean();
		llb.setIp(lub.getIp());
		llb.setUser_id(lub.getUser_id());
		llb.setUser_name(lub.getUser_realname());
		
		return llb;
	}
	
	/**
	 * 添加登出日志
	 * @param lub	登录用户对象
	 * @return
	 */
	public static boolean insertLogoutLog(LoginUserBean lub) {
		LoginLogsBean llb = getLoginLogsBean(lub);
		llb.setAudit_des(LogUtil.LOGOUT);
		
		return CsLoginLogDAO.insertLog(llb);
	}
	
	/**
	 * 添加登录日志
	 * @param lub	登录对象信息
	 * @return
	 */
	public static boolean insertLoginLog(LoginUserBean lub) {
		LoginLogsBean llb = getLoginLogsBean(lub);
		llb.setAudit_des(LogUtil.LOGIN);
		
		return CsLoginLogDAO.insertLog(llb);
	}
	
//***********************************************************************************************
//*******************             审计日志操作                       *******************************************
//***********************************************************************************************	
	
	/**
	 * 根据条件取得审计日志
	 * @param mp  取得条件
	 * @return 
	 */
	public static List<SettingLogsBean> searchSettingLog(Map<String, String> mp){
		List<SettingLogsBean> ret = CsLogDAO.searchLogSetting(mp);
		return ret;
	}
	
	/**
	 * 取得搜索到的审计日志条数
	 * @param mp	取得条件
	 * @return
	 */
	public static String searchSettingLogCnt(Map<String, String> mp){
		String ret = CsLogDAO.searchLogSettingCount(mp);
		return ret;
	}
//***********************************************************************************************
//*******************             登录日志操作                       *******************************************
//***********************************************************************************************
	/**
	 * 取得登录日志列表
	 * 这个列表中不包含超级管理员的信息
	 * 超级管理员主要是为公司实施,维护创建的,这个帐号用户是不应该知道的.
	 * @param mp
	 * @return
	 */
	public static List<LoginLogsBean> searchLoginLogs(Map<String, String> mp){
		List<LoginLogsBean> ret;
		ret = CsLoginLogDAO.searchLog(mp);
		return ret;
	}
	
	/**
	 * 取得登录日志列表条数
	 * @param mp
	 * @return
	 */
	public static String searchLoginLogsCnt(Map<String, String> mp){
		String ret = CsLoginLogDAO.searchLogCnt(mp);
		return ret;
	}
	
	public static void main(String arg[]) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("start_num", "0");
		mp.put("page_size", "15");
		mp.put("sortType", "desc");
		
		List lt = searchLoginLogs(mp);
		
		System.out.println(lt.size());
	}
	
//	登录日志功能暂时不需要修改操作  	
//	/**
//	 * 修改登录日志信息
//	 * @param llb	登录日志信息对象
//	 * @return
//	 */
//	public static boolean updateLoginLog(LoginLogsBean llb){
//		return CsLoginLogDAO.updateLog(llb);
//	}
//	登录日志功能暂时不需要删除操作
//	/**
//	 * 删除登录日志信息
//	 * @param mp	登录日志信息对象
//	 * @return
//	 */
//	public static boolean deleteLoginLog(Map<String, String> mp){
//		return CsLoginLogDAO.deleteLog(mp);
//	}
	
}
