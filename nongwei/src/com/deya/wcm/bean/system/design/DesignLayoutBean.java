package com.deya.wcm.bean.system.design;
/**
 * @Description:设计工具布局
 * @version 1.0
 * @date 2012-01-06 下午01:24:34
 */
public class DesignLayoutBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2906157949026416738L;
	private int id = 0;
	private int layout_id = 0;
	private String layout_name = "";
	private String layout_content = "";
	private String thumb_url = "";
	private int weight = 0;
	private String app_id = "";
	private String site_id = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLayout_id() {
		return layout_id;
	}
	public void setLayout_id(int layoutId) {
		layout_id = layoutId;
	}
	public String getLayout_name() {
		return layout_name;
	}
	public void setLayout_name(String layoutName) {
		layout_name = layoutName;
	}
	public String getLayout_content() {
		return layout_content;
	}
	public void setLayout_content(String layoutContent) {
		layout_content = layoutContent;
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
