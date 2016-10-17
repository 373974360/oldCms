package com.deya.wcm.bean.cms.category;

public class CategoryModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7292527114366745514L;
	private int cat_model_id = 0;
	private int cat_id = 0;
	private int model_id = 0;
	private int template_content = 0;
	private String app_id = "";
	private String site_id = "";
	private int isAdd = 1;
	public int getCat_model_id() {
		return cat_model_id;
	}
	public void setCat_model_id(int catModelId) {
		cat_model_id = catModelId;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public int getTemplate_content() {
		return template_content;
	}
	public void setTemplate_content(int templateContent) {
		template_content = templateContent;
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
	public int getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(int isAdd) {
		this.isAdd = isAdd;
	}
}
