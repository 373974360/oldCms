package com.deya.wcm.bean.appeal.cpUser;

import java.io.Serializable;

public class CPUserReleInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4455554902621237357L;
	private int user_id;
	private int dept_id;
	
	private String user_realname="";	
	private String role_ids="";
	private String role_names = "";
	private String model_ids="";
	private String model_names = "";
	private String dept_treeposition = "";
	public String getDept_treeposition() {
		return dept_treeposition;
	}
	public void setDept_treeposition(String deptTreeposition) {
		dept_treeposition = deptTreeposition;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int deptId) {
		dept_id = deptId;
	}
	public String getUser_realname() {
		return user_realname;
	}
	public void setUser_realname(String userRealname) {
		user_realname = userRealname;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public String getRole_ids() {
		return role_ids;
	}
	public void setRole_ids(String roleIds) {
		role_ids = roleIds;
	}
	public String getRole_names() {
		return role_names;
	}
	public void setRole_names(String roleNames) {
		role_names = roleNames;
	}
	public String getModel_ids() {
		return model_ids;
	}
	public void setModel_ids(String modelIds) {
		model_ids = modelIds;
	}
	public String getModel_names() {
		return model_names;
	}
	public void setModel_names(String modelNames) {
		model_names = modelNames;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
