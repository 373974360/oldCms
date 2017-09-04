package com.yinhai.model;

import java.io.Serializable;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-08-30 9:35
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class InfoCount implements Serializable {

    private int catId;
    private String colname;
    private String sortno;
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

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }

    public String getSortno() {
        return sortno;
    }

    public void setSortno(String sortno) {
        this.sortno = sortno;
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
