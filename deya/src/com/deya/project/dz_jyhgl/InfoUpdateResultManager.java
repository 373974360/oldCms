package com.deya.project.dz_jyhgl;


import java.util.List;
import java.util.Map;

public class InfoUpdateResultManager {

    public static List<InfoUpdateResultBean> getInfoUpdateResultList(Map<String, String> map){
        return InfoUpdateResultDAO.getInfoUpdateResultList(map);
    }

    public static boolean insertInfoUpdateResult(InfoUpdateResultBean bean){
        return InfoUpdateResultDAO.insertInfoUpdateResult(bean);
    }
    public static boolean clearInfoUpdateResult(){
        return InfoUpdateResultDAO.clearInfoUpdateResult();
    }
}
