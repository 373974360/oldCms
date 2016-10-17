package com.deya.wcm.bean.system.ware;

import java.io.Serializable;

/**
 * 信息组件（区块）分类Bean
 * @author Administrator
 * liqi
 */
public class WareCategoryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1040597672816207753L;

	private String id = "";
	private String wcat_id = "";//分类ID
	private String parent_id = "";//父ID
	private String wcat_name = "";//分类名称
	private String wcat_position = "";//类目路径
	private int wcat_level = 1;//深度级别
	private String wcat_memo = "";//分类描述
	private int sort_id = 999;
	private String app_id = "";
	private String site_id = "";
	
	public String getWcat_id() {
		return wcat_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public String getWcat_name() {
		return wcat_name;
	}
	public String getWcat_position() {
		return wcat_position;
	}
	public String getWcat_memo() {
		return wcat_memo;
	}
	public int getSort_id() {
		return sort_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	
	public void setWcat_id(String wcatId) {
		wcat_id = wcatId;
	}
	public void setParent_id(String parentId) {
		parent_id = parentId;
	}
	public void setWcat_name(String wcatName) {
		wcat_name = wcatName;
	}
	public void setWcat_position(String wcatPosition) {
		wcat_position = wcatPosition;
	}
	public void setWcat_memo(String wcatMemo) {
		wcat_memo = wcatMemo;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getWcat_level() {
		return wcat_level;
	}
	public void setWcat_level(int wcatLevel) {
		wcat_level = wcatLevel;
	}
	
	
}
