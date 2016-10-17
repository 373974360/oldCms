package com.deya.wcm.bean.cms.category;

public class ZTCategoryBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3288469410148245786L;
	private int id = 0;//类目ID	
	private int zt_cat_id = 0;
	private String zt_cat_name = "";
	private int sort_id = 999;
	private String site_id = "";
	private String app_id = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getZt_cat_id() {
		return zt_cat_id;
	}
	public void setZt_cat_id(int ztCatId) {
		zt_cat_id = ztCatId;
	}
	public String getZt_cat_name() {
		return zt_cat_name;
	}
	public void setZt_cat_name(String ztCatName) {
		zt_cat_name = ztCatName;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
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
