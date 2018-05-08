package com.deya.wcm.bean.system.dict;

/**
 * @author 符江波
 * @version 1.0
 * @date 2011-5-31 下午04:35:17
 */
public class DataDictBean {
	private int id;
	private String dict_cat_id;
	private String dict_id;
	private String dict_name;
	private int dict_sort;
	private int is_defult;
	private String app_id;
	private String site_id;
	
	public int getId() {
		return id;
	}
	public String getDict_cat_id() {
		return dict_cat_id;
	}
	public String getDict_id() {
		return dict_id;
	}
	public String getDict_name() {
		return dict_name;
	}
	public int getDict_sort() {
		return dict_sort;
	}
	public int getIs_defult() {
		return is_defult;
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
	public void setDict_id(String dictId) {
		dict_id = dictId;
	}
	public void setDict_name(String dictName) {
		dict_name = dictName;
	}
	public void setDict_sort(int dictSort) {
		dict_sort = dictSort;
	}
	public void setIs_defult(int isDefult) {
		is_defult = isDefult;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}

}
