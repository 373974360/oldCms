package com.deya.wcm.bean.cms.info;

/**
 * @author 张强
 * @version 1.0
 * @date 2011-07-23 中午09:33:34
 */

public class GKFflgwBean extends GKInfoBean implements java.io.Serializable{
	
	private static final long serialVersionUID = 4046757031168332887L;
	private String gk_uniform_num="";
	private String gk_qsrq="";
	private String gk_djrq="";
	private String gk_xxsxx="";
	private String gk_content = "";
	


	public String getGk_uniform_num() {
		return gk_uniform_num;
	}
	public String getGk_qsrq() {
		return gk_qsrq;
	}
	public String getGk_djrq() {
		return gk_djrq;
	}
	public String getGk_xxsxx() {
		return gk_xxsxx;
	}
	public String getGk_content() {
		return gk_content;
	}
	       
	public void setGk_uniform_num(String gkUniformNum) {
		gk_uniform_num = gkUniformNum;
	}
	public void setGk_qsrq(String gkQsrq) {
		gk_qsrq = gkQsrq;
	}
	public void setGk_djrq(String gkDjrq) {
		gk_djrq = gkDjrq;
	}
	public void setGk_xxsxx(String gkXxsxx) {
		gk_xxsxx = gkXxsxx;
	}
	public void setGk_content(String gkContent) {
		gk_content = gkContent;
	}
}
