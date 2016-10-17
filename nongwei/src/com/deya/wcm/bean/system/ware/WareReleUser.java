package com.deya.wcm.bean.system.ware;

public class WareReleUser implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1062841104290636541L;
	private int id = 0;
	private int wcat_id = 0;
	private int prv_id = 0;	
	private int priv_type = 0;
	private String app_id = "";
	private String site_id = "";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWcat_id() {
		return wcat_id;
	}
	public void setWcat_id(int wcatId) {
		wcat_id = wcatId;
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
