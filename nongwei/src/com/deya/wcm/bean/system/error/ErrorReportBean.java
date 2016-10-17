package com.deya.wcm.bean.system.error;

public class ErrorReportBean {

	private int id;  
	private int info_id;
	private String info_title = "";
	private String info_url = "";
	private String err_type = "";  
	private String err_content = "";
	private String err_name = ""; 
	private String err_name_tel = "";  
	private String err_name_email = ""; 
	private String err_name_ip = ""; 
	private String err_time = ""; 
	private String err_state = "1"; 
	private String err_note = "";
	private String site_id = "";
	private String o_time = "";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	public String getInfo_title() {
		return info_title;
	}
	public void setInfo_title(String infoTitle) {
		info_title = infoTitle;
	}
	public String getInfo_url() {
		return info_url;
	}
	public void setInfo_url(String infoUrl) {
		info_url = infoUrl;
	}
	public String getErr_type() {
		return err_type;
	}
	public void setErr_type(String errType) {
		err_type = errType;
	}
	public String getErr_content() {
		return err_content;
	}
	public void setErr_content(String errContent) {
		err_content = errContent;
	}
	public String getErr_name() {
		return err_name;
	}
	public void setErr_name(String errName) {
		err_name = errName;
	}
	public String getErr_name_tel() {
		return err_name_tel;
	}
	public void setErr_name_tel(String errNameTel) {
		err_name_tel = errNameTel;
	}
	public String getErr_name_email() {
		return err_name_email;
	}
	public void setErr_name_email(String errNameEmail) {
		err_name_email = errNameEmail;
	}
	public String getErr_name_ip() {
		return err_name_ip;
	}
	public void setErr_name_ip(String errNameIp) {
		err_name_ip = errNameIp;
	}
	public String getErr_time() {
		return err_time;
	}
	public void setErr_time(String errTime) {
		err_time = errTime;
	}
	public String getErr_state() {
		return err_state;
	}
	public void setErr_state(String errState) {
		err_state = errState;
	}
	public String getErr_note() {
		return err_note;
	}
	public void setErr_note(String errNote) {
		err_note = errNote;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getO_time() {
		return o_time;
	}
	public void setO_time(String oTime) {
		o_time = oTime;
	}
	
	
	
}
