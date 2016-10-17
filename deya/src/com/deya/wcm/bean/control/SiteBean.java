package com.deya.wcm.bean.control;

import java.util.List;

public class SiteBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7192101090521919673L;
	private String site_id = "";
	private String site_domain = "";
	private String parent_id ;
	private int server_id;
	private int dept_id;
	private String site_name = "";
	private String site_cdkey = "";
	private String site_createtime = "";
	private String site_pausetime = "";
	private String site_position = "";
	private int site_status = 0;//0：正常	1：暂停	-1：为删除
	private int site_sort = 0;
	private String sgroup_id = "";
	private String site_demo = "";
	private String site_path = "";
	private String site_manager = "";//站点管理员，多个用,号隔开
	private List<SiteBean> child_list ;
	private String clone_site_id = "";//需要克隆的目标站点ID，表中无此字段
	
	public String getClone_site_id() {
		return clone_site_id;
	}
	public void setClone_site_id(String cloneSiteId) {
		clone_site_id = cloneSiteId;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}	
	public int getServer_id() {
		return server_id;
	}
	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}
	public String getSgroup_id() {
		return sgroup_id;
	}
	public void setSgroup_id(String sgroup_id) {
		this.sgroup_id = sgroup_id;
	}
	public String getSite_cdkey() {
		return site_cdkey;
	}
	public void setSite_cdkey(String site_cdkey) {
		this.site_cdkey = site_cdkey;
	}
	public String getSite_createtime() {
		return site_createtime;
	}
	public void setSite_createtime(String site_createtime) {
		this.site_createtime = site_createtime;
	}
	public String getSite_demo() {
		return site_demo;
	}
	public void setSite_demo(String site_demo) {
		this.site_demo = site_demo;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getSite_pausetime() {
		return site_pausetime;
	}
	public void setSite_pausetime(String site_pausetime) {
		this.site_pausetime = site_pausetime;
	}
	public String getSite_position() {
		return site_position;
	}
	public void setSite_position(String site_position) {
		this.site_position = site_position;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	public int getSite_sort() {
		return site_sort;
	}
	public void setSite_sort(int site_sort) {
		this.site_sort = site_sort;
	}
	public int getSite_status() {
		return site_status;
	}
	public void setSite_status(int site_status) {
		this.site_status = site_status;
	}
	public String getSite_domain() {
		return site_domain;
	}
	public void setSite_domain(String site_domain) {
		this.site_domain = site_domain;
	}
	public String getSite_path() {
		return site_path;
	}
	public void setSite_path(String site_path) {
		this.site_path = site_path;
	}
	public String getSite_manager() {
		return site_manager;
	}
	public void setSite_manager(String site_manager) {
		this.site_manager = site_manager;
	}
	public List<SiteBean> getChild_list() {
		return child_list;
	}
	public void setChild_list(List<SiteBean> childList) {
		child_list = childList;
	}
	
}
