package com.deya.project.searchInfo;
 
import java.io.Serializable;

public class LsspzsBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String scs = "";    //生产商
	private String cpmc = "";   //产品名称
    private String cpbh = "";   //产品编号
	private String qyxxm = "";  //企业信息码
    private String hzcl = "";  //核准产量（吨）
    private String mj = "";  //面积（亩）
    private String xkqx = "";  //许可期限
    private String bz = "";  //备注

    private String addTime = "";
    private String status = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScs() {
        return scs;
    }

    public void setScs(String scs) {
        this.scs = scs;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getCpbh() {
        return cpbh;
    }

    public void setCpbh(String cpbh) {
        this.cpbh = cpbh;
    }

    public String getQyxxm() {
        return qyxxm;
    }

    public void setQyxxm(String qyxxm) {
        this.qyxxm = qyxxm;
    }

    public String getHzcl() {
        return hzcl;
    }

    public void setHzcl(String hzcl) {
        this.hzcl = hzcl;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getXkqx() {
        return xkqx;
    }

    public void setXkqx(String xkqx) {
        this.xkqx = xkqx;
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