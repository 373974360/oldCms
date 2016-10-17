package com.deya.wcm.bean.system.formodel;

public class ModelBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9074766779513834348L;
	private int model_id;
	private String model_ename = "";
	private String model_name = "";
	private String table_name = "";
	private int model_sort = 999;
	private String template_list = "";
	private String template_show = "";
	private String model_icon = "";
	private String add_page = "";
	private String view_page = "";
	private String model_type = "0";
	
	public String getAdd_page() {
		return add_page;
	}

	public void setAdd_page(String addPage) {
		add_page = addPage;
	}

	public String getView_page() {
		return view_page;
	}

	public void setView_page(String viewPage) {
		view_page = viewPage;
	}

	private String app_id = "";
	private int disabled = 0;//是否可用
	
	public String getModel_icon() {
		return model_icon;
	}

	public void setModel_icon(String modelIcon) {
		model_icon = modelIcon;
	}
	public String getModel_ename() {
		return model_ename;
	}

	public void setModel_ename(String modelEname) {
		model_ename = modelEname;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String modelName) {
		model_name = modelName;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String tableName) {
		table_name = tableName;
	}

	public int getModel_sort() {
		return model_sort;
	}

	public void setModel_sort(int modelSort) {
		model_sort = modelSort;
	}

	public String getTemplate_list() {
		return template_list;
	}

	public void setTemplate_list(String templateList) {
		template_list = templateList;
	}

	public String getTemplate_show() {
		return template_show;
	}

	public void setTemplate_show(String templateShow) {
		template_show = templateShow;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String appId) {
		app_id = appId;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public String getModel_memo() {
		return model_memo;
	}

	public void setModel_memo(String modelMemo) {
		model_memo = modelMemo;
	}

	private String model_memo = "";

	public int getModel_id() {
		return model_id;
	}

	public void setModel_id(int modelId) {
		model_id = modelId;
	}

	public String getModel_type() {
		return model_type;
	}

	public void setModel_type(String modelType) {
		model_type = modelType;
	}
}
