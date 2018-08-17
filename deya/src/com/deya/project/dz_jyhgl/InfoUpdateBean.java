package com.deya.project.dz_jyhgl;

import java.io.Serializable;

public class InfoUpdateBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int gz_id=0;//主键ID
    private String gz_name="";//规则名称
    private int gz_day=0;//检查频率  检查间隔天数
    private int gz_count=0;//信息条数  间隔天数内需要更新的信息条数
    private String site_id="";//站点ID
    private String gz_nexttime="";//下次检查时间

    public int getGz_id() {
        return gz_id;
    }

    public void setGz_id(int gz_id) {
        this.gz_id = gz_id;
    }

    public String getGz_name() {
        return gz_name;
    }

    public void setGz_name(String gz_name) {
        this.gz_name = gz_name;
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

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getGz_nexttime() {
        return gz_nexttime;
    }

    public void setGz_nexttime(String gz_nexttime) {
        this.gz_nexttime = gz_nexttime;
    }
}
