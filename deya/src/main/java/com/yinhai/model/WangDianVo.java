package com.yinhai.model;

import java.io.Serializable;

/**
 * @Description: 银海调查问卷列表返回对象
 * @User: Administrator
 * @Date: 2018/5/7 0007
 */
public class WangDianVo implements Serializable {

    private String networkid;//ID
    private String networkname;//网点名称
    private String networkarea;//所在区域
    private String networkaddress;//网点地址
    private String contactnumber;//联系电话
    private String networklongitud;//经度
    private String networklatitud;//纬度

    private String projectname;//楼盘名称
    private String projectaddress;//项目地址
    private String takebank;//承办银行
    private String receiptcorp;//售房单位

    private String orgname;//机构名称
    private String orgaddress;//机构地址
    private String contacttime;//办公时间


    public String getNetworkid() {
        return networkid;
    }

    public void setNetworkid(String networkid) {
        this.networkid = networkid;
    }

    public String getNetworkname() {
        return networkname;
    }

    public void setNetworkname(String networkname) {
        this.networkname = networkname;
    }

    public String getNetworkarea() {
        return networkarea;
    }

    public void setNetworkarea(String networkarea) {
        this.networkarea = networkarea;
    }

    public String getNetworkaddress() {
        return networkaddress;
    }

    public void setNetworkaddress(String networkaddress) {
        this.networkaddress = networkaddress;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getNetworklongitud() {
        return networklongitud;
    }

    public void setNetworklongitud(String networklongitud) {
        this.networklongitud = networklongitud;
    }

    public String getNetworklatitud() {
        return networklatitud;
    }

    public void setNetworklatitud(String networklatitud) {
        this.networklatitud = networklatitud;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectaddress() {
        return projectaddress;
    }

    public void setProjectaddress(String projectaddress) {
        this.projectaddress = projectaddress;
    }

    public String getTakebank() {
        return takebank;
    }

    public void setTakebank(String takebank) {
        this.takebank = takebank;
    }

    public String getReceiptcorp() {
        return receiptcorp;
    }

    public void setReceiptcorp(String receiptcorp) {
        this.receiptcorp = receiptcorp;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgaddress() {
        return orgaddress;
    }

    public void setOrgaddress(String orgaddress) {
        this.orgaddress = orgaddress;
    }

    public String getContacttime() {
        return contacttime;
    }

    public void setContacttime(String contacttime) {
        this.contacttime = contacttime;
    }
}
