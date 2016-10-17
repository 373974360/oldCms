package com.deya.wcm.bean.sendInfo;
//接收栏目配置
public class ReceiveCatConf implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9004072344337605935L;
	private int id = 0;
	private String site_id = "";
	private int cat_id = 0;
	private int sort_id = 0;
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
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	
}
