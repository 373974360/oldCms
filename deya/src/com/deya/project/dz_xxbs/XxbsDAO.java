package com.deya.project.dz_xxbs;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XxbsDAO {

    public static List<XxbsBean> getXxbsInfoList(Map<String, String> map) {
        return DBManager.queryFList("getXxbsInfoList", map);
    }

    public static String getXxbsInfoCount(Map<String, String> map) {
        return DBManager.getString("getXxbsInfoCount", map);
    }


    public static XxbsBean getXxbs(String id) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",id);
        return (XxbsBean)DBManager.queryFObj("getXxbs", map);
    }

    public static boolean insertXxbs(XxbsBean xxbs, SettingLogsBean stl) {
        if (DBManager.insert("insertXxbs", xxbs)) {
            PublicTableDAO.insertSettingLogs("添加", "报送信息", xxbs.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateXxbs(XxbsBean xxbs, SettingLogsBean stl) {
        if (DBManager.update("updateXxbs", xxbs)) {
            PublicTableDAO.insertSettingLogs("编辑", "报送信息", xxbs.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteXxbs(List<XxbsBean> list, SettingLogsBean stl) {
        if (list != null && list.size() > 0) {
            String ids = "";
            for(XxbsBean b:list){
                ids+=b.getId()+",";
            }
            ids = ids.substring(0,ids.length()-1);
            Map<String,String> map = new HashMap<String,String>();
            map.put("ids",ids);
            if (DBManager.delete("deleteXxbs", map)) {
                PublicTableDAO.insertSettingLogs("删除", "报送信息", ids, stl);
                return true;
            }
        }
        return false;
    }

    public static boolean updateInfoStatus(List<XxbsBean> list,String status, SettingLogsBean stl) {
        if (list != null && list.size() > 0) {
            String ids = "";
            for(XxbsBean b:list){
                ids+=b.getId()+",";
            }
            ids = ids.substring(0,ids.length()-1);
            Map<String,String> map = new HashMap<String,String>();
            map.put("ids",ids);
            map.put("info_status",status);
            if (DBManager.delete("updateInfoStatus", map)) {
                PublicTableDAO.insertSettingLogs("修改报送信息状态", "报送信息", ids, stl);
                return true;
            }
        }
        return false;
    }

    public static boolean updateNoPassStatus(List<XxbsBean> list,String desc, SettingLogsBean stl) {
        if (list != null && list.size() > 0) {
            String ids = "";
            for(XxbsBean b:list){
                ids+=b.getId()+",";
            }
            ids = ids.substring(0,ids.length()-1);
            Map<String,String> map = new HashMap<String,String>();
            map.put("ids",ids);
            map.put("auto_desc",desc);
            if (DBManager.delete("updateNoPassStatus", map)) {
                PublicTableDAO.insertSettingLogs("报送信息退回", "报送信息", ids, stl);
                return true;
            }
        }
        return false;
    }

    public static boolean infoPass(List<XxbsBean> list,String cat_id,String cat_name, SettingLogsBean stl) {
        if (list != null && list.size() > 0) {
            String ids = "";
            for(XxbsBean b:list){
                ids+=b.getId()+",";
            }
            ids = ids.substring(0,ids.length()-1);
            Map<String,String> map = new HashMap<String,String>();
            map.put("ids",ids);
            map.put("cat_id",cat_id);
            map.put("cat_name",cat_name);
            if (DBManager.delete("infoPass", map)) {
                PublicTableDAO.insertSettingLogs("报送信息采用", "报送信息", ids, stl);
                return true;
            }
        }
        return false;
    }

    public static String getXxbsDeptCount(Map<String, String> map) {
        return DBManager.getString("getXxbsDeptCount", map);
    }
}
