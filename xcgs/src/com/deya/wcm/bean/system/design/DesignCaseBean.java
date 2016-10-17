package com.deya.wcm.bean.system.design;
/**
 * @Description:设计工具方案
 * @version 1.0
 * @date 2012-01-06 下午01:24:34
 */
public class DesignCaseBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4871009405992889090L;
	private int id = 0;
	private int case_id  = 0;
	private int css_id = 0;//关联主题风格ID
	private String case_name = "";
	private String case_content = "";				
	private String thumb_url = "";
	private int weight  = 0;
	private String app_id = "";
	private String site_id = "";
	public int getCss_id() {
		return css_id;
	}
	public void setCss_id(int cssId) {
		css_id = cssId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCase_id() {
		return case_id;
	}
	public void setCase_id(int caseId) {
		case_id = caseId;
	}
	public String getCase_name() {
		return case_name;
	}
	public void setCase_name(String caseName) {
		case_name = caseName;
	}
	public String getCase_content() {
		return case_content;
	}
	public void setCase_content(String caseContent) {
		case_content = caseContent;
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
