package com.deya.project.dz_house;

import java.io.Serializable;

/**
 * Created by MaChaoWei on 2017/5/17.
 */
public class HouseCountBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name="";
    private String codes="";
    private String nums="";
    private String jzmj="";
    private String symj="";
    private String ysnums="";
    private String ysjzmj="";
    private String yssymj="";
    private String wsnums="";
    private String wsjzmj="";
    private String wssymj="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
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

    public String getYsnums() {
        return ysnums;
    }

    public void setYsnums(String ysnums) {
        this.ysnums = ysnums;
    }

    public String getYsjzmj() {
        return ysjzmj;
    }

    public void setYsjzmj(String ysjzmj) {
        this.ysjzmj = ysjzmj;
    }

    public String getYssymj() {
        return yssymj;
    }

    public void setYssymj(String yssymj) {
        this.yssymj = yssymj;
    }

    public String getWsnums() {
        return wsnums;
    }

    public void setWsnums(String wsnums) {
        this.wsnums = wsnums;
    }

    public String getWsjzmj() {
        return wsjzmj;
    }

    public void setWsjzmj(String wsjzmj) {
        this.wsjzmj = wsjzmj;
    }

    public String getWssymj() {
        return wssymj;
    }

    public void setWssymj(String wssymj) {
        this.wssymj = wssymj;
    }
}
