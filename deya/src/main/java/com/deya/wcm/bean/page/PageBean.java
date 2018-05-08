package com.deya.wcm.bean.page;

import java.util.ArrayList;
import java.util.List;

public class PageBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5221827487623932524L;
	private int id = 0;
	private int page_id = 0;
	private int parent_id = 0;
	private String page_ename = "";
	private String page_cname = "";
	private int template_id = 0;
	private String page_path = "";
	private int page_interval = 0;
	private String last_dtime = "";
	private String next_dtime = "";
	private int sort_id = 999;
	private String page_memo = "";
	private String app_id = "";
	private String site_id = "";
	private List<PageBean> child_list = null;
	public List<PageBean> getChild_list() {
		return child_list;
	}
	public void setChild_list(List<PageBean> childList) {
		child_list = childList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPage_id() {
		return page_id;
	}
	public void setPage_id(int pageId) {
		page_id = pageId;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public String getPage_ename() {
		return page_ename;
	}
	public void setPage_ename(String pageEname) {
		page_ename = pageEname;
	}
	public String getPage_cname() {
		return page_cname;
	}
	public void setPage_cname(String pageCname) {
		page_cname = pageCname;
	}
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int templateId) {
		template_id = templateId;
	}
	public String getPage_path() {
		return page_path;
	}
	public void setPage_path(String pagePath) {
		page_path = pagePath;
	}
	public int getPage_interval() {
		return page_interval;
	}
	public void setPage_interval(int pageInterval) {
		page_interval = pageInterval;
	}
	public String getLast_dtime() {
		return last_dtime;
	}
	public void setLast_dtime(String lastDtime) {
		last_dtime = lastDtime;
	}
	public String getNext_dtime() {
		return next_dtime;
	}
	public void setNext_dtime(String nextDtime) {
		next_dtime = nextDtime;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public String getPage_memo() {
		return page_memo;
	}
	public void setPage_memo(String pageMemo) {
		page_memo = pageMemo;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
}
