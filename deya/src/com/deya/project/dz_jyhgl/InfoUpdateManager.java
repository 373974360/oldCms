package com.deya.project.dz_jyhgl;

import java.util.List;
import java.util.Map;

public class InfoUpdateManager {
    public static List<InfoUpdateBean> getInfoUpdateList(Map<String, String> map){
        return InfoUpdateDAO.getInfoUpdateList(map);
    }
    public static int getInfoUpdateCount(Map<String, String> map){
        return InfoUpdateDAO.getInfoUpdateCount(map);
    }
    public static InfoUpdateBean getInfoUpdateById(int gz_id){
        return InfoUpdateDAO.getInfoUpdateById(gz_id);
    }
    public static boolean insertInfoUpdate(InfoUpdateBean bean){
        return InfoUpdateDAO.insertInfoUpdate(bean);
    }
    public static boolean updateInfoUpdate(InfoUpdateBean bean){
        return InfoUpdateDAO.updateInfoUpdate(bean);
    }
    public static boolean deleteInfoUpdate(int gz_id){
        return InfoUpdateDAO.deleteInfoUpdate(gz_id);
    }
    public static boolean insertInfoUpdateCategory(String cat_ids,int gz_id){
        return InfoUpdateDAO.insertInfoUpdateCategory(cat_ids,gz_id);
    }
    public static String getInfoUpdateCategoryByGzId(int gz_id){
        return InfoUpdateDAO.getInfoUpdateCategoryByGzId(gz_id);
    }
}
