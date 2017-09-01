package com.yinhai.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-09-01 13:20
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class InfoListResult implements Serializable{

    private List<HotInfoBean> infoList;
    private Integer total;
    private Integer start;
    private Integer limit;

    public List<HotInfoBean> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<HotInfoBean> infoList) {
        this.infoList = infoList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
