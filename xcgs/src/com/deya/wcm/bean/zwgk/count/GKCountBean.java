package com.deya.wcm.bean.zwgk.count;

import java.io.Serializable;
import java.util.List;

public class GKCountBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8522700275190020027L;

	private int cat_id;
	private int info_count = 0;// 信息总件数
	private int z_count = 0;	// 主动公开件数
	private int y_count = 0;	// 依申请公开件数
	private int b_count = 0;	// 不公开件数
	private int pic_count = 0;	// 图片信息数
	private String update_time = "";
	private String app_id = "";
	private String site_id = "";
	
	// 以下字段段仅供前台显示时使用
	private String cat_name = "";// 此字段仅供前台显示时使用
	private String site_name = "";// 此字段仅供前台显示时使用
	private boolean is_leaf = true;// 此字段仅供前台显示时使用
	private List<GKCountBean> child_list;// 此字段仅供前台显示时使用

	public int getCat_id() {
		return cat_id;
	}

	public int getInfo_count() {
		return info_count;
	}

	public int getZ_count() {
		return z_count;
	}

	public int getY_count() {
		return y_count;
	}

	public int getB_count() {
		return b_count;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public String getApp_id() {
		return app_id;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setCat_id(int catId) {
		cat_id = catId;
	}

	public void setInfo_count(int infoCount) {
		info_count = infoCount;
	}

	public void setZ_count(int zCount) {
		z_count = zCount;
	}

	public void setY_count(int yCount) {
		y_count = yCount;
	}

	public void setB_count(int bCount) {
		b_count = bCount;
	}

	public void setUpdate_time(String updateTime) {
		update_time = updateTime;
	}

	public void setApp_id(String appId) {
		app_id = appId;
	}

	public void setSite_id(String siteId) {
		site_id = siteId;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String catName) {
		cat_name = catName;
	}

	public boolean isIs_leaf() {
		return is_leaf;
	}

	public void setIs_leaf(boolean isLeaf) {
		is_leaf = isLeaf;
	}

	public List<GKCountBean> getChild_list() {
		return child_list;
	}

	public void setChild_list(List<GKCountBean> childList) {
		child_list = childList;
		// 如果是叶节点,则不允许赋值
		if(is_leaf)
			child_list = null;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String siteName) {
		site_name = siteName;
	}

	public int getPic_count() {
		return pic_count;
	}

	public void setPic_count(int picCount) {
		pic_count = picCount;
	}
}
