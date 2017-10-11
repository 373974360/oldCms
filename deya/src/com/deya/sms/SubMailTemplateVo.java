package com.deya.sms;

import java.io.Serializable;

/**
 * submail短信模板对象
 * Created by program on 2016/11/24.
 */
public class SubMailTemplateVo implements Serializable {
    private String id;
    private String template_id;
    private String sms_title;
    private String sms_signature;
    private String sms_content;
    private String add_date;
    private String edit_date;
    private String template_status;
    private String template_status_description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
        this.id = template_id;
    }

    public String getSms_title() {
        return sms_title;
    }

    public void setSms_title(String sms_title) {
        this.sms_title = sms_title;
    }

    public String getSms_signature() {
        return sms_signature;
    }

    public void setSms_signature(String sms_signature) {
        this.sms_signature = sms_signature;
    }

    public String getSms_content() {
        return sms_content;
    }

    public void setSms_content(String sms_content) {
        this.sms_content = sms_content;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    public String getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(String edit_date) {
        this.edit_date = edit_date;
    }

    public String getTemplate_status() {
        return template_status;
    }

    public void setTemplate_status(String template_status) {
        this.template_status = template_status;
    }

    public String getTemplate_status_description() {
        return template_status_description;
    }

    public void setTemplate_status_description(String template_status_description) {
        this.template_status_description = template_status_description;
    }
}
