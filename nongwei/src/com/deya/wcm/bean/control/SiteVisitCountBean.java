package com.deya.wcm.bean.control;
//站点访问量统计
public class SiteVisitCountBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8413605347167871842L;
	private int id=0;
	private String site_id = "";
	private String app_id = "";
	private int hits = 0;
	private int day_hits = 0;
	private int week_hits = 0;
	private int month_hits = 0;
	private int temp_count = 1;//临时计数
	private boolean is_exist = false;//是否已在库中
	private int click_step = 1;//点击步长
	public int getClick_step() {
		return click_step;
	}
	public void setClick_step(int clickStep) {
		click_step = clickStep;
	}
	public boolean getIs_exist() {
		return is_exist;
	}
	public void setIs_exist(boolean isExist) {
		is_exist = isExist;
	}
	public int getTemp_count() {
		return temp_count;
	}
	public void setTemp_count(int tempCount) {
		temp_count = tempCount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getDay_hits() {
		return day_hits;
	}
	public void setDay_hits(int dayHits) {
		day_hits = dayHits;
	}
	public int getWeek_hits() {
		return week_hits;
	}
	public void setWeek_hits(int weekHits) {
		week_hits = weekHits;
	}
	public int getMonth_hits() {
		return month_hits;
	}
	public void setMonth_hits(int monthHits) {
		month_hits = monthHits;
	}
}
