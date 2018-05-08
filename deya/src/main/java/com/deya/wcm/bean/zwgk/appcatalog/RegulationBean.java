package com.deya.wcm.bean.zwgk.appcatalog;
//公开应用目录汇聚规则
public class RegulationBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private int cata_id = 0;
	private int regu_type = 0;//0为共享目录　1为节点
	private String site_ids = "";
	private String cat_ids = "";
	private String site_id_names = "";
	private String cat_id_names = "";
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
	public int getCata_id() {
		return cata_id;
	}
	public void setCata_id(int cataId) {
		cata_id = cataId;
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
