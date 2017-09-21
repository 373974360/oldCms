package com.yinhai.model;

import java.util.List;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-08-30 10:44
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class InfoCountResult {

    private String source;

    private List<InfoCount> infoCounts;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<InfoCount> getInfoCounts() {
        return infoCounts;
    }

    public void setInfoCounts(List<InfoCount> infoCounts) {
        this.infoCounts = infoCounts;
    }
}
