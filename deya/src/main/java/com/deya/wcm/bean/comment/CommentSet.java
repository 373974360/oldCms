package com.deya.wcm.bean.comment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommentSet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7517668686831997255L;
	private int id;
	private String app_id;
	private String site_id;
	private String is_public;
	private String com_prefix;
	private String is_need;
	private String is_code;
	private String time_spacer;
	private String ip_time;
	private int pass_size;
	private String tact_word;
	private List<String> tactList = new ArrayList<String>();
	
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
	public String getIs_public() {
		return is_public;
	}
	public void setIs_public(String isPublic) {
		is_public = isPublic;
	}
	public String getIs_need() {
		return is_need;
	}
	public void setIs_need(String isNeed) {
		is_need = isNeed;
	}
	public String getIs_code() {
		return is_code;
	}
	public void setIs_code(String isCode) {
		is_code = isCode;
	}
	public String getTime_spacer() {
		return time_spacer;
	}
	public void setTime_spacer(String timeSpacer) {
		time_spacer = timeSpacer;
	}
	public String getIp_time() {
		return ip_time;
	}
	public void setIp_time(String ipTime) {
		ip_time = ipTime;
	}
	public int getPass_size() {
		return pass_size;
	}
	public void setPass_size(int passSize) {
		pass_size = passSize;
	}
	public String getTact_word() {
		return nullToString(tact_word);
	}
	public void setTact_word(String tactWord) {
		tact_word = tactWord;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public String getCom_prefix() {
		return nullToString(com_prefix);
	}
	public void setCom_prefix(String comPrefix) {
		com_prefix = comPrefix;
	}
	
	
	public static String nullToString(String str){
		if(str==null || "null".equals(str)){
			str = "";
		}
		return str;
	}
	public List<String> getTactList() {
		return tactList;
	}
	public void setTactList(List<String> tactList) {
		this.tactList = tactList;
	}
}
