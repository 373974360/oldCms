package com.deya.wcm.bean.appCom.guestbook;

import java.io.Serializable;

public class GuestBookClass implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4182086551176027141L;
	private int id = 0;		
	private int class_id = 0;			
	private int cat_id = 0;
	private String name = "";
	private String site_id = "";
	private String description = "";
	private int sort_id	= 999;
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int classId) {
		class_id = classId;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	
	
}
