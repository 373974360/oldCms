package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LsspzsDAO {
    private static Map<String, String> m;

    public static String getLsspzsCount(Map<String, String> m) {
        return DBManager.getString("getLsspzsCount", m);
    }

    public static List<LsspzsBean> getLsspzsList(Map<String, String> m) {
        LsspzsDAO.m = m;
        return DBManager.queryFList("getLsspzsList", m);
    }
    
    public static LsspzsBean getLsspzsBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (LsspzsBean) DBManager.queryFObj("getLsspzsBean", m);
    }

    public static List<LsspzsBean> getAllLsspzs() {
        return DBManager.queryFList("getAllLsspzs", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeLsspzsStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "绿色食品证书",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertLsspzs(LsspzsBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertLsspzs", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "绿色食品证书", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateLsspzs(LsspzsBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateLsspzs", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "绿色食品证书", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteLsspzs(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteLsspzs", m)) {
            PublicTableDAO.insertSettingLogs("删除", "绿色食品证书", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}