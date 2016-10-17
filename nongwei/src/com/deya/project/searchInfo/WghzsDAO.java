package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WghzsDAO {
    private static Map<String, String> m;

    public static String getWghzsCount(Map<String, String> m) {
        return DBManager.getString("getWghzsCount", m);
    }

    public static List<WghzsBean> getWghzsList(Map<String, String> m) {
        WghzsDAO.m = m;
        return DBManager.queryFList("getWghzsList", m);
    }
    
    public static WghzsBean getWghzsBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (WghzsBean) DBManager.queryFObj("getWghzsBean", m);
    }

    public static List<WghzsBean> getAllWghzs() {
        return DBManager.queryFList("getAllWghzs", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeWghzsStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "无公害产品证书",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertWghzs(WghzsBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertWghzs", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "无公害产品证书", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateWghzs(WghzsBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateWghzs", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "无公害产品证书", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteWghzs(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteWghzs", m)) {
            PublicTableDAO.insertSettingLogs("删除", "无公害产品证书", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}