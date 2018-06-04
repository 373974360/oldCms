package com.yinhai.model;

import java.io.Serializable;

/**
 * @Description:
 * @User: Administrator
 * @Date: 2018/5/8 0008
 */
public class GuiDangVo implements Serializable{
    private String ywbh;
    private String ywbt;
    private String lcmc;
    private String files;
    private String curcode;
    private String buscode;
    private String curdate;
    private String usercode;
    private String filepath;
    private String xxly;

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }

    public String getYwbt() {
        return ywbt;
    }

    public void setYwbt(String ywbt) {
        this.ywbt = ywbt;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getCurcode() {
        return curcode;
    }

    public void setCurcode(String curcode) {
        this.curcode = curcode;
    }

    public String getBuscode() {
        return buscode;
    }

    public void setBuscode(String buscode) {
        this.buscode = buscode;
    }

    public String getCurdate() {
        return curdate;
    }

    public void setCurdate(String curdate) {
        this.curdate = curdate;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getXxly() {
        return xxly;
    }

    public void setXxly(String xxly) {
        this.xxly = xxly;
    }
}
