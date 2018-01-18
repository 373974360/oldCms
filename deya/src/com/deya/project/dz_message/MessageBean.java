package com.deya.project.dz_message;

import java.io.Serializable;

public class MessageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id = 0;//主键ID
    private String title = "";//信息标题
    private String released_dtime="";//发布时间
    private String contents="";//正文内容
    private String jimi="";//机密等级
    private String jinji="";//加急等级
    private int info_status=1;//报送状态，1未发送，2已发送
    private int input_user=0;//录入人ID
    private String input_dtime="";//入库时间
    private int modify_user=0;//修改人ID
    private String modify_dtime="";//修改时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleased_dtime() {
        return released_dtime;
    }

    public void setReleased_dtime(String released_dtime) {
        this.released_dtime = released_dtime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getInfo_status() {
        return info_status;
    }

    public void setInfo_status(int info_status) {
        this.info_status = info_status;
    }

    public int getInput_user() {
        return input_user;
    }

    public void setInput_user(int input_user) {
        this.input_user = input_user;
    }

    public String getInput_dtime() {
        return input_dtime;
    }

    public void setInput_dtime(String input_dtime) {
        this.input_dtime = input_dtime;
    }

    public int getModify_user() {
        return modify_user;
    }

    public void setModify_user(int modify_user) {
        this.modify_user = modify_user;
    }

    public String getModify_dtime() {
        return modify_dtime;
    }

    public void setModify_dtime(String modify_dtime) {
        this.modify_dtime = modify_dtime;
    }

    public String getJimi() {
        return jimi;
    }

    public void setJimi(String jimi) {
        this.jimi = jimi;
    }

    public String getJinji() {
        return jinji;
    }

    public void setJinji(String jinji) {
        this.jinji = jinji;
    }
}
