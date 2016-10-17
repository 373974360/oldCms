package com.deya.wcm.bean.org.user;

public class LoginUserBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7946333247137118617L;
	private int user_id;
	private int dept_id;//部门ID
	private String userlevel_value;//级别数值
	private String user_realname = "";//用户真实姓名
	private String user_aliasname = "";//别名/曾用名
	private String user_name;//登录用户名
	private String ip = "";
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
	public String getUserlevel_value() {
		return userlevel_value;
	}
	public void setUserlevel_value(String userlevelValue) {
		userlevel_value = userlevelValue;
	}
	public String getUser_realname() {
		return user_realname;
	}
	public void setUser_realname(String userRealname) {
		user_realname = userRealname;
	}
	public String getUser_aliasname() {
		return user_aliasname;
	}
	public void setUser_aliasname(String userAliasname) {
		user_aliasname = userAliasname;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
