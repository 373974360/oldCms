package com.deya.wcm.bean.cms.category;

public class CategorySharedBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8817861135453799241L;
	private String s_site_id = "";
	private int cat_id = 0;
	private String t_site_id = "";
	private int shared_type = 0;//0：共享 1接收	
	private int range_type = 0;//0：限制共享、接收1：全部共享、接收
	public String getS_site_id() {
		return s_site_id;
	}
	public void setS_site_id(String sSiteId) {
		s_site_id = sSiteId;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public String getT_site_id() {
		return t_site_id;
	}
	public void setT_site_id(String tSiteId) {
		t_site_id = tSiteId;
	}
	public int getShared_type() {
		return shared_type;
	}
	public void setShared_type(int sharedType) {
		shared_type = sharedType;
	}
	public int getRange_type() {
		return range_type;
	}
	public void setRange_type(int rangeType) {
		range_type = rangeType;
	}
	
}
