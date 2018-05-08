package com.deya.wcm.bean.system.design;
/**
 * @Description:设计工具内容样式
 * @version 1.0
 * @date 2012-01-06 下午01:24:34
 */
public class DesignStyleBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -620424896356897870L;
	private int id = 0;
	private int style_id  = 0;
	private String style_name = "";
	private String class_name = "";
	private String thumb_url = "";
	private int weight  = 0;
	private String app_id = "";
	private String site_id = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStyle_id() {
		return style_id;
	}
	public void setStyle_id(int styleId) {
		style_id = styleId;
	}
	public String getStyle_name() {
		return style_name;
	}
	public void setStyle_name(String styleName) {
		style_name = styleName;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String className) {
		class_name = className;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumbUrl) {
		thumb_url = thumbUrl;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
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
