package com.deya.wcm.bean.query;

public class QueryItemBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1660757468238567665L;
	
	public int item_id =0;
	private int conf_id =0;
	private int item_key =0;
	private String item_value ="";
	private String site_id ="";
	
	
	
	public int getItem_id() {
		return item_id;
	}
	public int getConf_id() {
		return conf_id;
	}
	public int getItem_key() {
		return item_key;
	}
	public String getItem_value() {
		return item_value;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setItem_id(int itemId) {
		item_id = itemId;
	}
	public void setConf_id(int confId) {
		conf_id = confId;
	}
	public void setItem_key(int itemKey) {
		item_key = itemKey;
	}
	public void setItem_value(String itemValue) {
		item_value = itemValue;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}

}
