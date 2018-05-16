package com.yinhai.model;

import java.util.Map;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-08-31 13:46
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class InfoWorkStep  implements java.io.Serializable,Cloneable{

    private int id;
    private int info_id;
    private int user_id;
    private String user_name;
    private int step_id;
    private int pass_status;
    private String description;
    private String work_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getStep_id() {
        return step_id;
    }

    public void setStep_id(int step_id) {
        this.step_id = step_id;
    }

    public int getPass_status() {
        return pass_status;
    }

    public void setPass_status(int pass_status) {
        this.pass_status = pass_status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }
}
