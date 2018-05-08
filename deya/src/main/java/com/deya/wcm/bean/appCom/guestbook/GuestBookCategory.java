package com.deya.wcm.bean.appCom.guestbook;

import java.io.Serializable;

public class GuestBookCategory implements Serializable,Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 648042059446308929L;
	private int id = 0;
	private int cat_id = 0;
	private String title = "";
	private int publish_status = 0;
	private int direct_publish = 0;
	private int is_member = 0;
	private int is_receive_show = 0;
	private int is_auth_code = 0;
	private int sort_id	= 999;				
	private int template_index = 0;
	private int template_list = 0;
	private int template_form = 0;
	private int template_content = 0;
	private String site_id = "";
	private String description = "";
	
	public int getTemplate_content() {
		return template_content;
	}

	public void setTemplate_content(int templateContent) {
		template_content = templateContent;
	}

	public GuestBookCategory clone(){
		GuestBookCategory o = null;
        try{
            o = (GuestBookCategory)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPublish_status() {
		return publish_status;
	}
	public void setPublish_status(int publishStatus) {
		publish_status = publishStatus;
	}
	public int getDirect_publish() {
		return direct_publish;
	}
	public void setDirect_publish(int directPublish) {
		direct_publish = directPublish;
	}
	public int getIs_member() {
		return is_member;
	}
	public void setIs_member(int isMember) {
		is_member = isMember;
	}
	public int getIs_receive_show() {
		return is_receive_show;
	}
	public void setIs_receive_show(int isReceiveShow) {
		is_receive_show = isReceiveShow;
	}
	public int getIs_auth_code() {
		return is_auth_code;
	}
	public void setIs_auth_code(int isAuthCode) {
		is_auth_code = isAuthCode;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
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
	public int getTemplate_form() {
		return template_form;
	}
	public void setTemplate_form(int templateForm) {
		template_form = templateForm;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
