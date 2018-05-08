package com.deya.wcm.dataCollection.bean;

import java.io.Serializable;

/**
 * 信息组件（区块）分类Bean
 * @author Administrator
 * liqi
 */
public class RuleCategoryBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1040597672816207753L;

    private String id = "";
    private String rcat_id = "";//分类ID
    private String parent_id = "";//父ID
    private String rcat_name = "";//分类名称
    private String rcat_position = "";//类目路径
    private int rcat_level = 1;//深度级别
    private String rcat_memo = "";//分类描述
    private int sort_id = 999;
    private String app_id = "";
    private String site_id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRcat_id() {
        return rcat_id;
    }

    public void setRcat_id(String rcat_id) {
        this.rcat_id = rcat_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getRcat_name() {
        return rcat_name;
    }

    public void setRcat_name(String rcat_name) {
        this.rcat_name = rcat_name;
    }

    public String getRcat_position() {
        return rcat_position;
    }

    public void setRcat_position(String rcat_position) {
        this.rcat_position = rcat_position;
    }

    public int getRcat_level() {
        return rcat_level;
    }

    public void setRcat_level(int rcat_level) {
        this.rcat_level = rcat_level;
    }

    public String getRcat_memo() {
        return rcat_memo;
    }

    public void setRcat_memo(String rcat_memo) {
        this.rcat_memo = rcat_memo;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }
}
