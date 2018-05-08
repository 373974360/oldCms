package com.deya.wcm.bean.system.dict;

/**
 * @author 符江波
 * @version 1.0
 * @date 2011-5-31 下午04:35:02
 */
public class DataDictCategoryBean {
	private int id;
	private String dict_cat_id;
	private String dict_cat_name;
	private String dict_cat_memo;
	private int is_sys;
	private String app_id;
	private String site_id;
	
	public int getId() {
		return id;
	}
	public String getDict_cat_id() {
		return dict_cat_id;
	}
	public String getDict_cat_name() {
		return dict_cat_name;
	}
	public String getDict_cat_memo() {
		return dict_cat_memo;
	}
	public int getIs_sys() {
		return is_sys;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDict_cat_id(String dictCatId) {
		dict_cat_id = dictCatId;
	}
	public void setDict_cat_name(String dictCatName) {
		dict_cat_name = dictCatName;
	}
	public void setDict_cat_memo(String dictCatMemo) {
		dict_cat_memo = dictCatMemo;
	}
	public void setIs_sys(int isSys) {
		is_sys = isSys;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
}
