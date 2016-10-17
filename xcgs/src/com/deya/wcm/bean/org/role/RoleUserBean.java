package com.deya.wcm.bean.org.role;

public class RoleUserBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3555475247214674303L;
	private int user_role_id;
	private String role_id = "";
	private String user_id = "";
	private String app_id = "";//添加角色的应用系统ID
	private String site_id = "";//添加角色的站点ID
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	
	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}
