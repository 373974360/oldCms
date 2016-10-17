package com.deya.wcm.bean.org.dept;

public class DeptLevelBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 335862833803416495L;
	private int deptlevel_id;//ID
	private int deptlevel_value;//部门级别
	private String deptlevel_name = "";//部门全名称
	private String deptlevel_memo = "";//备注
	private int is_delete = 0;//是否已删除
	public int getDeptlevel_id() {
		return deptlevel_id;
	}
	public void setDeptlevel_id(int deptlevel_id) {
		this.deptlevel_id = deptlevel_id;
	}
	public String getDeptlevel_memo() {
		return deptlevel_memo;
	}
	public void setDeptlevel_memo(String deptlevel_memo) {
		this.deptlevel_memo = deptlevel_memo;
	}
	public String getDeptlevel_name() {
		return deptlevel_name;
	}
	public void setDeptlevel_name(String deptlevel_name) {
		this.deptlevel_name = deptlevel_name;
	}
	public int getDeptlevel_value() {
		return deptlevel_value;
	}
	public void setDeptlevel_value(int deptlevel_value) {
		this.deptlevel_value = deptlevel_value;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
}
