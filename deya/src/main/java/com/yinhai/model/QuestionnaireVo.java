package com.yinhai.model;

import java.io.Serializable;

/**
 * @Description: 银海调查问卷列表返回对象
 * @User: Administrator
 * @Date: 2018/5/7 0007
 */
public class QuestionnaireVo implements Serializable {
    /**
     * 业务流水号 问卷（唯一标识）
     */
    private String ywlsh;
    /**
     * 问卷名称
     */
    private String question_title;
    /**
     * 问卷描述
     */
    private String question_desc;
    /**
     * 创建时间  yyyy-mm-dd hh24:mi:ss
     */
    private String create_date;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 问卷起始时间 yyyy-mm-dd hh24:mi:ss
     */
    private String start_date;
    /**
     * 问卷结束时间 yyyy-mm-dd hh24:mi:ss
     */
    private String end_date;
    /**
     * 问卷附件名称 多个用“|”隔开
     */
    private String question_file_name;
    /**
     * 状态
     */
    private String state;

    public String getYwlsh() {
        return ywlsh;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_desc() {
        return question_desc;
    }

    public void setQuestion_desc(String question_desc) {
        this.question_desc = question_desc;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getQuestion_file_name() {
        return question_file_name;
    }

    public void setQuestion_file_name(String question_file_name) {
        this.question_file_name = question_file_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
