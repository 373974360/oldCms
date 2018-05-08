package com.deya.wcm.bean.system.template;

public class TemplateCategoryBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3183801738195540001L;
	private int id = 0;
	private int tcat_id = 0;
	private int tclass_id = 0;
	private String tcat_ename = "";
	private String tcat_cname = "";
	private int parent_id = 0;
	private String tcat_position = "";
	private String tcat_memo = "";
	private String app_id = "";
	private String site_id = "";
	private int sort_id = 999;
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public int getTcat_id() {
		return tcat_id;
	}
	public int getTclass_id() {
		return tclass_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public String getTcat_position() {
		return tcat_position;
	}
	public String getTcat_memo() {
		return tcat_memo;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setTcat_id(int tcatId) {
		tcat_id = tcatId;
	}
	public void setTclass_id(int tclassId) {
		tclass_id = tclassId;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public void setTcat_position(String tcatPosition) {
		if(tcatPosition == null){
			tcatPosition = " ";
		}
		tcat_position = tcatPosition;
	}
	public void setTcat_memo(String tcatMemo) {
		if(tcatMemo == null){
			tcatMemo = " ";
		}
		tcat_memo = tcatMemo;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public int getId() {
		return id;
	}
	public String getTcat_ename() {
		return tcat_ename;
	}
	public String getTcat_cname() {
		return tcat_cname;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTcat_ename(String tcatEname) {
		if(tcatEname == null){
			tcatEname = " ";
		}
		tcat_ename = tcatEname;
	}
	public void setTcat_cname(String tcatCname) {
		if(tcatCname == null){
			tcatCname = " ";
		}
		tcat_cname = tcatCname;
	}

}
