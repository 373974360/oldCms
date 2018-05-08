package com.deya.wcm.bean.cms.category;

import java.io.Serializable;

public class SyncBean implements Serializable{

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 8266136896847669175L;
	
	private String s_site_id = ""; 		// 站点ID,公共节点ID
	private int s_cat_id;			// 站目ID
	private String t_site_id = "";		// 站点ID,公共节点ID
	private int t_cat_id;			// 站目ID
	private int is_auto_publish = 0;	// 是否自动发布
	private int cite_type = 1;			// 同步类型1：引用	2：复制	3：链接
	private int orientation = 0; //信息流向  0获取 1推送 
	
	public int getOrientation() {
		return orientation;
	}
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	public String getS_site_id() {
		return s_site_id;
	}
	public int getS_cat_id() {
		return s_cat_id;
	}
	public String getT_site_id() {
		return t_site_id;
	}
	public int getT_cat_id() {
		return t_cat_id;
	}
	public int getIs_auto_publish() {
		return is_auto_publish;
	}
	public int getCite_type() {
		return cite_type;
	}
	
	public void setS_site_id(String sSiteId) {
		s_site_id = sSiteId;
	}
	public void setT_site_id(String tSiteId) {
		t_site_id = tSiteId;
	}
	public void setIs_auto_publish(int isAutoPublish) {
		is_auto_publish = isAutoPublish;
	}
	public void setCite_type(int citeType) {
		cite_type = citeType;
	}
	public void setS_cat_id(int sCatId) {
		s_cat_id = sCatId;
	}
	public void setT_cat_id(int tCatId) {
		t_cat_id = tCatId;
	}
}
