package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JyyDAO {
    private static Map<String, String> m;

    public static String getJyyCount(Map<String, String> m) {
        return DBManager.getString("getJyyCount", m);
    }

    public static List<JyyBean> getJyyList(Map<String, String> m) {
        JyyDAO.m = m;
        return DBManager.queryFList("getJyyList", m);
    }
    
    public static JyyBean getJyyBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (JyyBean) DBManager.queryFObj("getJyyBean", m);
    }

    public static List<JyyBean> getAllJyy() {
        return DBManager.queryFList("getAllJyy", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeJyyStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "检验员信息",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertJyy(JyyBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertJyy", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "检验员信息", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateJyy(JyyBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateJyy", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "检验员信息", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteJyy(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteJyy", m)) {
            PublicTableDAO.insertSettingLogs("删除", "检验员信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}