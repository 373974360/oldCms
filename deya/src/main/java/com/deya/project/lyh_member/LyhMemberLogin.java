package com.deya.project.lyh_member;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javax.servlet.http.HttpServletRequest;

import com.deya.project.lyh_member.LyhMemberBean;
import com.deya.project.lyh_member.LyhMemberManager;
import com.deya.wcm.services.org.user.SessionManager;
import javax.servlet.http.HttpServletRequest;

public class LyhMemberLogin {
    public LyhMemberLogin() {
    }

    public static String memberLogin(String me_account, String me_password, HttpServletRequest request) {
        String result = LyhMemberManager.memberLogin(me_account, me_password);
        if("true".equals(result)) {
            SessionManager.set(request, "cicro_wcm_lyhmember", LyhMemberManager.getLyhMemberBenaByAccount(me_account));
        }

        return result;
    }

    public static LyhMemberBean getMemberBySession(HttpServletRequest request) {
        return (LyhMemberBean)SessionManager.get(request, "cicro_wcm_lyhmember");
    }

    public static boolean logout(HttpServletRequest request) {
        SessionManager.remove(request, "cicro_wcm_lyhmember");
        return true;
    }
}
