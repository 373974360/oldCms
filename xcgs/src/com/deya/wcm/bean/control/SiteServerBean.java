package com.deya.wcm.bean.control;

public class SiteServerBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8613162006670129996L;
	private int server_id;
	private String server_name = "";
	private String server_ip = "";
	private int server_type;
	private String server_memo = "";
	public int getServer_id() {
		return server_id;
	}
	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}
	public String getServer_ip() {
		return server_ip;
	}
	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}
	public String getServer_memo() {
		return server_memo;
	}
	public void setServer_memo(String server_memo) {
		this.server_memo = server_memo;
	}
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	public int getServer_type() {
		return server_type;
	}
	public void setServer_type(int server_type) {
		this.server_type = server_type;
	}
	
}	
