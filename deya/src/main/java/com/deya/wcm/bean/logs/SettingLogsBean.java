package com.deya.wcm.bean.logs;

public class SettingLogsBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6815990262857142840L;
	private int audit_id;
	private int user_id;
	private String user_name = "";
	private String audit_des = "";
	private String audit_time = "";
	private String ip = "";
	private String app_id = "";
	private String site_id = "";
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getAudit_des() {
		return audit_des;
	}
	public void setAudit_des(String audit_des) {
		this.audit_des = audit_des;
	}
	public int getAudit_id() {
		return audit_id;
	}
	public void setAudit_id(int audit_id) {
		this.audit_id = audit_id;
	}
	public String getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
