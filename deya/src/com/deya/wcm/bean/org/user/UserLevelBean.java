package com.deya.wcm.bean.org.user;

public class UserLevelBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8386388832262443398L;
	private int userlevel_id;//ID
	private int userlevel_value;//用户级别
	private String userlevel_name = "";//级别名称
	private String userlevel_memo = "";//备注
	private int is_delete = 0;//是否已删除
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public int getUserlevel_id() {
		return userlevel_id;
	}
	public void setUserlevel_id(int userlevel_id) {
		this.userlevel_id = userlevel_id;
	}
	public String getUserlevel_memo() {
		return userlevel_memo;
	}
	public void setUserlevel_memo(String userlevel_memo) {
		this.userlevel_memo = userlevel_memo;
	}
	public String getUserlevel_name() {
		return userlevel_name;
	}
	public void setUserlevel_name(String userlevel_name) {
		this.userlevel_name = userlevel_name;
	}
	public int getUserlevel_value() {
		return userlevel_value;
	}
	public void setUserlevel_value(int userlevel_value) {
		this.userlevel_value = userlevel_value;
	}
}
