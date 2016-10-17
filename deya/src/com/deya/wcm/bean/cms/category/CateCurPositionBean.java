package com.deya.wcm.bean.cms.category;

//栏目当前位置Bean

public class CateCurPositionBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7767356558078529129L;
	private int cat_id;
	private String cat_cname = "";
	private String url = "";
	private String jump_url = "";
	public String getJump_url() {
		return jump_url;
	}
	public void setJump_url(String jumpUrl) {
		jump_url = jumpUrl;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public String getCat_cname() {
		return cat_cname;
	}
	public void setCat_cname(String catCname) {
		cat_cname = catCname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
