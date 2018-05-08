package com.deya.project.dz_recruit;

import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoDAO {

    public static UserInfoBean getUserInfoBean(String id,String tokenId) {
        Map m = new HashMap();
        m.put("tokenId", tokenId);
        m.put("id", id);
        return (UserInfoBean) DBManager.queryFObj("getUserInfoBean", m);
    }

    public static boolean insertUserInfo(UserInfoBean userInfo) {
        return DBManager.insert("insertUserInfo", userInfo);
    }

    public static boolean updateUserInfo(UserInfoBean userInfo) {
        return DBManager.update("updateUserInfo", userInfo);
    }

    public static List<InfoBean> getZpxxInfoList(Map<String, String> con_map){
        return DBManager.queryFList("getZpxxInfoList",con_map);
    }

    public static String getZpxxInfoCount(Integer user_id){
        Map m = new HashMap();
        m.put("user_id", user_id);
        return DBManager.getString("getZpxxInfoCount",m);
    }

    public static List<UserInfoBean> getUserInfoList(Map<String, String> con_map){
        return DBManager.queryFList("getUserInfoList",con_map);
    }

    public static String getUserInfoCount(Integer info_id){
        Map m = new HashMap();
        m.put("info_id", info_id);
        return DBManager.getString("getUserInfoCount",m);
    }

    public static boolean insertUserInfoRelation(UserInfoRelationBean xmk) {
        return DBManager.insert("insertUserInfoRelation", xmk);
    }

    public static List<UserInfoRelationBean> getUserInfoRelation(Integer info_id,Integer user_id){
        Map m = new HashMap();
        m.put("info_id", info_id);
        m.put("user_id", user_id);
        return DBManager.queryFList("getUserInfoRelation",m);
    }
}