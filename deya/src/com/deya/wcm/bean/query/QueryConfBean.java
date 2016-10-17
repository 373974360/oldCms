package com.deya.wcm.bean.query;

public class QueryConfBean implements java.io.Serializable{

	/**
	 *  自定义查询业务bean.
	 * <p>Title: CicroDate</p>
	 * <p>Description:  自定义查询业务bean.</p>
	 * <p>Copyright: Copyright (c) 2011-12-28</p>
	 * <p>Company: Cicro</p>
	 * @author zhangqiang
	 * @version 1.0
	 * * 
	 */
	private static final long serialVersionUID = -1520737041025282140L;
	
	private int conf_id = 0;
	private String  conf_title="";
	private String  conf_description="";
	private int t_list_id = 0;
	private int t_content_id = 0;

	private String  site_id="";
	private String  app_id="";
	
	
	
	public int getConf_id() {
		return conf_id;
	}
	public String getConf_title() {
		return conf_title;
	}
	public String getConf_description() {
		return conf_description;
	}
	public String getSite_id() {
		return site_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setConf_id(int confId) {
		conf_id = confId;
	}
	public void setConf_title(String confTitle) {
		conf_title = confTitle;
	}
	public void setConf_description(String confDescription) {
		conf_description = confDescription;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public int getT_list_id() {
		return t_list_id;
	}
	public int getT_content_id() {
		return t_content_id;
	}
	public void setT_list_id(int tListId) {
		t_list_id = tListId;
	}
	public void setT_content_id(int tContentId) {
		t_content_id = tContentId;
	}
	
}
