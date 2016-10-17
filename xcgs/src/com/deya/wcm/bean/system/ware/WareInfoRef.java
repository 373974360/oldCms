package com.deya.wcm.bean.system.ware;

import java.io.Serializable;

/**
 * 信息推荐Bean（推荐到标签）
 * */

public class WareInfoRef implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3527704703696990843L;
	private int ref_id = 0;
	private int ware_id = 0;
	private int info_id = 0;
	private String update_dtime = "";
	private int ref_user = 0;
	private String app_id = "";
	private String site_id = "";
	public int getRef_id() {
		return ref_id;
	}
	public void setRef_id(int refId) {
		ref_id = refId;
	}
	public int getWare_id() {
		return ware_id;
	}
	public void setWare_id(int wareId) {
		ware_id = wareId;
	}
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	public String getUpdate_dtime() {
		return update_dtime;
	}
	public void setUpdate_dtime(String updateDtime) {
		update_dtime = updateDtime;
	}
	public int getRef_user() {
		return ref_user;
	}
	public void setRef_user(int refUser) {
		ref_user = refUser;
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
