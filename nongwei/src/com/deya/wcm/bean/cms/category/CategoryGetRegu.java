package com.deya.wcm.bean.cms.category;
//栏目获取规则表
public class CategoryGetRegu implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1535550491759355273L;
	private int id = 0;
	private int cat_id = 0;
	private int regu_type = 0;//0为共享目录　1为节点
	private String site_ids = "";
	private String cat_ids = "";
	private String site_id_names = "";
	private String cat_id_names = "";
	private String site_id = "";
	private String app_id = "";
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
	public String getSite_id_names() {
		return site_id_names;
	}
	public void setSite_id_names(String siteIdNames) {
		site_id_names = siteIdNames;
	}
	public String getCat_id_names() {
		return cat_id_names;
	}
	public void setCat_id_names(String catIdNames) {
		cat_id_names = catIdNames;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public int getRegu_type() {
		return regu_type;
	}
	public void setRegu_type(int reguType) {
		regu_type = reguType;
	}
	public String getSite_ids() {
		return site_ids;
	}
	public void setSite_ids(String siteIds) {
		site_ids = siteIds;
	}
	public String getCat_ids() {
		return cat_ids;
	}
	public void setCat_ids(String catIds) {
		cat_ids = catIds;
	}
}
