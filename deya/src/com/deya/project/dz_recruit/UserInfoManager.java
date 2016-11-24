package com.deya.project.dz_recruit;

import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.dao.PublicTableDAO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class UserInfoManager {

    public static UserInfoBean getUserInfoBean(String id,String tokenId) {
        return UserInfoDAO.getUserInfoBean(id,tokenId);
    }

    public static UserInfoBean getUserInfoBean(HttpServletRequest request) {
        Object wxOpenId = request.getSession().getAttribute("wxOpenId");
        if(wxOpenId != null){
            return UserInfoDAO.getUserInfoBean(null,wxOpenId.toString());
        }
        return null;
    }

    public static boolean insertUserInfo(UserInfoBean hb, HttpServletRequest request) {
        Object wxOpenId = request.getSession().getAttribute("wxOpenId");
        if(wxOpenId != null){
            hb.setId(PublicTableDAO.getIDByTableName("dz_user_info"));
            hb.setTokenId(wxOpenId.toString());
            return UserInfoDAO.insertUserInfo(hb);
        }
        else{
            return false;
        }
    }

    public static boolean updateUserInfo(UserInfoBean hb, HttpServletRequest request) {
        Object wxOpenId = request.getSession().getAttribute("wxOpenId");
        if(wxOpenId != null){
            hb.setTokenId(wxOpenId.toString());
            return UserInfoDAO.updateUserInfo(hb);
        }
        else{
            return false;
        }
    }

    public static boolean isToudi(Integer info_id, HttpServletRequest request){
        Object wxOpenId = request.getSession().getAttribute("wxOpenId");
        Integer user_id = 0;
        if(wxOpenId != null){
            UserInfoBean userInfoBean = UserInfoDAO.getUserInfoBean(null, wxOpenId.toString());
            user_id = userInfoBean.getId();
            List<UserInfoRelationBean> userInfoRelation = UserInfoDAO.getUserInfoRelation(info_id, user_id);
            if(userInfoRelation != null && userInfoRelation.size() > 0){
                return true;
            }
        }
        return false;
    }

    public static List<InfoBean> getZpxxInfoList(Map<String, String> con_map){
        return UserInfoDAO.getZpxxInfoList(con_map);
    }

    public static String getZpxxInfoCount(Integer user_id){
        return UserInfoDAO.getZpxxInfoCount(user_id);
    }

    public static List<UserInfoBean> getUserInfoList(Map<String, String> con_map){
        return UserInfoDAO.getUserInfoList(con_map);
    }

    public static String getUserInfoCount(Integer info_id){
        return UserInfoDAO.getUserInfoCount(info_id);
    }

    public static boolean insertUserInfoRelation(Integer info_id, HttpServletRequest request) {
        Object wxOpenId = request.getSession().getAttribute("wxOpenId");
        if(wxOpenId != null){
            UserInfoRelationBean userInfoRelationBean = new UserInfoRelationBean();
            userInfoRelationBean.setId(PublicTableDAO.getIDByTableName("dz_user_info_relation"));
            UserInfoBean userInfoBean = getUserInfoBean(null, wxOpenId.toString());
            userInfoRelationBean.setUser_id(userInfoBean.getId());
            userInfoRelationBean.setInfo_id(info_id);
            return UserInfoDAO.insertUserInfoRelation(userInfoRelationBean);
        }
        else{
            return false;
        }
    }
}