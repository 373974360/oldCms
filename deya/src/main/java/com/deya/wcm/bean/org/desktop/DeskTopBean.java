package com.deya.wcm.bean.org.desktop;

public class DeskTopBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private int user_id = 0;
	private String app_type = "";
	private String k_v = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public String getApp_type() {
		return app_type;
	}
	public void setApp_type(String appType) {
		app_type = appType;
	}
	public String getK_v() {
		return k_v;
	}
	public void setK_v(String kV) {
		k_v = kV;
	}
	
}
