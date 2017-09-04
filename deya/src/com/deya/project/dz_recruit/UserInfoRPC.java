package com.deya.project.dz_recruit;

import com.deya.wcm.bean.cms.info.InfoBean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoRPC {

    public static UserInfoBean getUserInfoBean(String id,String tokenId) {
        return UserInfoManager.getUserInfoBean(id,tokenId);
    }

    public static UserInfoBean getUserInfoBean(HttpServletRequest request) {
        return UserInfoManager.getUserInfoBean(request);
    }

    public static boolean updateUserInfo(UserInfoBean hb, HttpServletRequest request) {
        return UserInfoManager.updateUserInfo(hb,request);
    }

    public static boolean insertUserInfo(UserInfoBean hb, HttpServletRequest request) {
        return UserInfoManager.insertUserInfo(hb,request);
    }

    public static boolean insertUserInfoRelation(Integer info_id, HttpServletRequest request) {
        return UserInfoManager.insertUserInfoRelation(info_id,request);
    }

    public static boolean isToudi(Integer info_id, HttpServletRequest request){
        return UserInfoManager.isToudi(info_id,request);
    }

    public static List<InfoBean> getZpxxInfoList(Integer user_id, Integer start_num, Integer page_size) {
        Map<String, String> con_map = new HashMap<String, String>();
        String orderby = "info.id desc";
        con_map.put("user_id", user_id + "");
        con_map.put("page_size", page_size + "");
        con_map.put("start_num", start_num + "");
        con_map.put("orderby", orderby);
        return UserInfoManager.getZpxxInfoList(con_map);
    }

    public static String getZpxxInfoCount(Integer user_id) {
        return UserInfoManager.getZpxxInfoCount(user_id);
    }

    public static List<UserInfoBean> getUserInfoList(Integer info_id, Integer start_num, Integer page_size) {
        Map<String, String> con_map = new HashMap<String, String>();
        String orderby = "ur.info_id desc";
        con_map.put("info_id", info_id + "");
        con_map.put("page_size", page_size + "");
        con_map.put("start_num", start_num + "");
        con_map.put("orderby", orderby);
        return UserInfoManager.getUserInfoList(con_map);
    }

    public static String getUserInfoCount(Integer info_id) {
        return UserInfoManager.getUserInfoCount(info_id);
    }
}