package com.deya.wcm.bean.cms.category;

import java.util.ArrayList;
import java.util.List;

public class SMCategoryBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4476253177179214909L;
	private int cat_id = 0;
	private int parent_id = 0;
	private String cat_position = "";	
	private String cat_cname = "";
	private String site_id = "";
	private List<SMCategoryBean> sm_list = new ArrayList<SMCategoryBean>();
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public String getCat_position() {
		return cat_position;
	}
	public void setCat_position(String catPosition) {
		cat_position = catPosition;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getCat_cname() {
		return cat_cname;
	}
	public void setCat_cname(String catCname) {
		cat_cname = catCname;
	}
	public List<SMCategoryBean> getSm_list() {
		return sm_list;
	}
	public void setSm_list(List<SMCategoryBean> smList) {
		sm_list = smList;
	}
	
}
