package com.deya.sms;


import java.io.Serializable;
import java.util.List;

/**
 * @Description: submail短信发送、短信模板返回结果类
 * @User: like
 * @Date: 2016/8/19 11:12
 * @Version: 1.0
 * @Created with IntelliJ IDEA.
 */
public class SubMailMessageResultVo implements Serializable {

    private String status;
    private String to;
    private String send_id;
    private Float fee;
    private String sms_credits;
    private String code;
    private String msg;
    private String template_id;
    private List<SubMailTemplateVo> templates;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSend_id() {
        return send_id;
    }

    public void setSend_id(String send_id) {
        this.send_id = send_id;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public String getSms_credits() {
        return sms_credits;
    }

    public void setSms_credits(String sms_credits) {
        this.sms_credits = sms_credits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public List<SubMailTemplateVo> getTemplates() {
        return templates;
    }

    public void setTemplates(List<SubMailTemplateVo> templates) {
        this.templates = templates;
    }

}
