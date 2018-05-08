/**
 * 
 */
package com.deya.wcm.dao.logs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.db.DBManager;

/**
 * 设置审计日志表.
 * <p>Title: CicroDate</p>
 * <p>Description:设置操作类审计日志表</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author wanglei
 * @version 1.0
 * * 
 */
public class CsLogDAO {
	

	/**
	 * 
	 */
	public CsLogDAO() {
		// TODO Auto-generated constructor stub
		
	}
	
	/**
     * 得到所有工作流列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List getAllLogSetting(Map<String,String> m)
	{
		return DBManager.queryFList("getAllLogSetting", m);
	}
	
	/**
	 * 得到所有审计日志总数
	 * @param m
	 * @return
	 */
	public static String getLogSettingCount(Map<String,String> m)
	{
		return DBManager.getString("getLogSettingCount", m);
	}
	
	/**
	 * 得到  指定的  审计日志
	 * @param audit_id
	 * @return
	 */
	public static SettingLogsBean getLogSettingBean(int audit_id){
		return (SettingLogsBean)DBManager.queryFObj("getLogSettingBean", audit_id+"");
	}
	
	/**
	 * 添加 新的审计日志
	 * @param slb
	 * @return
	 */
	public static boolean insertLogSetting(SettingLogsBean slb){
		return DBManager.insert("insert_LogSetting", slb);
	}
	
	/**
	 * 修改  指定的  审计日志
	 * @param slb
	 * @return
	 */
	public static boolean updateLogSetting(SettingLogsBean slb){
		return DBManager.update("update_LogSetting", slb);
	}
	
	/**
	 * 修改  指定的  审计日志
	 * @param audit_ids
	 * @return
	 */
	public static boolean deleteLogSetting(String audit_ids){
		Map<String,String> m = new HashMap<String,String>();
		m.put("audit_ids", audit_ids);
		return DBManager.delete("delete_LogSetting", m);
	}
	
	/**
	 * 取得检索日志信息的条数
	 * @param mp
	 * @return
	 */
	public static String searchLogSettingCount(Map<String, String> mp) {
		return DBManager.getString("searchLogSettingCount", mp);
	}
	
	/**
	 * 检索日志信息
	 * @param mp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<SettingLogsBean> searchLogSetting(Map<String, String> mp) {
		return DBManager.queryFList("searchLogSetting", mp);
	}

	public static void main(String []args) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("page_size", "20");
		mp.put("start_num", "20");
		List lt = searchLogSetting(mp);
		System.out.println(lt.size());
	}
}
