package com.deya.wcm.bean.control;

import java.util.List;

public class SiteGroupBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5359757397691218711L;
	private String sgroup_id = "";
	private String parent_id = "";//默认为0
	private String sgroup_name = "";
	private String sgroup_ip = "";//可以被其它网站群互访的IP
	private String sgroup_prot = "";//RMI端口
	private int dept_id;
	private int sgroup_sort;
	private String sgroup_memo = "";
	private List<SiteGroupBean> child_menu_list;
	
	public int getDept_id() {
		return dept_id; 
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getSgroup_id() {
		return sgroup_id;
	}
	public void setSgroup_id(String sgroup_id) {
		this.sgroup_id = sgroup_id;
	}
	public String getSgroup_ip() {
		return sgroup_ip;
	}
	public void setSgroup_ip(String sgroup_ip) {
		this.sgroup_ip = sgroup_ip;
	}
	public String getSgroup_memo() {
		return sgroup_memo;
	}
	public void setSgroup_memo(String sgroup_memo) {
		this.sgroup_memo = sgroup_memo;
	}
	public String getSgroup_name() {
		return sgroup_name;
	}
	public void setSgroup_name(String sgroup_name) {
		this.sgroup_name = sgroup_name;
	}
	public String getSgroup_prot() {
		return sgroup_prot;
	}
	public void setSgroup_prot(String sgroup_prot) {
		this.sgroup_prot = sgroup_prot;
	}
	public int getSgroup_sort() {
		return sgroup_sort;
	}
	public void setSgroup_sort(int sgroup_sort) {
		this.sgroup_sort = sgroup_sort;
	}
	public List<SiteGroupBean> getChild_menu_list() {
		return child_menu_list;
	}
	public void setChild_menu_list(List<SiteGroupBean> childMenuList) {
		child_menu_list = childMenuList;
	}
	
}
