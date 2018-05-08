package com.deya.wcm.bean.org.dept;

public class DeptManBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1666477749976803799L;
	private int dept_manager_id;
	private int dept_id;
	private int user_id;
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	public int getDept_manager_id() {
		return dept_manager_id;
	}
	public void setDept_manager_id(int dept_manager_id) {
		this.dept_manager_id = dept_manager_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
