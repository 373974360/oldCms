package com.deya.wcm.bean.org.group;

public class GroupBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8260908770697656610L;
	private int group_id;
	private int parent_id;//保留
	private String group_name = "";
	private String app_id = "";
	private String site_id = "";
	private String group_memo = "";
	private String user_ids = "";
	public String getUser_ids() {
		return user_ids;
	}
	public void setUser_ids(String user_ids) {
		this.user_ids = user_ids;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getGroup_memo() {
		return group_memo;
	}
	public void setGroup_memo(String group_memo) {
		this.group_memo = group_memo;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
}
