package com.deya.project.salarySearch;

/**
 * Created with IntelliJ IDEA.
 * User: like
 * Date: 2016/3/21
 * Time: 14:05
 * Description:
 * Version: v1.0
 */
public class SalaryUserBean {

    private static final long serialVersionUID = 1L;
    private int id = 0;
    private String name = "";
    private String department = "";
    private String identify = "";
    private String password = "";
    private String addTime = "";
    private String updateTime = "";
    private String status = "";

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
