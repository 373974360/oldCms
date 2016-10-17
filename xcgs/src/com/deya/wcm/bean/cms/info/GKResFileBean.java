package com.deya.wcm.bean.cms.info;

public class GKResFileBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7866022055826858640L;
	private int res_id = 0;
	private int info_id = 0;
	private String res_name = "";
	private String res_url = "";
	private int sort_id = 0;
	public int getRes_id() {
		return res_id;
	}
	public void setRes_id(int resId) {
		res_id = resId;
	}
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	public String getRes_name() {
		return res_name;
	}
	public void setRes_name(String resName) {
		res_name = resName;
	}
	public String getRes_url() {
		return res_url;
	}
	public void setRes_url(String resUrl) {
		res_url = resUrl;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	
}
