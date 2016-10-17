package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyjyqyDAO {
    private static Map<String, String> m;

    public static String getSyjyqyCount(Map<String, String> m) {
        return DBManager.getString("getSyjyqyCount", m);
    }

    public static List<SyjyqyBean> getSyjyqyList(Map<String, String> m) {
        SyjyqyDAO.m = m;
        return DBManager.queryFList("getSyjyqyList", m);
    }
    
    public static SyjyqyBean getSyjyqyBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (SyjyqyBean) DBManager.queryFObj("getSyjyqyBean", m);
    }

    public static List<SyjyqyBean> getAllSyjyqy() {
        return DBManager.queryFList("getAllSyjyqy", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeSyjyqyStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "兽药经营企业",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertSyjyqy(SyjyqyBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertSyjyqy", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "兽药经营企业", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateSyjyqy(SyjyqyBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateSyjyqy", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "兽药经营企业", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteSyjyqy(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteSyjyqy", m)) {
            PublicTableDAO.insertSettingLogs("删除", "兽药经营企业", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}