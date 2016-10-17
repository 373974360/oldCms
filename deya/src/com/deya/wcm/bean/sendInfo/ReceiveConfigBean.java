package com.deya.wcm.bean.sendInfo;

public class ReceiveConfigBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String site_id = "";
	private String user_name = "";
	private String pass_word = "";
	private int receive_status = 0;
	private int sort_id = 0;
	private String site_name = "";
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String siteName) {
		site_name = siteName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getPass_word() {
		return pass_word;
	}
	public void setPass_word(String passWord) {
		pass_word = passWord;
	}
	public int getReceive_status() {
		return receive_status;
	}
	public void setReceive_status(int receiveStatus) {
		receive_status = receiveStatus;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
}
