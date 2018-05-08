package com.deya.project.lyh_member;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import com.deya.project.lyh_member.LyhMemberBean;
import com.deya.project.lyh_member.LyhMemberLogin;
import com.deya.project.lyh_member.LyhMemberManager;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.org.user.SessionManager;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class LyhMemberRPC {
    public LyhMemberRPC() {
    }

    public static String getLyhMemberCount(Map<String, String> m) {
        return LyhMemberManager.getLyhMemberCount(m);
    }

    public static List<LyhMemberBean> getAllLyhMemberList(Map<String, String> m) {
        return LyhMemberManager.getAllLyhMemberList(m);
    }

    public static LyhMemberBean getLyhMemberBean(String gq_id, boolean is_browser) {
        return LyhMemberManager.getLyhMemberBean(gq_id, is_browser);
    }

    public static boolean updateLyhMember(LyhMemberBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        return stl != null?LyhMemberManager.updateLyhMember(hb, stl):false;
    }

    public static boolean publishLyhMember(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        return stl != null?LyhMemberManager.publishLyhMember(m, stl):false;
    }

    public static boolean deleteLyhMember(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        return stl != null?LyhMemberManager.deleteLyhMember(m, stl):false;
    }

    public static LyhMemberBean getLyhMemberBySession(HttpServletRequest request) {
        return LyhMemberManager.getMemberBySession(request);
    }

    public static void setUrlToSession(String url, HttpServletRequest request) {
        SessionManager.set(request, "cicro_wcm_lyhmember_pro_url", url);
    }

    public static String memberLogin(String me_account, String me_password, HttpServletRequest request) {
        return LyhMemberLogin.memberLogin(me_account, me_password, request);
    }

    public static String getUrlFoSesson(HttpServletRequest request) {
        return (String)SessionManager.get(request, "cicro_wcm_lyhmember_pro_url");
    }

    public static boolean logout(HttpServletRequest request) {
        return LyhMemberLogin.logout(request);
    }

    public static String decode(String password) {
        return LyhMemberManager.decode(password);
    }

    public static String encode(String password) {
        return LyhMemberManager.encode(password);
    }

    public static String memberOutExcel(List listBean, List headerList) {
        return LyhMemberManager.memberOutExcel(listBean, headerList);
    }
}
