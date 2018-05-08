package com.deya.wcm.bean.sendInfo;

import java.util.ArrayList;
import java.util.List;

public class SendRecordCount implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 993499666621437090L;
	private int user_id = 0;
	private String user_realname = "";
	private int send_count = 0;
	private int adopt_count =  0;
	private int not_count = 0;
	private String adopt_rate = "";
	private int cat_parent_id = 0;
	private int cat_id = 0;
	private int cat_sort = 0;
	private String cat_cname = "'";
	private List<SendRecordCount> child_cate_list = new ArrayList<SendRecordCount>();;
	private String site_name = "";
	private String site_id = "";
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String siteName) {
		site_name = siteName;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public int getCat_sort() {
		return cat_sort;
	}
	public void setCat_sort(int catSort) {
		cat_sort = catSort;
	}
	public int getCat_parent_id() {
		return cat_parent_id;
	}
	public void setCat_parent_id(int catParentId) {
		cat_parent_id = catParentId;
	}
	public List<SendRecordCount> getChild_cate_list() {
		return child_cate_list;
	}
	public void setChild_cate_list(List<SendRecordCount> childCateList) {
		child_cate_list = childCateList;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public String getCat_cname() {
		return cat_cname;
	}
	public void setCat_cname(String catCname) {
		cat_cname = catCname;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public String getUser_realname() {
		return user_realname;
	}
	public void setUser_realname(String userRealname) {
		user_realname = userRealname;
	}
	
	public int getSend_count() {
		return send_count;
	}
	public void setSend_count(int sendCount) {
		send_count = sendCount;
	}
	public int getAdopt_count() {
		return adopt_count;
	}
	public void setAdopt_count(int adoptCount) {
		adopt_count = adoptCount;
	}
	public int getNot_count() {
		return not_count;
	}
	public void setNot_count(int notCount) {
		not_count = notCount;
	}
	public String getAdopt_rate() {
		return adopt_rate;
	}
	public void setAdopt_rate(String adoptRate) {
		adopt_rate = adoptRate;
	}
	
	
}
