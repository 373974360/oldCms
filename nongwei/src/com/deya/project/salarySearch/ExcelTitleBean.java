package com.deya.project.salarySearch;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/3/21
 * Time: 13:21
 * Description:
 * Version: v1.0
 */
public class ExcelTitleBean {
    private static final long serialVersionUID = 1L;
    private int id = 0;
    private int typeId = 0;  //分类ID
    private String ename = "";     //字段英文名称
    private String cname = "";     //字段中文名称
    private int isShow = 0;      //是否显示
    private String addTime = "";
    private String status = "";
    private int sortId = 999;
    private String comments = "";   //备注

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }
}
