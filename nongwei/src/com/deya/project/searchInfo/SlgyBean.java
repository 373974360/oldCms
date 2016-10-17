package com.deya.project.searchInfo;
 
import java.io.Serializable;

public class SlgyBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String gymc = "";    //公园名称
	private String zdmj = "";   //占地面积
    private String gyjb = "";   //公园级别
	private String zgbmmc = "";  //主管部门名称
    private String gydz = "";  //公园地址
    private String lxdh = "";  //联系电话
    private String bz = "";  //备注

    private String addTime = "";
    private String status = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGymc() {
        return gymc;
    }

    public void setGymc(String gymc) {
        this.gymc = gymc;
    }

    public String getZdmj() {
        return zdmj;
    }

    public void setZdmj(String zdmj) {
        this.zdmj = zdmj;
    }

    public String getGyjb() {
        return gyjb;
    }

    public void setGyjb(String gyjb) {
        this.gyjb = gyjb;
    }

    public String getZgbmmc() {
        return zgbmmc;
    }

    public void setZgbmmc(String zgbmmc) {
        this.zgbmmc = zgbmmc;
    }

    public String getGydz() {
        return gydz;
    }

    public void setGydz(String gydz) {
        this.gydz = gydz;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
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