package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CwmzyyDAO {
    private static Map<String, String> m;

    public static String getCwmzyyCount(Map<String, String> m) {
        return DBManager.getString("getCwmzyyCount", m);
    }

    public static List<CwmzyyBean> getCwmzyyList(Map<String, String> m) {
        CwmzyyDAO.m = m;
        return DBManager.queryFList("getCwmzyyList", m);
    }
    
    public static CwmzyyBean getCwmzyyBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (CwmzyyBean) DBManager.queryFObj("getCwmzyyBean", m);
    }

    public static List<CwmzyyBean> getAllCwmzyy() {
        return DBManager.queryFList("getAllCwmzyy", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeCwmzyyStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "宠物门诊医院",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertCwmzyy(CwmzyyBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertCwmzyy", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "宠物门诊医院", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateCwmzyy(CwmzyyBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateCwmzyy", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "宠物门诊医院", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteCwmzyy(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteCwmzyy", m)) {
            PublicTableDAO.insertSettingLogs("删除", "宠物门诊医院", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}