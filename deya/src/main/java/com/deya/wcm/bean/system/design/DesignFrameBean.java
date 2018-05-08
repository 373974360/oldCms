package com.deya.wcm.bean.system.design;
/**
 * @Description:设计工具外框
 * @version 1.0
 * @date 2012-01-06 下午01:24:34
 */
public class DesignFrameBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5377355581519465675L;
	private int id = 0;
	private int frame_id  = 0;
	private String frame_name = "";
	private String frame_content = "";					
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
	public int getFrame_id() {
		return frame_id;
	}
	public void setFrame_id(int frameId) {
		frame_id = frameId;
	}
	public String getFrame_name() {
		return frame_name;
	}
	public void setFrame_name(String frameName) {
		frame_name = frameName;
	}
	public String getFrame_content() {
		return frame_content;
	}
	public void setFrame_content(String frameContent) {
		frame_content = frameContent;
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
