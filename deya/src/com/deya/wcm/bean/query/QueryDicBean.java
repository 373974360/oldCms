package com.deya.wcm.bean.query;

public class QueryDicBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7832061740840162338L;
	
	private int dic_id =0;
	private int conf_id =0;
	private String  field_cname="";
	private int  is_selected=0;
	private int  is_query=0;
	private int  is_result=0;
	private int  sort_id =0;
	private String  site_id="";
	
	
	
	public int getDic_id() {
		return dic_id;
	}
	public int getConf_id() {
		return conf_id;
	}
	public String getField_cname() {
		return field_cname;
	}
	public int getIs_selected() {
		return is_selected;
	}
	public int getIs_query() {
		return is_query;
	}
	public int getIs_result() {
		return is_result;
	}
	public int getSort_id() {
		return sort_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setDic_id(int dicId) {
		dic_id = dicId;
	}
	public void setConf_id(int confId) {
		conf_id = confId;
	}
	public void setField_cname(String fieldCname) {
		field_cname = fieldCname;
	}
	public void setIs_selected(int isSelected) {
		is_selected = isSelected;
	}
	public void setIs_query(int isQuery) {
		is_query = isQuery;
	}
	public void setIs_result(int isResult) {
		is_result = isResult;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	
	
}
