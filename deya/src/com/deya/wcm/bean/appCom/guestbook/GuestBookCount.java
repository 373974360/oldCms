package com.deya.wcm.bean.appCom.guestbook;

import java.io.Serializable;

public class GuestBookCount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6608541640393760804L;
	private int gbs_id = 0;	
	private int cat_id = 0;
	private String site_id = "";
	private String title= "";
	private int count = 0;
	private int reply_count = 0;
	private int publish_count = 0;
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public int getGbs_id() {
		return gbs_id;
	}
	public void setGbs_id(int gbsId) {
		gbs_id = gbsId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getReply_count() {
		return reply_count;
	}
	public void setReply_count(int replyCount) {
		reply_count = replyCount;
	}
	public int getPublish_count() {
		return publish_count;
	}
	public void setPublish_count(int publishCount) {
		publish_count = publishCount;
	}
	
	
}
