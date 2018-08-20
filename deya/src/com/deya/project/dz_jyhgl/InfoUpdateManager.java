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
    public static int getInfoUpdateCountByTimeAndCatId(String time,int cat_id){
        return InfoUpdateDAO.getInfoUpdateCountByTimeAndCatId(time,cat_id);
    }
    public static int getInfoUpdateCountByTimeAndCatId(String time,String cat_id){
        return InfoUpdateDAO.getInfoUpdateCountByTimeAndCatId(time,cat_id);
    }

    public static String getInfoMaxReleasedDtime(int cat_id){
        return InfoUpdateDAO.getInfoMaxReleasedDtime(cat_id);
    }

    //首页栏目聚合  最后一条信息的更新时间
    public static String getInfoMaxReleasedDtime(String cat_id){
        return InfoUpdateDAO.getInfoMaxReleasedDtime(cat_id);
    }


    public static List<InfoUpdateDownLoadBean> getDownloadFile(int gz_id,String site_id){
        List<InfoUpdateDownLoadBean> files = new ArrayList<>();
        String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
        String path = FormatUtil.formatPath(root_path + "/project/dz_jyhgl/"+site_id+"/"+gz_id+"/");
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            InfoUpdateDownLoadBean downLoadBean = new InfoUpdateDownLoadBean();
            if (tempList[i].isFile()) {
                downLoadBean.setFile_name(tempList[i].getName());
                downLoadBean.setFile_path("/sys/project/dz_jyhgl/"+site_id+"/"+gz_id+"/"+tempList[i].getName());
                files.add(downLoadBean);
            }
        }
        return files;
    }


}
