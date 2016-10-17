package com.deya.project.dz_pxxx;
 
import java.io.Serializable;
 
public class PxxxBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String pxmc = "";
	private String pxlx = "";
	private String rsxz = "";
	private String pxsj = "";
	private String pxsjEnd = "";
	private String bz = "";
	private String status = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPxmc() {
		return pxmc;
	}
	public void setPxmc(String pxmc) {
		this.pxmc = pxmc;
	}
	public String getPxlx() {
		return pxlx;
	}
	public void setPxlx(String pxlx) {
		this.pxlx = pxlx;
	}
	public String getRsxz() {
		return rsxz;
	}
	public void setRsxz(String rsxz) {
		this.rsxz = rsxz;
	}
	public String getPxsj() {
		return pxsj;
	}
	public void setPxsj(String pxsj) {
		this.pxsj = pxsj;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPxsjEnd() {
		return pxsjEnd;
	}
	public void setPxsjEnd(String pxsjEnd) {
		this.pxsjEnd = pxsjEnd;
	}
	
}