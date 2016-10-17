package com.deya.project.searchInfo;
 
import java.io.Serializable;

public class WghzsBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String sqrqc = "";    //申请人全称
	private String cpmc = "";   //产品名称
    private String shb = "";   //商标
	private String ncl = "";  //年常量（吨）
    private String zsbh = "";  //证书编号
    private String zsyxq = "";  //证书有效期
    private String sflq = "";  //是否领取
    private String bz = "";  //备注

    private String addTime = "";
    private String status = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSqrqc() {
        return sqrqc;
    }

    public void setSqrqc(String sqrqc) {
        this.sqrqc = sqrqc;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getShb() {
        return shb;
    }

    public void setShb(String shb) {
        this.shb = shb;
    }

    public String getNcl() {
        return ncl;
    }

    public void setNcl(String ncl) {
        this.ncl = ncl;
    }

    public String getZsbh() {
        return zsbh;
    }

    public void setZsbh(String zsbh) {
        this.zsbh = zsbh;
    }

    public String getZsyxq() {
        return zsyxq;
    }

    public void setZsyxq(String zsyxq) {
        this.zsyxq = zsyxq;
    }

    public String getSflq() {
        return sflq;
    }

    public void setSflq(String sflq) {
        this.sflq = sflq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}