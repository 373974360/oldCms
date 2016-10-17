package com.deya.wcm.bean.sendInfo;

public class SendConfigBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3209113476237988997L;
	private int id = 0;
	private String site_id = "";
	private String site_domain = "";
	private String site_name = "";
	private int range_type = 0;
	private String allow_site_id = "";
	private int is_zwgk = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getSite_domain() {
		return site_domain;
	}
	public void setSite_domain(String siteDomain) {
		site_domain = siteDomain;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String siteName) {
		site_name = siteName;
	}
	public int getRange_type() {
		return range_type;
	}
	public void setRange_type(int rangeType) {
		range_type = rangeType;
	}
	public String getAllow_site_id() {
		return allow_site_id;
	}
	public void setAllow_site_id(String allowSiteId) {
		allow_site_id = allowSiteId;
	}
	public int getIs_zwgk() {
		return is_zwgk;
	}
	public void setIs_zwgk(int isZwgk) {
		is_zwgk = isZwgk;
	}
}
