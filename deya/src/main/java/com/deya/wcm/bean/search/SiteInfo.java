package com.deya.wcm.bean.search;

/**
 * <p>站点信息类</p>
 * <p>站点信息类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */
public class SiteInfo {
    
	
	private String site_id;
	private String site_name;
	private String state;
	private String site_domain; 
	
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String siteName) {
		site_name = siteName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSite_domain() {
		return site_domain;
	}
	public void setSite_domain(String siteDomain) {
		site_domain = siteDomain;
	}
	
}
