package com.deya.wcm.bean.org.role;

public class RoleAppBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4077817677544556645L;
	private int role_app_id;
	private int role_id;
	private String app_id = "";
	private String site_id = "";
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public int getRole_app_id() {
		return role_app_id;
	}
	public void setRole_app_id(int role_app_id) {
		this.role_app_id = role_app_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
}
