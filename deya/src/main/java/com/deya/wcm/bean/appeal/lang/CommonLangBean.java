package com.deya.wcm.bean.appeal.lang;

import java.io.Serializable;

public class CommonLangBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2936891631036288499L;
	
	private String ph_id = "";//常用字ID
	private String ph_title = "";// 常用字短名
	private String ph_content = "";	// 常用字内容
	private int ph_type = 0;	// 类型
	private int sort_id = 999;	// 排序
	
	public String getPh_id() {
		return ph_id;
	}
	public String getPh_title() {
		return ph_title;
	}
	public String getPh_content() {
		return ph_content;
	}
	public int getPh_type() {
		return ph_type;
	}
	public int getSort_id() {
		return sort_id;
	}
	
	public void setPh_id(String phId) {
		ph_id = phId;
	}
	public void setPh_title(String phTitle) {
		ph_title = phTitle;
	}
	public void setPh_content(String phContent) {
		ph_content = phContent;
	}
	public void setPh_type(int phType) {
		ph_type = phType;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
}
