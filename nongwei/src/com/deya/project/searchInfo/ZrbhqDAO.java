package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZrbhqDAO {
    private static Map<String, String> m;

    public static String getZrbhqCount(Map<String, String> m) {
        return DBManager.getString("getZrbhqCount", m);
    }

    public static List<ZrbhqBean> getZrbhqList(Map<String, String> m) {
        ZrbhqDAO.m = m;
        return DBManager.queryFList("getZrbhqList", m);
    }
    
    public static ZrbhqBean getZrbhqBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (ZrbhqBean) DBManager.queryFObj("getZrbhqBean", m);
    }

    public static List<ZrbhqBean> getAllZrbhq() {
        return DBManager.queryFList("getAllZrbhq", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeZrbhqStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "自然保护区",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertZrbhq(ZrbhqBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertZrbhq", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "自然保护区", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateZrbhq(ZrbhqBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateZrbhq", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "自然保护区", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteZrbhq(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteZrbhq", m)) {
            PublicTableDAO.insertSettingLogs("删除", "自然保护区", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}