package com.deya.project.searchInfo;
 
import java.io.Serializable;

public class LmzmqyBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String qymc = "";    //林木种苗企业名称
	private String dh = "";   //电话
    private String scjydd = "";   //生产经营地点
	private String ybsz = "";  //一般树种
    private String zxsz = "";  //珍稀树种
    private String scjyxkz = "";  //生产经营许可证
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

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getScjydd() {
        return scjydd;
    }

    public void setScjydd(String scjydd) {
        this.scjydd = scjydd;
    }

    public String getYbsz() {
        return ybsz;
    }

    public void setYbsz(String ybsz) {
        this.ybsz = ybsz;
    }

    public String getZxsz() {
        return zxsz;
    }

    public void setZxsz(String zxsz) {
        this.zxsz = zxsz;
    }

    public String getScjyxkz() {
        return scjyxkz;
    }

    public void setScjyxkz(String scjyxkz) {
        this.scjyxkz = scjyxkz;
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