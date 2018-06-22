package com.yinhai.model;

import java.io.Serializable;

/**
 * @Description: 银海调查问卷列表返回对象
 * @User: Administrator
 * @Date: 2018/5/7 0007
 */
public class WangDianVo implements Serializable {

    private String id;//机构/网点id
    private String pid;//上级机构代码
    private String depcode;//机构代码
    private String name;//机构/网点名称
    private String depaddr;//机构地址
    private String depphone;//机构联系电话
    private String longitude;//经度
    private String latitude;//纬度
    private String sertime;//服务时间
    private String serarea;//服务范围

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDepcode() {
        return depcode;
    }

    public void setDepcode(String depcode) {
        this.depcode = depcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepaddr() {
        return depaddr;
    }

    public void setDepaddr(String depaddr) {
        this.depaddr = depaddr;
    }

    public String getDepphone() {
        return depphone;
    }

    public void setDepphone(String depphone) {
        this.depphone = depphone;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSertime() {
        return sertime;
    }

    public void setSertime(String sertime) {
        this.sertime = sertime;
    }

    public String getSerarea() {
        return serarea;
    }

    public void setSerarea(String serarea) {
        this.serarea = serarea;
    }
}
