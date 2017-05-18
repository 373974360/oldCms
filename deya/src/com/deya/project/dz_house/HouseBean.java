package com.deya.project.dz_house;

import java.io.Serializable;

/**
 * Created by MaChaoWei on 2017/5/17.
 */
public class HouseBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id=0;
    private String housecode="";
    private String fjh="";
    private String szdy="";
    private String szlc="";
    private String jzmj="";
    private String symj="";
    private String cx="";
    private String fjlx="";
    private String fjzt="";
    private String hxt="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHousecode() {
        return housecode;
    }

    public void setHousecode(String housecode) {
        this.housecode = housecode;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getSzdy() {
        return szdy;
    }

    public void setSzdy(String szdy) {
        this.szdy = szdy;
    }

    public String getSzlc() {
        return szlc;
    }

    public void setSzlc(String szlc) {
        this.szlc = szlc;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getSymj() {
        return symj;
    }

    public void setSymj(String symj) {
        this.symj = symj;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getFjlx() {
        return fjlx;
    }

    public void setFjlx(String fjlx) {
        this.fjlx = fjlx;
    }

    public String getFjzt() {
        return fjzt;
    }

    public void setFjzt(String fjzt) {
        this.fjzt = fjzt;
    }

    public String getHxt() {
        return hxt;
    }

    public void setHxt(String hxt) {
        this.hxt = hxt;
    }
}
