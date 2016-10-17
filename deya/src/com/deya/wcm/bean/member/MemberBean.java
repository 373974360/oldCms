package com.deya.wcm.bean.member;

import java.io.Serializable;

public class MemberBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -475378874975925048L;

    private String me_id = "";
    private String mcat_id = "";
    private String me_realname = "";
    private String me_nickname = "";
    private String me_card_id = "";
    private int me_sex = 0;// 0/1 --female/man
    private String me_vocation = "";
    private String me_age = "";
    private String me_address = "";
    private String me_email = "";
    private String me_tel = "";
    private String me_phone = "";
    private int me_status = 0;
    private String app_id = "";
    private String site_id = "";

    public String getMe_id() {
        return me_id;
    }
    public String getMcat_id() {
        return mcat_id;
    }
    public String getMe_realname() {
        return me_realname;
    }
    public String getMe_nickname() {
        return me_nickname;
    }
    public String getMe_card_id() {
        return me_card_id;
    }
    public int getMe_sex() {
        return me_sex;
    }
    public String getMe_vocation() {
        return me_vocation;
    }
    public String getMe_age() {
        return me_age;
    }
    public String getMe_address() {
        return me_address;
    }
    public String getMe_email() {
        return me_email;
    }
    public String getMe_tel() {
        return me_tel;
    }
    public String getMe_phone() {
        return me_phone;
    }
    public int getMe_status() {
        return me_status;
    }
    public String getApp_id() {
        return app_id;
    }
    public String getSite_id() {
        return site_id;
    }
    public void setMe_id(String meId) {
        me_id = meId;
    }
    public void setMcat_id(String mcatId) {
        mcat_id = mcatId;
    }

    public void setMe_realname(String meRealname) {
        me_realname = meRealname;
    }
    public void setMe_nickname(String meNickname) {
        me_nickname = meNickname;
    }
    public void setMe_card_id(String meCardId) {
        me_card_id = meCardId;
    }
    public void setMe_sex(int meSex) {
        me_sex = meSex;
    }
    public void setMe_vocation(String meVocation) {
        me_vocation = meVocation;
    }
    public void setMe_age(String meAge) {
        me_age = meAge;
    }
    public void setMe_address(String meAddress) {
        me_address = meAddress;
    }
    public void setMe_email(String meEmail) {
        me_email = meEmail;
    }
    public void setMe_tel(String meTel) {
        me_tel = meTel;
    }
    public void setMe_phone(String mePhone) {
        me_phone = mePhone;
    }
    public void setMe_status(int meStatus) {
        me_status = meStatus;
    }
    public void setApp_id(String appId) {
        app_id = appId;
    }
    public void setSite_id(String siteId) {
        site_id = siteId;
    }



}
