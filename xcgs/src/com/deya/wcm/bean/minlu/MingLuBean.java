package com.deya.wcm.bean.minlu;

public class MingLuBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private int template_index = 0;
	private int template_list = 0;
	private int template_content = 0;
	private int reinfo_temp_list = 0;
	private int reinfo_temp_content = 0;
	private int reinfo_temp_pic_list = 0;
	private int reinfo_temp_pic_content = 0;
	private String app_id = "";
	private String site_id = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTemplate_index() {
		return template_index;
	}
	public void setTemplate_index(int templateIndex) {
		template_index = templateIndex;
	}
	public int getTemplate_list() {
		return template_list;
	}
	public void setTemplate_list(int templateList) {
		template_list = templateList;
	}
	public int getTemplate_content() {
		return template_content;
	}
	public void setTemplate_content(int templateContent) {
		template_content = templateContent;
	}
	public int getReinfo_temp_list() {
		return reinfo_temp_list;
	}
	public void setReinfo_temp_list(int reinfoTempList) {
		reinfo_temp_list = reinfoTempList;
	}
	public int getReinfo_temp_content() {
		return reinfo_temp_content;
	}
	public void setReinfo_temp_content(int reinfoTempContent) {
		reinfo_temp_content = reinfoTempContent;
	}
	public int getReinfo_temp_pic_list() {
		return reinfo_temp_pic_list;
	}
	public void setReinfo_temp_pic_list(int reinfoTempPicList) {
		reinfo_temp_pic_list = reinfoTempPicList;
	}
	public int getReinfo_temp_pic_content() {
		return reinfo_temp_pic_content;
	}
	public void setReinfo_temp_pic_content(int reinfoTempPicContent) {
		reinfo_temp_pic_content = reinfoTempPicContent;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
}
