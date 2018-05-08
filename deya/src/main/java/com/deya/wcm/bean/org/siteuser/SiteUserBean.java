package com.deya.wcm.bean.org.siteuser;

public class SiteUserBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5989812128106356118L;

	private String user_id = "";
	private String app_id = "";
	private String site_id = "";
	private String role_ids = "";
	private String role_names = "";
	private String user_name = "";
	private String dept_id = "";
	private String dept_name = "";

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String deptId) {
		dept_id = deptId;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String deptName) {
		dept_name = deptName;
	}
	public String getRole_ids() {
		return role_ids;
	}
	public void setRole_ids(String roleIds) {
		role_ids = roleIds;
	}
	public String getRole_names() {
		return role_names;
	}
	public void setRole_names(String roleNames) {
		role_names = roleNames;
	}
	public String getUser_id() {
		return user_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}

}
