package com.deya.wcm.bean.system.design;
/**
 * @Description:设计工具整体风格
 * @version 1.0
 * @date 2012-01-06 下午01:24:34
 */
public class DesignCSSBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5713248001058843094L;
	private int id = 0;
	private int css_id = 0;
	private String css_ename = "";
	private String css_name = "";
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
	public int getCss_id() {
		return css_id;
	}
	public void setCss_id(int cssId) {
		css_id = cssId;
	}
	public String getCss_ename() {
		return css_ename;
	}
	public void setCss_ename(String cssEname) {
		css_ename = cssEname;
	}
	public String getCss_name() {
		return css_name;
	}
	public void setCss_name(String cssName) {
		css_name = cssName;
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
