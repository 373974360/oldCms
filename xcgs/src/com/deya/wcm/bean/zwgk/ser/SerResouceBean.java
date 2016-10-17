package com.deya.wcm.bean.zwgk.ser;

import java.io.Serializable;

public class SerResouceBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5657511190071930719L;
	private int id = 0;
	private int res_id = 0;
	private int ser_id = 0;
	private String title = "";
	private String content = "";
	private String url = "";
	private int sort_id = 0;
	private int res_status = 0;
	private int publish_status = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRes_id() {
		return res_id;
	}
	public void setRes_id(int resId) {
		res_id = resId;
	}
	public int getSer_id() {
		return ser_id;
	}
	public void setSer_id(int serId) {
		ser_id = serId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public int getRes_status() {
		return res_status;
	}
	public void setRes_status(int resStatus) {
		res_status = resStatus;
	}
	public int getPublish_status() {
		return publish_status;
	}
	public void setPublish_status(int publishStatus) {
		publish_status = publishStatus;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publishTime) {
		publish_time = publishTime;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String addTime) {
		add_time = addTime;
	}
	public String getAdd_user() {
		return add_user;
	}
	public void setAdd_user(String addUser) {
		add_user = addUser;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String updateTime) {
		update_time = updateTime;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String updateUser) {
		update_user = updateUser;
	}
	private String publish_time = "";
	private String add_time = "";
	private String add_user = "";
	private String update_time = "";
	private String update_user = "";
}
