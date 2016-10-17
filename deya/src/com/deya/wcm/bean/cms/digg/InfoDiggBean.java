package com.deya.wcm.bean.cms.digg;

import com.deya.wcm.bean.cms.info.InfoBean;

/**
 * 记录支持反对的bean
 * @author 王磊
 *
 */
public class InfoDiggBean extends InfoBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5641414916369623668L;
	private int info_id;
	private int supports; //支持
	private int againsts; //反对
	private String app_id = ""; //应用ID
	private String site_id = ""; //站点
	
	
	public int getInfo_id() {
		return info_id;
	}
	
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	
	public int getSupports() {
		return supports;
	}
	
	public void setSupports(int supports) {
		this.supports = supports;
	}
	
	public int getAgainsts() {
		return againsts;
	}
	
	public void setAgainsts(int againsts) {
		this.againsts = againsts;
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
