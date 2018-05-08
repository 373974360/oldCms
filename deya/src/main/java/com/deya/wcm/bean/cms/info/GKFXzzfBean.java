package com.deya.wcm.bean.cms.info;

/**
 * @author 张强
 * @version 1.0
 * @date 2011-07-25 中午21:50:34
 */

public class GKFXzzfBean extends GKInfoBean implements java.io.Serializable {
	
	private static final long serialVersionUID = -8342229352595948881L;
	private String gk_zflb ="";
	private String gk_xzxw ="";
	private String gk_xzcl ="";
	private String gk_flyj ="";
	private String gk_nrfj ="";
	
	public String getGk_zflb() {
		return gk_zflb;
	}
	public String getGk_xzxw() {
		return gk_xzxw;
	}
	public String getGk_xzcl() {
		return gk_xzcl;
	}
	public String getGk_flyj() {
		return gk_flyj;
	}
	public String getGk_nrfj() {
		return gk_nrfj;
	}
	public void setGk_zflb(String gkZflb) {
		gk_zflb = gkZflb;
	}
	public void setGk_xzxw(String gkXzxw) {
		gk_xzxw = gkXzxw;
	}
	public void setGk_xzcl(String gkXzcl) {
		gk_xzcl = gkXzcl;
	}
	public void setGk_flyj(String gkFlyj) {
		gk_flyj = gkFlyj;
	}
	public void setGk_nrfj(String gkNrfj) {
		gk_nrfj = gkNrfj;
	}
}
