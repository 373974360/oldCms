package com.deya.wcm.bean.system.comment;

import java.io.Serializable;

public class CommentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7728368101821151808L;
	
	private String cmt_id = "";//评论ID
	private String item_id = "";//评论对象ID
	private String parent_id = "";//父ID
	private String cmt_content = "";//评论内容
	private String me_id = "";//会员ID
	private String me_nickname = "";//会员昵称
	private String add_dtime = "";//添加时间
	private String cmt_ip = "";//评论IP
	private int support_num = 0;//支持数
	private int cmt_status = 0;//评论状态
	private int is_deleted = 0;//是否删除
	private String app_id = "";//应用ID
	private String site_id = "";//站点ID
	private String rele_title = "";//关联的内容标题
	private int model_id = 0;
	private int dept_id = 0;
	private String ip_addr = "";
	
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int deptId) {
		dept_id = deptId;
	}
	public String getIp_addr() {
		return ip_addr;
	}
	public void setIp_addr(String ipAddr) {
		ip_addr = ipAddr;
	}
	public String getRele_title() {
		return rele_title;
	}
	public void setRele_title(String releTitle) {
		rele_title = releTitle;
	}
	public String getCmt_id() {
		return cmt_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public String getCmt_content() {
		return cmt_content;
	}
	public String getMe_id() {
		return me_id;
	}
	public String getMe_nickname() {
		return me_nickname;
	}
	public String getAdd_dtime() {
		return add_dtime;
	}
	public String getCmt_ip() {
		return cmt_ip;
	}
	public int getSupport_num() {
		return support_num;
	}
	public int getCmt_status() {
		return cmt_status;
	}
	public int getIs_deleted() {
		return is_deleted;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	
	
	public void setCmt_id(String cmtId) {
		cmt_id = cmtId;
	}
	public void setItem_id(String itemId) {
		item_id = itemId;
	}
	public void setParent_id(String parentId) {
		parent_id = parentId;
	}
	public void setCmt_content(String cmtContent) {
		cmt_content = cmtContent;
	}
	public void setMe_id(String meId) {
		me_id = meId;
	}
	public void setMe_nickname(String meNickname) {
		me_nickname = meNickname;
	}
	public void setAdd_dtime(String addDtime) {
		add_dtime = addDtime;
	}
	public void setCmt_ip(String cmtIp) {
		cmt_ip = cmtIp;
	}
	public void setSupport_num(int supportNum) {
		support_num = supportNum;
	}
	public void setCmt_status(int cmtStatus) {
		cmt_status = cmtStatus;
	}
	public void setIs_deleted(int isDeleted) {
		is_deleted = isDeleted;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}

}
