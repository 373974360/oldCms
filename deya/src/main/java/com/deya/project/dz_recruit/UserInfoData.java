package com.deya.project.dz_recruit;


public class UserInfoData {

    public static UserInfoBean getUserInfoObject(String id,String tokenId) {
        return UserInfoManager.getUserInfoBean(id,tokenId);
    }
}