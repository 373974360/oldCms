package com.deya.wcm.bean.member;

import java.io.Serializable;

public class MemberRegisterBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7346556839717611345L;
	
	private String register_id = "";
	private String me_id = "";
	private String me_account = "";
	private String me_password = "";
	private int register_type = 0;
	
	public String getRegister_id() {
		return register_id;
	}
	public String getMe_id() {
		return me_id;
	}
	public String getMe_account() {
		return me_account;
	}
	public String getMe_password() {
		return me_password;
	}
	public int getRegister_type() {
		return register_type;
	}
	
	public void setRegister_id(String registerId) {
		register_id = registerId;
	}
	public void setMe_id(String meId) {
		me_id = meId;
	}
	public void setMe_account(String meAccount) {
		me_account = meAccount;
	}
	public void setMe_password(String mePassword) {
		me_password = mePassword;
	}
	public void setRegister_type(int registerType) {
		register_type = registerType;
	}
}
