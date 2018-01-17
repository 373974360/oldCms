package com.deya.project.dz_xxbs;

import java.io.Serializable;

public class XxbsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id = 0;//主键ID
    private String title = "";//信息标题
    private int do_dept = 0;//报送部门
    private String do_dept_name = "";//报送部门名称
    private String thumb_url = "";//标题图片
    private int islink=1;//是否外联，1否，2是
    private String content_url="";//外链地址
    private String author="";//作者
    private String description="";//摘要
    private String keywords="";//关键字
    private String editor="";//网络编辑
    private String released_dtime="";//发布时间
    private String contents="";//正文内容
    private int info_status=1;//报送状态，1等待，2已采用，3已退稿
    private int input_user=0;//录入人ID
    private String input_dtime="";//入库时间
    private int modify_user=0;//修改人ID
    private String modify_dtime="";//修改时间
    private String auto_desc="";//退稿意见

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

    public int getDo_dept() {
        return do_dept;
    }

    public void setDo_dept(int do_dept) {
        this.do_dept = do_dept;
    }

    public String getDo_dept_name() {
        return do_dept_name;
    }

    public void setDo_dept_name(String do_dept_name) {
        this.do_dept_name = do_dept_name;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public int getIslink() {
        return islink;
    }

    public void setIslink(int islink) {
        this.islink = islink;
    }

    public String getContent_url() {
        return content_url;
    }

    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
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

    public String getAuto_desc() {
        return auto_desc;
    }

    public void setAuto_desc(String auto_desc) {
        this.auto_desc = auto_desc;
    }
}
