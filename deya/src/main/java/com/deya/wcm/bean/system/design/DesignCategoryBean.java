package com.deya.wcm.bean.system.design;
/**
 * @Description:设计页面与栏目的关联
 * @version 1.0
 * @date 2012-01-06 下午01:24:34
 */
public class DesignCategoryBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1022186835853600221L;
	private int id = 0;
	private int cat_id  = 0;
	private int css_id = 0;//关联主题风格ID
	private int template_id  = 0;
	private String design_content = "";				
	private String page_type = "";	
	private int publish_status  = 0;
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
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int templateId) {
		template_id = templateId;
	}
	public String getDesign_content() {
		return design_content;
	}
	public void setDesign_content(String designContent) {
		design_content = designContent;
	}
	public String getPage_type() {
		return page_type;
	}
	public void setPage_type(String pageType) {
		page_type = pageType;
	}
	public int getPublish_status() {
		return publish_status;
	}
	public void setPublish_status(int publishStatus) {
		publish_status = publishStatus;
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
