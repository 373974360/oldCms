package com.deya.wcm.bean.cms.info;

/**
 * @author 张强
 * @version 1.0
 * @date 2011-07-24 中午11:33:34
 */

public class GKFldcyBean extends GKInfoBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5687750795420169212L;
	
	private String gk_ldzw ="";
	private String gk_grjl ="";
	private String gk_gzfg ="";
	private String gk_dz ="";
	private String gk_tel ="";
	private String gk_email ="";
	private String gk_pic ="";
	private String gk_content = "";
	
	
	public String getGk_ldzw() {
		return gk_ldzw;
	}
	public String getGk_grjl() {
		return gk_grjl;
	}
	public String getGk_gzfg() {
		return gk_gzfg;
	}
	public String getGk_dz() {
		return gk_dz;
	}
	public String getGk_tel() {
		return gk_tel;
	}
	public String getGk_email() {
		return gk_email;
	}
	public String getGk_pic() {
		return gk_pic;
	}
	public String getGk_content() {
		return gk_content;
	}
	public void setGk_ldzw(String gkLdzw) {
		gk_ldzw = gkLdzw;
	}
	public void setGk_grjl(String gkGrjl) {
		gk_grjl = gkGrjl;
	}
	public void setGk_gzfg(String gkGzfg) {
		gk_gzfg = gkGzfg;
	}
	public void setGk_dz(String gkDz) {
		gk_dz = gkDz;
	}
	public void setGk_tel(String gkTel) {
		gk_tel = gkTel;
	}
	public void setGk_email(String gkEmail) {
		gk_email = gkEmail;
	}
	public void setGk_pic(String gkPic) {
		gk_pic = gkPic;
	}
	public void setGk_content(String gkContent) {
		gk_content = gkContent;
	}
}
