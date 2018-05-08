package com.deya.wcm.bean.org.role;

public class RoleOptBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8478608025019117515L;
	private int role_opt_id;
	private int role_id;
	private int opt_id;
	public int getOpt_id() {
		return opt_id;
	}
	public void setOpt_id(int opt_id) {
		this.opt_id = opt_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getRole_opt_id() {
		return role_opt_id;
	}
	public void setRole_opt_id(int role_opt_id) {
		this.role_opt_id = role_opt_id;
	}

}
