package com.deya.wcm.bean.system.assist;

public class SourceBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 917725246185915410L;
	private int source_id;
	private String source_name;
	private String source_initial;
	private String source_url;
	private String logo_path;
	private String app_id;
	private String site_id;
	public int getSource_id() {
		return source_id;
	}
	public String getSource_name() {
		return source_name;
	}
	public String getSource_initial() {
		return source_initial;
	}
	public String getSource_url() {
		return source_url;
	}
	public String getLogo_path() {
		return logo_path;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSource_id(int sourceId) {
		source_id = sourceId;
	}
	public void setSource_name(String sourceName) {
		source_name = sourceName;
	}
	public void setSource_initial(String sourceInitial) {
		source_initial = sourceInitial;
	}
	public void setSource_url(String sourceUrl) {
		source_url = sourceUrl;
	}
	public void setLogo_path(String logoPath) {
		logo_path = logoPath;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	
}
