package com.deya.wcm.services.appeal.myddc;
 
import java.io.Serializable;

public class SqMyddcBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private int sq_id = 0;
	private String myd = "";
	private String pjnr = "";
	private String pjsj = "";
	private String pjr = "";
	private String pjip = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSq_id() {
		return sq_id;
	}

	public void setSq_id(int sq_id) {
		this.sq_id = sq_id;
	}

	public String getMyd() {
		return myd;
	}

	public void setMyd(String myd) {
		this.myd = myd;
	}

	public String getPjnr() {
		return pjnr;
	}

	public void setPjnr(String pjnr) {
		this.pjnr = pjnr;
	}

	public String getPjsj() {
		return pjsj;
	}

	public void setPjsj(String pjsj) {
		this.pjsj = pjsj;
	}

	public String getPjr() {
		return pjr;
	}

	public void setPjr(String pjr) {
		this.pjr = pjr;
	}

	public String getPjip() {
		return pjip;
	}

	public void setPjip(String pjip) {
		this.pjip = pjip;
	}
}