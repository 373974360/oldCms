package com.deya.project.dz_jyhgl;

import com.deya.util.DateUtil;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoUpdateDAO {
    public static List<InfoUpdateBean> getInfoUpdateList(Map<String, String> map){
        return DBManager.queryFList("getInfoUpdateList",map);
    }
    public static int getInfoUpdateCount(Map<String, String> map){
        return Integer.parseInt(DBManager.getString("getInfoUpdateCount",map));
    }
    public static InfoUpdateBean getInfoUpdateById(int gz_id){
        Map<String,Integer> map = new HashMap<>();
        map.put("gz_id",gz_id);
        return (InfoUpdateBean)DBManager.queryFObj("getInfoUpdateById",map);
    }
    public static boolean insertInfoUpdate(InfoUpdateBean bean){
        try {
            bean.setGz_id(PublicTableDAO.getIDByTableName("cs_info_update"));
            bean.setGz_time(DateUtil.getCurrentDate()+" 00:00:00");
            DBManager.insert("insertInfoUpdate",bean);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateInfoUpdate(InfoUpdateBean bean){
        try {
            DBManager.update("updateInfoUpdate",bean);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static boolean deleteInfoUpdate(int gz_id){
        try {
            Map<String,Integer> map = new HashMap<>();
            map.put("gz_id",gz_id);
            DBManager.delete("deleteInfoUpdate",map);

            Map<String,String> map_1 = new HashMap<>();
            map_1.put("gz_id",gz_id+"");
            DBManager.delete("clearInfoUpdateCategory",map_1);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertInfoUpdateCategory(String cat_ids,int gz_id){
        try {
            InfoUpdateBean infoUpdateBean = getInfoUpdateById(gz_id);
            Map<String,String> map = new HashMap<>();
            if(infoUpdateBean.getGz_type()==2){//只有列表页的监测配置  一个栏目只能配一个规则
                map.put("cat_ids",cat_ids);
            }
            map.put("gz_id",gz_id+"");
            DBManager.delete("clearInfoUpdateCategory",map);

            String catArray[] = cat_ids.split(",");
            if(ArrayUtils.isNotEmpty(catArray)){
                for(String cat:catArray){
                    InfoUpdateCategoryBean bean = new InfoUpdateCategoryBean();
                    bean.setId(PublicTableDAO.getIDByTableName("cs_info_update_category"));
                    bean.setCat_id(Integer.parseInt(cat));
                    bean.setGz_id(gz_id);
                    DBManager.insert("insertInfoUpdateCategory",bean);
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static String getInfoUpdateCategoryByGzId(int gz_id){
        Map<String,Integer> map = new HashMap<>();
        map.put("gz_id",gz_id);
        return DBManager.getString("getInfoUpdateCategoryByGzId",map);
    }

    public static List<InfoUpdateBean> getStartInfoUpdateList(String currTime){
        Map<String,String> map = new HashMap<>();
        map.put("curr_time",currTime);
        return DBManager.queryFList("getStartInfoUpdateList",map);
    }

    public static List<InfoUpdateCategoryBean> getStartInfoUpdateCategoryList(int gz_id){
        Map<String,Integer> map = new HashMap<>();
        map.put("gz_id",gz_id);
        return DBManager.queryFList("getStartInfoUpdateCategoryList",map);
    }

    public static int getInfoUpdateCountByTimeAndCatId(String starttime,String endtime,String cat_id){
        Map<String,String> map = new HashMap<>();
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        map.put("cat_id",cat_id);
        return Integer.parseInt(DBManager.getString("getInfoUpdateCountByTimeAndCatId",map));
    }


    public static int getSqPublishCount(String starttime,String endtime){
        Map<String,String> map = new HashMap<>();
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        return Integer.parseInt(DBManager.getString("getSqPublishCount",map));
    }


    public static int getSurveyPublishCount(String starttime,String endtime,String site_id){
        Map<String,String> map = new HashMap<>();
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        map.put("site_id",site_id);
        return Integer.parseInt(DBManager.getString("getSurveyPublishCount",map));
    }

    public static int getSubjectApplyCount(String starttime,String endtime,String site_id){
        Map<String,String> map = new HashMap<>();
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        map.put("site_id",site_id);
        return Integer.parseInt(DBManager.getString("getSubjectApplyCount",map));
    }
}
