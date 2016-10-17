package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LtqyxxDAO {
    private static Map<String, String> m;

    public static String getLtqyxxCount(Map<String, String> m) {
        return DBManager.getString("getLtqyxxCount", m);
    }

    public static List<LtqyxxBean> getLtqyxxList(Map<String, String> m) {
        LtqyxxDAO.m = m;
        return DBManager.queryFList("getLtqyxxList", m);
    }
    
    public static LtqyxxBean getLtqyxxBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (LtqyxxBean) DBManager.queryFObj("getLtqyxxBean", m);
    }

    public static List<LtqyxxBean> getAllLtqyxx() {
        return DBManager.queryFList("getAllLtqyxx", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeLtqyxxStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "绿色食品证书",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertLtqyxx(LtqyxxBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertLtqyxx", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "绿色食品证书", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateLtqyxx(LtqyxxBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateLtqyxx", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "绿色食品证书", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteLtqyxx(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteLtqyxx", m)) {
            PublicTableDAO.insertSettingLogs("删除", "绿色食品证书", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}