package com.deya.wcm.bean.control;

public class SiteConfigBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3695837133612316524L;
	private int config_id;
	private String site_id = "";
	private String config_key = "";
	private String config_value = "";
	
	public int getConfig_id() {
		return config_id;
	}
	public void setConfig_id(int config_id) {
		this.config_id = config_id;
	}
	public String getConfig_key() {
		return config_key;
	}
	public void setConfig_key(String config_key) {
		this.config_key = config_key;
	}
	public String getConfig_value() {
		return config_value;
	}
	public void setConfig_value(String config_value) {
		this.config_value = config_value;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
}
