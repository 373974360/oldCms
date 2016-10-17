package com.deya.wcm.bean.cms.count;

public class InfoAccessBean implements java.io.Serializable,Cloneable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5805744790380742181L;
	
	private int id;// bigint 20
	private int info_id;// bigint 20
	private String info_title = "";// varchar 250
	private int cat_id;// bigint 20
	private String cat_name="";
    private String app_id ="";
	private String access_time = "";// char 20
	private String access_ip = "";
	private String access_url = "";
	private String access_day = "";
	private String access_month = "";
	private String access_year = "";
	private String site_id  = "";
	private String site_domain = "";
	
	private int icount = 0 ;//用于对应ibatis取得结果的临时存放
	
	private int count = 0 ;
	private String site_name="";
	
	
	
	
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String catName) {
		cat_name = catName;
	}
	public int getIcount() {
		return icount;
	}
	public void setIcount(int icount) {
		this.icount = icount;
	}
	public int getCount() {
		return count;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setSite_name(String siteName) {
		site_name = siteName;
	}
	
	
	
	public int getId() {
		return id;
	}
	public int getInfo_id() {
		return info_id;
	}
	public String getInfo_title() {
		return info_title;
	}
	public int getCat_id() {
		return cat_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getAccess_time() {
		return access_time;
	}
	public String getAccess_ip() {
		return access_ip;
	}
	public String getAccess_url() {
		return access_url;
	}
	public String getAccess_day() {
		return access_day;
	}
	public String getAccess_month() {
		return access_month;
	}
	public String getAccess_year() {
		return access_year;
	}
	public String getSite_id() {
		return site_id;
	}
	public String getSite_domain() {
		return site_domain;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	public void setInfo_title(String infoTitle) {
		info_title = infoTitle;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setAccess_time(String accessTime) {
		access_time = accessTime;
	}
	public void setAccess_ip(String accessIp) {
		access_ip = accessIp;
	}
	public void setAccess_url(String accessUrl) {
		access_url = accessUrl;
	}
	public void setAccess_day(String accessDay) {
		access_day = accessDay;
	}
	public void setAccess_month(String accessMonth) {
		access_month = accessMonth;
	}
	public void setAccess_year(String accessYear) {
		access_year = accessYear;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public void setSite_domain(String siteDomain) {
		site_domain = siteDomain;
	}
}