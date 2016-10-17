package com.deya.project.searchInfo;
 
import java.io.Serializable;

public class YjcpzsBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String cyrmc = "";    //证书持有人名称
	private String dz = "";   //地址
    private String jdmc = "";   //基地（加工厂）名称
	private String jddz = "";  //基地（加工厂）地址
    private String mj = "";  //面积(亩)
    private String cpmc = "";  //产品名称
    private String cpms = "";  //产品描述
    private String scgm = "";  //生产规模
    private String cl = "";  //产量
    private String ccfzrq = "";  //初次发证日期
    private String bcfzrq = "";  //本次发证日期
    private String yxqx = "";  //有效期限
    private String bz = "";  //备注

    private String addTime = "";
    private String status = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCyrmc() {
        return cyrmc;
    }

    public void setCyrmc(String cyrmc) {
        this.cyrmc = cyrmc;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getJddz() {
        return jddz;
    }

    public void setJddz(String jddz) {
        this.jddz = jddz;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getCpmc() {
        return cpmc;
    }

    public void setCpmc(String cpmc) {
        this.cpmc = cpmc;
    }

    public String getCpms() {
        return cpms;
    }

    public void setCpms(String cpms) {
        this.cpms = cpms;
    }

    public String getScgm() {
        return scgm;
    }

    public void setScgm(String scgm) {
        this.scgm = scgm;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }

    public String getCcfzrq() {
        return ccfzrq;
    }

    public void setCcfzrq(String ccfzrq) {
        this.ccfzrq = ccfzrq;
    }

    public String getBcfzrq() {
        return bcfzrq;
    }

    public void setBcfzrq(String bcfzrq) {
        this.bcfzrq = bcfzrq;
    }

    public String getYxqx() {
        return yxqx;
    }

    public void setYxqx(String yxqx) {
        this.yxqx = yxqx;
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