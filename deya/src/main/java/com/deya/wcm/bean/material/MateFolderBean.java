package com.deya.wcm.bean.material;

public class MateFolderBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -277595599113942992L;
	private int f_id;
	private int parent_id;
	private String cname;
	private String f_treeposition;
	private String creat_dtime;
	private int user_id ;
	private String app_id;
	private String site_id;
	
	
	public int getF_id() {
		return f_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public String getCname() {
		return cname;
	}
	public String getF_treeposition() {
		return f_treeposition;
	}
	public String getCreat_dtime() {
		return creat_dtime;
	}
	public int getUser_id() {
		return user_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setF_id(int fId) {
		f_id = fId;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public void setF_treeposition(String fTreeposition) {
		f_treeposition = fTreeposition;
	}
	public void setCreat_dtime(String creatDtime) {
		creat_dtime = creatDtime;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	
}