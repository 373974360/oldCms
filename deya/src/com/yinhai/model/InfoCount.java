package com.yinhai.model;

import java.io.Serializable;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-08-30 9:35
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class InfoCount implements Serializable{

    private int catId;
    private int parentId;
    private String catName;
    private String catPosition;
    private int tpubnum;
    private int pubnum;
    private int tvisitnum;
    private int visitnum;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCatPosition() {
        return catPosition;
    }

    public void setCatPosition(String catPosition) {
        this.catPosition = catPosition;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getTpubnum() {
        return tpubnum;
    }

    public void setTpubnum(int tpubnum) {
        this.tpubnum = tpubnum;
    }

    public int getPubnum() {
        return pubnum;
    }

    public void setPubnum(int pubnum) {
        this.pubnum = pubnum;
    }

    public int getTvisitnum() {
        return tvisitnum;
    }

    public void setTvisitnum(int tvisitnum) {
        this.tvisitnum = tvisitnum;
    }

    public int getVisitnum() {
        return visitnum;
    }

    public void setVisitnum(int visitnum) {
        this.visitnum = visitnum;
    }
}
