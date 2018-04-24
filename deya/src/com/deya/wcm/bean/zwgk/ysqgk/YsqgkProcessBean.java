package com.deya.wcm.bean.zwgk.ysqgk;

public class YsqgkProcessBean implements java.io.Serializable {
    private int pro_id=0;
    private int ysq_id=0;
    private int do_dept=0;
    private String do_dept_name="";
    private int old_dept=0;
    private String old_dept_name="";
    private String pro_content="";
    private String pro_dtime="";

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public int getYsq_id() {
        return ysq_id;
    }

    public void setYsq_id(int ysq_id) {
        this.ysq_id = ysq_id;
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

    public int getOld_dept() {
        return old_dept;
    }

    public void setOld_dept(int old_dept) {
        this.old_dept = old_dept;
    }

    public String getOld_dept_name() {
        return old_dept_name;
    }

    public void setOld_dept_name(String old_dept_name) {
        this.old_dept_name = old_dept_name;
    }

    public String getPro_content() {
        return pro_content;
    }

    public void setPro_content(String pro_content) {
        this.pro_content = pro_content;
    }

    public String getPro_dtime() {
        return pro_dtime;
    }

    public void setPro_dtime(String pro_dtime) {
        this.pro_dtime = pro_dtime;
    }
}
