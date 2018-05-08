package com.deya.wcm.bean.org.role;

public class RoleUGroupBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 201420895409757842L;
	private int group_role_id;
	private String role_id = "";
	private String group_id = "";
	private String app_id = "";
	private String site_id = "";
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public int getGroup_role_id() {
		return group_role_id;
	}
	public void setGroup_role_id(int group_role_id) {
		this.group_role_id = group_role_id;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
}
