package com.deya.wcm.bean.system.ware;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WareInfoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4032576061014213078L;
	private int winfo_id = 0;
	private int ware_id = 0;
	private int sort_id = 999;
	private String app_id = "";
	private String site_id = "";
	private List<WareInfos> infos_list = new ArrayList<WareInfos>();
	public List<WareInfos> getInfos_list() {
		return infos_list;
	}
	public void setInfos_list(List<WareInfos> infosList) {
		infos_list = infosList;
	}
	public int getWinfo_id() {
		return winfo_id;
	}
	public void setWinfo_id(int winfoId) {
		winfo_id = winfoId;
	}
	public int getWare_id() {
		return ware_id;
	}
	public void setWare_id(int wareId) {
		ware_id = wareId;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
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
