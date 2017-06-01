package com.deya.project.dz_house;

import java.io.Serializable;

public class LouYuBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String lycode = "";
	private String ldh = "";
	private String dys = "";
	private String cg="";
	private String fjzs = "";
	private String jzmj = "";
	private String zdmj = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getLycode() {
		return lycode;
	}

	public void setLycode(String lycode) {
		this.lycode = lycode;
	}

	public String getLdh() {
		return ldh;
	}
	public void setLdh(String ldh) {
		this.ldh = ldh;
	}
	public String getDys() {
		return dys;
	}
	public void setDys(String dys) {
		this.dys = dys;
	}
	public String getFjzs() {
		return fjzs;
	}
	public void setFjzs(String fjzs) {
		this.fjzs = fjzs;
	}
	public String getJzmj() {
		return jzmj;
	}
	public void setJzmj(String jzmj) {
		this.jzmj = jzmj;
	}
	public String getZdmj() {
		return zdmj;
	}
	public void setZdmj(String zdmj) {
		this.zdmj = zdmj;
	}

	public String getCg() {
		return cg;
	}

	public void setCg(String cg) {
		this.cg = cg;
	}
}
