package com.deya.project.dz_siteError;
 
import java.io.Serializable;

public class ErrorTypeBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String typeName = "";
	private int sort = 999;
	private String addTime = "";
	private String status = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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
}