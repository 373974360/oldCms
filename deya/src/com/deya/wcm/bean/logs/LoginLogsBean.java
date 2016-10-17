package com.deya.wcm.bean.logs;

public class LoginLogsBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8241697055835775966L;
	private int audit_id = 0;
	private int user_id = 0;
	private String user_name = "";
	private String audit_des = "";
	private String audit_time = "";
	private String ip = "";
	private String app_id = "";
	private String site_id = "";
	public int getAudit_id() {
		return audit_id;
	}
	public void setAudit_id(int auditId) {
		audit_id = auditId;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getAudit_des() {
		return audit_des;
	}
	public void setAudit_des(String auditDes) {
		audit_des = auditDes;
	}
	public String getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(String auditTime) {
		audit_time = auditTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
}
