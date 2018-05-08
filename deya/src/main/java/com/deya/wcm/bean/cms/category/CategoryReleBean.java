package com.deya.wcm.bean.cms.category;

public class CategoryReleBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3673701023497034799L;
	private int cat_id;
	private int prv_id;
	private int priv_type;//0：用户	1：用户组
	private String app_id = "";
	private String site_id = "";
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public int getPrv_id() {
		return prv_id;
	}
	public void setPrv_id(int prvId) {
		prv_id = prvId;
	}
	public int getPriv_type() {
		return priv_type;
	}
	public void setPriv_type(int privType) {
		priv_type = privType;
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
	
}
