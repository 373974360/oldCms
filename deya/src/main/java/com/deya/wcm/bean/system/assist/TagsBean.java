package com.deya.wcm.bean.system.assist;

public class TagsBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3288221213400180699L;
	private int tag_id;
	private String tag_name;
	private String tag_color;
	private int font_size;
	private int tag_times;
	private String app_id;
	private String site_id;
	public int getTag_id() {
		return tag_id;
	}
	public String getTag_name() {
		return tag_name;
	}
	public String getTag_color() {
		return tag_color;
	}
	public int getFont_size() {
		return font_size;
	}
	public int getTag_times() {
		return tag_times;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setTag_id(int tagId) {
		tag_id = tagId;
	}
	public void setTag_name(String tagName) {
		tag_name = tagName;
	}
	public void setTag_color(String tagColor) {
		tag_color = tagColor;
	}
	public void setFont_size(int fontSize) {
		font_size = fontSize;
	}
	public void setTag_times(int tagTimes) {
		tag_times = tagTimes;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	
}
