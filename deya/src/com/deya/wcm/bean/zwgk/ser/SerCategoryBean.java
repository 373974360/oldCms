package com.deya.wcm.bean.zwgk.ser;

import java.io.Serializable;

public class SerCategoryBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -926972095646201821L;
	private int id = 0;
	private int ser_id = 0;
	private int parent_id = 0;
	private String cat_type = "";
	private String tree_position = "";
	private String cat_name = "";
	private String description = "";
	private String next_desc = "";
	private String url = "";
	private int template_index = 0;
	private int template_list = 0;
	private int template_content = 0;
	private int xgwt_cat_id = 0;
	private int cjwt_cat_id = 0;
	private int sort_id = 0;
	private int publish_status = 0;
	private String publish_time = "";
	private int res_flag = 0;
	private String dict_id = "";
	private String thumb_url = "";
	public int getXgwt_cat_id() {
		return xgwt_cat_id;
	}
	public void setXgwt_cat_id(int xgwtCatId) {
		xgwt_cat_id = xgwtCatId;
	}
	public int getCjwt_cat_id() {
		return cjwt_cat_id;
	}
	public void setCjwt_cat_id(int cjwtCatId) {
		cjwt_cat_id = cjwtCatId;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumbUrl) {
		thumb_url = thumbUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSer_id() {
		return ser_id;
	}
	public void setSer_id(int serId) {
		ser_id = serId;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public String getCat_type() {
		return cat_type;
	}
	public void setCat_type(String catType) {
		cat_type = catType;
	}
	public String getTree_position() {
		return tree_position;
	}
	public void setTree_position(String treePosition) {
		tree_position = treePosition;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String catName) {
		cat_name = catName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNext_desc() {
		return next_desc;
	}
	public void setNext_desc(String nextDesc) {
		next_desc = nextDesc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getTemplate_index() {
		return template_index;
	}
	public void setTemplate_index(int templateIndex) {
		template_index = templateIndex;
	}
	public int getTemplate_list() {
		return template_list;
	}
	public void setTemplate_list(int templateList) {
		template_list = templateList;
	}
	public int getTemplate_content() {
		return template_content;
	}
	public void setTemplate_content(int templateContent) {
		template_content = templateContent;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
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
	public int getRes_flag() {
		return res_flag;
	}
	public void setRes_flag(int resFlag) {
		res_flag = resFlag;
	}
	
	public String getDict_id() {
		return dict_id;
	}
	public void setDict_id(String dictId) {
		dict_id = dictId;
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
	private String add_time = "";
	private String add_user = "";
	private String update_time = "";
	private String update_user = "";
}
