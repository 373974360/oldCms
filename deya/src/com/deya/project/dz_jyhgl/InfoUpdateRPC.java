package com.deya.project.dz_jyhgl;

import java.util.List;
import java.util.Map;

public class InfoUpdateRPC {
    public static List<InfoUpdateBean> getInfoUpdateList(Map<String, String> map){
        return InfoUpdateManager.getInfoUpdateList(map);
    }
    public static int getInfoUpdateCount(Map<String, String> map){
        return InfoUpdateManager.getInfoUpdateCount(map);
    }

    public static InfoUpdateBean getInfoUpdateById(int gz_id){
        return InfoUpdateManager.getInfoUpdateById(gz_id);
    }
    public static boolean insertInfoUpdate(InfoUpdateBean bean){
        return InfoUpdateManager.insertInfoUpdate(bean);
    }
    public static boolean updateInfoUpdate(InfoUpdateBean bean){
        return InfoUpdateManager.updateInfoUpdate(bean);
    }
    public static boolean deleteInfoUpdate(int gz_id){
        return InfoUpdateManager.deleteInfoUpdate(gz_id);
    }
    public static boolean insertInfoUpdateCategory(String cat_ids,int gz_id){
        return InfoUpdateManager.insertInfoUpdateCategory(cat_ids,gz_id);
    }
    public static String getInfoUpdateCategoryByGzId(int gz_id){
        return InfoUpdateManager.getInfoUpdateCategoryByGzId(gz_id);
    }
    public static List<InfoUpdateDownLoadBean> getDownloadFile(int gz_id,String site_id){
        return InfoUpdateManager.getDownloadFile(gz_id,site_id);
    }
}
