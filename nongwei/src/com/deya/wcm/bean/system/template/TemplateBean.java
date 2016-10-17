package com.deya.wcm.bean.system.template;

public class TemplateBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8489485260713460551L;
	private int id;
	private int t_id;
	private int t_ver;
	private String app_id;
	private String site_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getT_id() {
		return t_id;
	}
	public int getT_ver() {
		return t_ver;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setT_id(int tId) {
		t_id = tId;
	}
	public void setT_ver(int tVer) {
		t_ver = tVer;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}

}
