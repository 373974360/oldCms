package com.deya.project.dz_jyhgl;

import java.io.Serializable;

public class InfoUpdateResultBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id = 0;//主键ID
    private int gz_id=0;//规则ID
    private int cat_id=0;//栏目Id
    private String cat_name="";//栏目名称
    private String end_update_time="";//最后更新时间
    private String check_time="";//截止时间
    private int gz_day=0;//监测周期
    private int gz_count=0;//规定更新数量
    private int update_count=0;//实际更新数量
    private String update_desc = "";//是否合格

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGz_id() {
        return gz_id;
    }

    public void setGz_id(int gz_id) {
        this.gz_id = gz_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getEnd_update_time() {
        return end_update_time;
    }

    public void setEnd_update_time(String end_update_time) {
        this.end_update_time = end_update_time;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public int getGz_day() {
        return gz_day;
    }

    public void setGz_day(int gz_day) {
        this.gz_day = gz_day;
    }

    public int getGz_count() {
        return gz_count;
    }

    public void setGz_count(int gz_count) {
        this.gz_count = gz_count;
    }

    public int getUpdate_count() {
        return update_count;
    }

    public void setUpdate_count(int update_count) {
        this.update_count = update_count;
    }

    public String getUpdate_desc() {
        return update_desc;
    }

    public void setUpdate_desc(String update_desc) {
        this.update_desc = update_desc;
    }
}
