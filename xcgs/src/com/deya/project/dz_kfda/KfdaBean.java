package com.deya.project.dz_kfda;
 
import java.io.Serializable;
 
public class KfdaBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String mlmc = "";
	private String dh = "";
	private String tm = "";
	private String gjc = "";
	private String zrz = "";
	private String sj = "";
	private String dalx = "";
	private String bgqx = "";
	private String ywlj = "";
	private String ywml = "";
	private String fz = "";
	private String status = "";
	private String inputTime = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMlmc() {
		return mlmc;
	}
	public void setMlmc(String mlmc) {
		this.mlmc = mlmc;
	}
	public String getDh() {
		return dh;
	}
	public void setDh(String dh) {
		this.dh = dh;
	}
	public String getTm() {
		return tm;
	}
	public void setTm(String tm) {
		this.tm = tm;
	}
	public String getGjc() {
		return gjc;
	}
	public void setGjc(String gjc) {
		this.gjc = gjc;
	}
	public String getZrz() {
		return zrz;
	}
	public void setZrz(String zrz) {
		this.zrz = zrz;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getDalx() {
		return dalx;
	}
	public void setDalx(String dalx) {
		this.dalx = dalx;
	}
	public String getYwlj() {
		return ywlj;
	}
	public void setYwlj(String ywlj) {
		this.ywlj = ywlj;
	}
	public String getYwml() {
		return ywml;
	}
	public void setYwml(String ywml) {
		this.ywml = ywml;
	}
	public String getFz() {
		return fz;
	}
	public void setFz(String fz) {
		this.fz = fz;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInputTime() {
		return inputTime;
	}
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}
	public String getBgqx() {
		return bgqx;
	}
	public void setBgqx(String bgqx) {
		this.bgqx = bgqx;
	}
	
}