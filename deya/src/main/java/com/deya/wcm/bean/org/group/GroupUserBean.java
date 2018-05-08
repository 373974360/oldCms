package com.deya.wcm.bean.org.group;

public class GroupUserBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3619452827109848593L;
	private int group_user_id;
	private int group_id;
	private int user_id;
	private String app_id = "";
	private String site_id = "";
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
	public int getGroup_user_id() {
		return group_user_id;
	}
	public void setGroup_user_id(int group_user_id) {
		this.group_user_id = group_user_id;
	}	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
}
