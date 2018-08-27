package com.deya.project.dz_jyhgl;

import com.deya.util.FormatUtil;
import com.deya.util.jconfig.JconfigUtilContainer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    public static List<InfoUpdateBean> getStartInfoUpdateList(String currTime){
        return InfoUpdateDAO.getStartInfoUpdateList(currTime);
    }
    public static List<InfoUpdateCategoryBean> getStartInfoUpdateCategoryList(int gz_id){
        return InfoUpdateDAO.getStartInfoUpdateCategoryList(gz_id);
    }
    public static int getInfoUpdateCountByTimeAndCatId(String starttime,String endtime,String cat_id){
        return InfoUpdateDAO.getInfoUpdateCountByTimeAndCatId(starttime,endtime,cat_id);
    }
    public static int getSqPublishCount(String starttime,String endtime){
        return InfoUpdateDAO.getSqPublishCount(starttime,endtime);
    }
    public static int getSurveyPublishCount(String starttime,String endtime,String site_id){
        return InfoUpdateDAO.getSurveyPublishCount(starttime,endtime,site_id);
    }
    public static int getSubjectApplyCount(String starttime,String endtime,String site_id){
        return InfoUpdateDAO.getSubjectApplyCount(starttime,endtime,site_id);
    }
}
