package com.deya.wcm.bean.cms.count;

public class InfoCountBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int count = 0;//总数
	private int publish_count = 0;//已发布总数
	private int not_publish_count = 0;//未发布总数	
	private String site_id = "";
	private String site_name = "";
	private int cat_id = 0;
	private String cat_cname = "";
	private int user_id = 0;
	private String user_realname = "";
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public String getUser_realname() {
		return user_realname;
	}
	public void setUser_realname(String userRealname) {
		user_realname = userRealname;
	}
	public int getPublish_count() {
		return publish_count;
	}
	public void setPublish_count(int publishCount) {
		publish_count = publishCount;
	}
	public int getNot_publish_count() {
		return not_publish_count;
	}
	public void setNot_publish_count(int notPublishCount) {
		not_publish_count = notPublishCount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String siteName) {
		site_name = siteName;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public String getCat_cname() {
		return cat_cname;
	}
	public void setCat_cname(String catCname) {
		cat_cname = catCname;
	}
	
}
