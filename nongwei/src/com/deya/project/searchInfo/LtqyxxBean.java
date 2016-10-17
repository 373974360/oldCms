package com.deya.project.searchInfo;
 
import java.io.Serializable;

public class LtqyxxBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String dwmc = "";    //单位名称
	private String zyyw = "";   //主营业务
    private String dz = "";   //地址
	private String dh = "";  //电话
    private String yb = "";  //邮编
    private String bz = "";  //备注

    private String addTime = "";
    private String status = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getZyyw() {
        return zyyw;
    }

    public void setZyyw(String zyyw) {
        this.zyyw = zyyw;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
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