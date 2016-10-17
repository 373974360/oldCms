package com.deya.wcm.dao.logs;

import java.util.List;
import java.util.Map;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.LoginLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

public class CsLoginLogDAO {
	
	/**
	 * 添加登录日志
	 * @param llb 登录日志对象
	 * @return	
	 */
	public static boolean insertLog(LoginLogsBean llb){
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.LOGINLOGS_TABLE_NAME);
		llb.setAudit_id(id);
		llb.setAudit_time(DateUtil.getCurrentDateTime()); // 添加登录日志时间
		return DBManager.insert("insert_LogLogin",llb);
	}
	
	/**
	 * 删除登录日志
	 * @param mp	删除条件
	 * @return
	 */
	public static boolean deleteLog(Map<String, String> mp) {
		return DBManager.delete("delete_LogLogin", mp);
	}
	
	/**
	 * 修改登录日志
	 * @param llb
	 * @return
	 */
	public static boolean updateLog(LoginLogsBean llb){
		return DBManager.update("update_LogLogin", llb);
	}
	
	/**
	 * 根据条件检索登录日志(如果条件的map中没有任何信息,则返回全部日志)
	 * @param mp	检索条件
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<LoginLogsBean> searchLog(Map<String, String> mp) {
		return DBManager.queryFList("searchLogLoginBean", mp);
	}
	
	/**
	 * 取得检索到的登录日志的条数
	 * @param mp	检索条件
	 * @return
	 */
	public static String searchLogCnt(Map<String, String> mp) {
		return (String)DBManager.queryFObj("searchLogLoginBeanCnt", mp);
	}
	
	/**
	 * 根据登录日志信息id取得登录日志
	 * @param audit_id
	 * @return
	 */
	public static LoginLogsBean getLog(String audit_id) {
		return (LoginLogsBean)DBManager.queryFObj("getLogLoginBean", audit_id);
	}
}
