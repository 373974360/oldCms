package com.deya.wcm.bean.appeal.purpose;

public class PurposeBean implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8581745683360549586L;
	
	private int pur_id;
	private String pur_name;
	private int sort_id=999;
	
	
    public int getPur_id() {
		return pur_id;
	}
	public String getPur_name() {
		return pur_name;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setPur_id(int purId) {
		pur_id = purId;
	}
	public void setPur_name(String purName) {
		pur_name = purName;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	

}
