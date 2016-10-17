package com.deya.wcm.bean.system.template;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-3-24 下午01:14:02
 */
public class TemplateEditBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8209912066873021042L;
	private int id;
	private int t_id;
	private int tcat_id;
	private String t_ename;
	private String t_cname;
	private String t_path;
	private String t_content;	
	private int t_ver;
	private int creat_user;
	private String creat_dtime;
	private int modify_user;
	private String modify_dtime;
	private String app_id;
	private String site_id;
	private String t_status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getT_id() {
		return t_id;
	}
	public int getTcat_id() {
		return tcat_id;
	}
	public String getT_ename() {
		return t_ename;
	}
	public String getT_cname() {
		return t_cname;
	}
	public String getT_path() {
		return t_path;
	}
	public String getT_content() {
		return t_content;
	}
	public int getT_ver() {
		return t_ver;
	}
	public int getCreat_user() {
		return creat_user;
	}
	public String getCreat_dtime() {
		return creat_dtime;
	}
	public int getModify_user() {
		return modify_user;
	}
	public String getModify_dtime() {
		return modify_dtime;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setT_id(int tId) {
		t_id = tId;
	}
	public void setTcat_id(int tcatId) {
		tcat_id = tcatId;
	}
	public void setT_ename(String tEname) {
		if(tEname == null){
			tEname = " ";
		}
		t_ename = tEname;
	}
	public void setT_cname(String tCname) {
		if(tCname == null){
			tCname = " ";
		}
		t_cname = tCname;
	}
	public void setT_path(String tPath) {
		if(tPath == null){
			tPath = " ";
		}
		t_path = tPath;
	}
	public void setT_content(String tContent) {
		if(tContent == null){
			tContent = " ";
		}
		t_content = tContent;
	}
	public void setT_ver(int tVer) {
		t_ver = tVer;
	}
	public void setCreat_user(int creatUser) {
		creat_user = creatUser;
	}
	public void setCreat_dtime(String creatDtime) {
		if(creatDtime == null){
			creatDtime = " ";
		}
		creat_dtime = creatDtime;
	}
	public void setModify_user(int modifyUser) {
		modify_user = modifyUser;
	}
	public void setModify_dtime(String modifyDtime) {
		if(modifyDtime == null){
			modifyDtime = " ";
		}
		modify_dtime = modifyDtime;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getT_status() {
		return t_status;
	}
	public void setT_status(String t_status) {
		this.t_status = t_status;
	}
	
}
