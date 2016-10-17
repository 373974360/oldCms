package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LmzmqyDAO {
    private static Map<String, String> m;

    public static String getLmzmqyCount(Map<String, String> m) {
        return DBManager.getString("getLmzmqyCount", m);
    }

    public static List<LmzmqyBean> getLmzmqyList(Map<String, String> m) {
        LmzmqyDAO.m = m;
        return DBManager.queryFList("getLmzmqyList", m);
    }
    
    public static LmzmqyBean getLmzmqyBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (LmzmqyBean) DBManager.queryFObj("getLmzmqyBean", m);
    }

    public static List<LmzmqyBean> getAllLmzmqy() {
        return DBManager.queryFList("getAllLmzmqy", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeLmzmqyStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "林木种苗企业信息",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertLmzmqy(LmzmqyBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertLmzmqy", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "林木种苗企业信息", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateLmzmqy(LmzmqyBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateLmzmqy", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "林木种苗企业信息", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteLmzmqy(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteLmzmqy", m)) {
            PublicTableDAO.insertSettingLogs("删除", "林木种苗企业信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}