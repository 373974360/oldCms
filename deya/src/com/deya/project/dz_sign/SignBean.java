package com.deya.project.dz_sign;

import com.deya.wcm.bean.member.MemberBean;

import java.io.Serializable;

/**
 * 描述：
 *
 * @Author: MaChaoWei
 * @Date: 2019/10/8 14:52
 */
public class SignBean extends MemberBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int sign_id;
    private int member_id;
    private String hall_name;
    private String sign_time;
    private int sign_status;
    private String theme_name;

    public int getSign_id() {
        return sign_id;
    }

    public void setSign_id(int sign_id) {
        this.sign_id = sign_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getHall_name() {
        return hall_name;
    }

    public void setHall_name(String hall_name) {
        this.hall_name = hall_name;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public int getSign_status() {
        return sign_status;
    }

    public void setSign_status(int sign_status) {
        this.sign_status = sign_status;
    }

    public String getTheme_name() {
        return theme_name;
    }

    public void setTheme_name(String theme_name) {
        this.theme_name = theme_name;
    }
}
