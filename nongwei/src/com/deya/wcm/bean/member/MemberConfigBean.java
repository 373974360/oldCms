package com.deya.wcm.bean.member;

import java.io.Serializable;

public class MemberConfigBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7634675647033476308L;

	private String config_id = "";
	private int is_allow = 0;
	private int is_check = 0;
	private String reg_pro = "";
	private String close_pro = "";
	private String forbidden_name = "";
	
	public String getConfig_id() {
		return config_id;
	}
	public int getIs_allow() {
		return is_allow;
	}
	public int getIs_check() {
		return is_check;
	}
	public String getReg_pro() {
		return reg_pro;
	}
	public String getClose_pro() {
		return close_pro;
	}
	public String getForbidden_name() {
		return forbidden_name;
	}
	
	public void setConfig_id(String configId) {
		config_id = configId;
	}
	public void setIs_allow(int isAllow) {
		is_allow = isAllow;
	}
	public void setIs_check(int isCheck) {
		is_check = isCheck;
	}
	public void setReg_pro(String regPro) {
		reg_pro = regPro;
	}
	public void setClose_pro(String closePro) {
		close_pro = closePro;
	}
	public void setForbidden_name(String forbiddenName) {
		forbidden_name = forbiddenName;
	}
	
}
