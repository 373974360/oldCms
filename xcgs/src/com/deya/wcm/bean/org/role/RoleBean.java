package com.deya.wcm.bean.org.role;

public class RoleBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 374115978782437202L;
	private int role_id;
	private int rele_shared = 0;
	private String role_name = "";
	private String app_id = "";//添加角色的应用系统ID
	private String site_id = "";//添加角色的站点ID
	private String a_app_id = "";//可应用该角色的应用系统ID

	private String role_memo = "";
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public int getRele_shared() {
		return rele_shared;
	}
	public void setRele_shared(int rele_shared) {
		this.rele_shared = rele_shared;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getRole_memo() {
		return role_memo;
	}
	public void setRole_memo(String role_memo) {
		this.role_memo = role_memo;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getA_app_id() {
		return a_app_id;
	}
	public void setA_app_id(String a_app_id) {
		this.a_app_id = a_app_id;
	}
}
