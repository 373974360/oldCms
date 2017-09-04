package com.yinhai.model;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-08-30 10:44
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class InfoCountResult {

    private String source;

    private ArrayList<InfoCount> infoCounts;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ArrayList<InfoCount> getInfoCounts() {
        return infoCounts;
    }

    public void setInfoCounts(ArrayList<InfoCount> infoCounts) {
        this.infoCounts = infoCounts;
    }
}
