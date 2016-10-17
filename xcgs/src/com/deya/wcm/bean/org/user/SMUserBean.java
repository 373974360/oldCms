package com.deya.wcm.bean.org.user;
/**
 * 少量的用户信息Bean
 */
public class SMUserBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8417530692366924944L;
	private int user_id;
	private int dept_id;//部门ID
	private String user_realname = "";//用户真实姓名
	private String dept_treeposition = "";
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
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
	public String getDept_treeposition() {
		return dept_treeposition;
	}
	public void setDept_treeposition(String deptTreeposition) {
		dept_treeposition = deptTreeposition;
	}
}
