package com.deya.wcm.bean.org.app;

public class AppBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -441491370699683455L;
	private String app_id = "";
	private String app_name = "";
	private int app_sort;
	private String app_ico = "";
	private String app_memo = "";
	public String getApp_ico() {
		return app_ico;
	}
	public void setApp_ico(String app_ico) {
		this.app_ico = app_ico;
	}
	public String getApp_memo() {
		return app_memo;
	}
	public void setApp_memo(String app_memo) {
		this.app_memo = app_memo;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public int getApp_sort() {
		return app_sort;
	}
	public void setApp_sort(int app_sort) {
		this.app_sort = app_sort;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}			
}
