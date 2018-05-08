package com.deya.wcm.bean.org.user;
//用户帐号
public class UserRegisterBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4635985492419904717L;
	private int register_id = 0;
	private int user_id;
	private String username = "";
	private String password = "";
	private String user_realname = "";
	private int register_status = -1;//0：正常 	1：停用   -1待开通 
	public String getUser_realname() {
		return user_realname;
	}
	public void setUser_realname(String userRealname) {
		user_realname = userRealname;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRegister_id() {
		return register_id;
	}
	public void setRegister_id(int register_id) {
		this.register_id = register_id;
	}
	public int getRegister_status() {
		return register_status;
	}
	public void setRegister_status(int register_status) {
		this.register_status = register_status;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
