package com.deya.wcm.bean.system.ware;

import java.io.Serializable;

public class WareInfos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6336369043896828862L;
	private int id = 0;
	private int ware_id = 0;
	private int winfo_id = 0;
	private String title = "";
	private String subtitle = "";
	private String title_color = "";
	private String description = "";
	private String thumb_url = "";
	private String content_url = "";
	private int sort_id = 999;
	private String publish_dtime = "";		
	private String app_id = "";
	private String site_id = "";
	protected String pre_title = "";// varchar 250
	
	public String getPre_title() {
		return pre_title;
	}
	public void setPre_title(String preTitle) {
		pre_title = preTitle;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWare_id() {
		return ware_id;
	}
	public void setWare_id(int wareId) {
		ware_id = wareId;
	}
	public int getWinfo_id() {
		return winfo_id;
	}
	public void setWinfo_id(int winfoId) {
		winfo_id = winfoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getTitle_color() {
		return title_color;
	}
	public void setTitle_color(String titleColor) {
		title_color = titleColor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumbUrl) {
		thumb_url = thumbUrl;
	}
	public String getContent_url() {
		return content_url;
	}
	public void setContent_url(String contentUrl) {
		content_url = contentUrl;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public String getPublish_dtime() {
		return publish_dtime;
	}
	public void setPublish_dtime(String publishDtime) {
		publish_dtime = publishDtime;
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
