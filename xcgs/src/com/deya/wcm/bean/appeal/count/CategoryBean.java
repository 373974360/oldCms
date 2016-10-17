package com.deya.wcm.bean.appeal.count;

import java.util.*;

public class CategoryBean {
	private String category_id = "";
	private String category_name = "";
	private String category_type = "sub";
	private String p_id;
	private List handle_list; 
	private List child_list;
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public List getChild_list() {
		return child_list;
	}
	public void setChild_list(List child_list) {
		this.child_list = child_list;
	}
	public List getHandle_list() {
		return handle_list;
	}
	public void setHandle_list(List handle_list) {
		this.handle_list = handle_list;
	}
	public String getCategory_type() {
		return category_type;
	}
	public void setCategory_type(String category_type) {
		this.category_type = category_type;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String pId) {
		p_id = pId;
	}
}
