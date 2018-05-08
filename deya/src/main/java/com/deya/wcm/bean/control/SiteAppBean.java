package com.deya.wcm.bean.control;

public class SiteAppBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5722934062833027011L;
	private int sa_id;
	private String site_id = "";
	private String app_id = "";
	private String mark1 = "";
	private String mark2 = "";
	private String mark3 = "";
	private String mark4 = "";
	private String mark5 = "";
	
	public String getMark1() {
		return mark1;
	}
	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}
	public String getMark2() {
		return mark2;
	}
	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}
	public String getMark3() {
		return mark3;
	}
	public void setMark3(String mark3) {
		this.mark3 = mark3;
	}
	public String getMark4() {
		return mark4;
	}
	public void setMark4(String mark4) {
		this.mark4 = mark4;
	}
	public String getMark5() {
		return mark5;
	}
	public void setMark5(String mark5) {
		this.mark5 = mark5;
	}
	public int getSa_id() {
		return sa_id;
	}
	public void setSa_id(int saId) {
		sa_id = saId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
}
