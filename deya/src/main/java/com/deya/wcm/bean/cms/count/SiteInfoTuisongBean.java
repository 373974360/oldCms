package com.deya.wcm.bean.cms.count;

public class SiteInfoTuisongBean implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5465262522972206364L;
	
	
	private String site_name = "";//站点名称
	private String site_id = "";//站点Id
	private int icount = 0;	//统计结果   --仅用于对应ibatis取得结果的临时存放
	
	
	public String getSite_name() {
		return site_name;
	}
	public String getSite_id() {
		return site_id;
	}
	public int getIcount() {
		return icount;
	}
	public void setSite_name(String siteName) {
		site_name = siteName;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public void setIcount(int icount) {
		this.icount = icount;
	}
	
	

}
