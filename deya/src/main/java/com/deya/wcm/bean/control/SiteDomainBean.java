package com.deya.wcm.bean.control;

public class SiteDomainBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -636274914018529420L;
	private int domain_id;
	private String site_id = "";
	private String site_domain = "";
	private int is_default;//0：普通	1：默认域名	
	private int is_host=0;//0:为普通 1为主域名,只能修改,不能删除
	public int getIs_host() {
		return is_host;
	}
	public void setIs_host(int is_host) {
		this.is_host = is_host;
	}
	public int getDomain_id() {
		return domain_id;
	}
	public void setDomain_id(int domain_id) {
		this.domain_id = domain_id;
	}
	public int getIs_default() {
		return is_default;
	}
	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}
	public String getSite_domain() {
		return site_domain;
	}
	public void setSite_domain(String site_domain) {
		this.site_domain = site_domain;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
}
