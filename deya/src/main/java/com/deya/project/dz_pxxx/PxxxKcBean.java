package com.deya.project.dz_pxxx;
 
import java.io.Serializable;
 
public class PxxxKcBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private int pxid = 0;
	private String pxmc = "";
	private String pxnr = "";
	private String bmid = "";
	private String pxsj = "";
	private String pxsjEnd = "";
	private String pxdd = "";
	private String zjr = "";
	private String status = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPxid() {
		return pxid;
	}
	public void setPxid(int pxid) {
		this.pxid = pxid;
	}
	public String getPxmc() {
		return pxmc;
	}
	public void setPxmc(String pxmc) {
		this.pxmc = pxmc;
	}
	public String getBmid() {
		return bmid;
	}
	public void setBmid(String bmid) {
		this.bmid = bmid;
	}
	public String getPxsj() {
		return pxsj;
	}
	public void setPxsj(String pxsj) {
		this.pxsj = pxsj;
	}
	public String getPxdd() {
		return pxdd;
	}
	public void setPxdd(String pxdd) {
		this.pxdd = pxdd;
	}
	public String getZjr() {
		return zjr;
	}
	public void setZjr(String zjr) {
		this.zjr = zjr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPxnr() {
		return pxnr;
	}
	public void setPxnr(String pxnr) {
		this.pxnr = pxnr;
	}
	public String getPxsjEnd() {
		return pxsjEnd;
	}
	public void setPxsjEnd(String pxsjEnd) {
		this.pxsjEnd = pxsjEnd;
	}
	
}