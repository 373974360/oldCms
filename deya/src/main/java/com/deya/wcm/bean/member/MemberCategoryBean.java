package com.deya.wcm.bean.member;

import java.io.Serializable;

public class MemberCategoryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1763371067078587728L;

	private String mcat_id = "";
	private String mcat_name ="";
	private String mcat_memo ="";
	private int sort_id = 999;
	
	public String getMcat_id() {
		return mcat_id;
	}
	public String getMcat_name() {
		return mcat_name;
	}
	public String getMcat_memo() {
		return mcat_memo;
	}
	public int getSort_id() {
		return sort_id;
	}
	
	public void setMcat_id(String mcatId) {
		mcat_id = mcatId;
	}
	public void setMcat_name(String mcatName) {
		mcat_name = mcatName;
	}
	public void setMcat_memo(String mcatMemo) {
		mcat_memo = mcatMemo;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
}
