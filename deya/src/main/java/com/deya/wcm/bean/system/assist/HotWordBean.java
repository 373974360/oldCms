package com.deya.wcm.bean.system.assist;

public class HotWordBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2580836618111892016L;
	private int hot_id = 0;
	private String hot_name = "";
	private String hot_url = "";
	private String app_id = "";
	private String site_id = "";
	public int getHot_id() {
		return hot_id;
	}
	public void setHot_id(int hotId) {
		hot_id = hotId;
	}
	public String getHot_name() {
		return hot_name;
	}
	public void setHot_name(String hotName) {
		hot_name = hotName;
	}
	public String getHot_url() {
		return hot_url;
	}
	public void setHot_url(String hotUrl) {
		hot_url = hotUrl;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	
	public void show(){
		System.out.println("hot_id = "+hot_id);
		System.out.println("hot_name = "+hot_name);
		System.out.println("hot_url = "+hot_url);
		System.out.println("app_id = "+app_id);
		System.out.println("site_id = "+site_id);
	}
}
