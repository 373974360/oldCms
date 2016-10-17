package com.deya.project.salarySearch;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/3/21
 * Time: 14:05
 * Description:
 * Version: v1.0
 */
public class SalaryBean {

    private static final long serialVersionUID = 1L;
    private int id = 0;
    private int userId = 0;
    private String salaryDate = "";
    private int excelTitleId = 0;
    private String excelData = "";
    private String addTime = "";
    private String updateTime = "";
    private String status = "";

    private String excelName = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(String salaryDate) {
        this.salaryDate = salaryDate;
    }

    public int getExcelTitleId() {
        return excelTitleId;
    }

    public void setExcelTitleId(int excelTitleId) {
        this.excelTitleId = excelTitleId;
    }

    public String getExcelData() {
        return excelData;
    }

    public void setExcelData(String excelData) {
        this.excelData = excelData;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }
}
