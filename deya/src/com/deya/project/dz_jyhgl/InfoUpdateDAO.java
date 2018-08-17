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
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("gz_id",gz_id);
        return (InfoUpdateBean)DBManager.queryFObj("getInfoUpdateById",map);
    }
    public static boolean insertInfoUpdate(InfoUpdateBean bean){
        try {
            bean.setGz_id(PublicTableDAO.getIDByTableName("cs_info_update"));
            String currTime = DateUtil.getCurrentDateTime();
            bean.setGz_time(currTime);
            bean.setGz_nexttime(DateUtil.getDateTimeString(DateUtil.getDateTimesAfter(currTime,bean.getGz_day())));
            DBManager.insert("insertInfoUpdate",bean);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateInfoUpdate(InfoUpdateBean bean){
        try {
            bean.setGz_nexttime(DateUtil.getDateTimeString(DateUtil.getDateTimesAfter(bean.getGz_time(),bean.getGz_day())));
            DBManager.update("updateInfoUpdate",bean);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static boolean deleteInfoUpdate(int gz_id){
        try {
            Map<String,Integer> map = new HashMap<String,Integer>();
            map.put("gz_id",gz_id);
            DBManager.delete("deleteInfoUpdate",map);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertInfoUpdateCategory(String cat_ids,int gz_id){
        try {
            Map<String,String> map = new HashMap<String,String>();
            map.put("cat_ids",cat_ids);
            DBManager.delete("clearInfoUpdateCategoryByGzId",map);

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
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("gz_id",gz_id);
        return DBManager.getString("getInfoUpdateCategoryByGzId",map);
    }
}
