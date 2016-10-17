package com.deya.wcm.bean.system.assist;

public class AuthorBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8732526258945336140L;
	private int author_id;
	private String author_name;
	private String author_initial;
	private String author_url;
	private String app_id;
	private String site_id;
	public int getAuthor_id() {
		return author_id;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public String getAuthor_initial() {
		return author_initial;
	}
	public String getAuthor_url() {
		return author_url;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setAuthor_id(int authorId) {
		author_id = authorId;
	}
	public void setAuthor_name(String authorName) {
		author_name = authorName;
	}
	public void setAuthor_initial(String authorInitial) {
		author_initial = authorInitial;
	}
	public void setAuthor_url(String authorUrl) {
		author_url = authorUrl;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	
}
