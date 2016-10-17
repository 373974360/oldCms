package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NzwzzDAO {
    private static Map<String, String> m;

    public static String getNzwzzCount(Map<String, String> m) {
        return DBManager.getString("getNzwzzCount", m);
    }

    public static List<NzwzzBean> getNzwzzList(Map<String, String> m) {
        NzwzzDAO.m = m;
        return DBManager.queryFList("getNzwzzList", m);
    }
    
    public static NzwzzBean getNzwzzBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (NzwzzBean) DBManager.queryFObj("getNzwzzBean", m);
    }

    public static List<NzwzzBean> getAllNzwzz() {
        return DBManager.queryFList("getAllNzwzz", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeNzwzzStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "农作物种子生产经营企业",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertNzwzz(NzwzzBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertNzwzz", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "农作物种子生产经营企业", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateNzwzz(NzwzzBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateNzwzz", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "农作物种子生产经营企业", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteNzwzz(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteNzwzz", m)) {
            PublicTableDAO.insertSettingLogs("删除", "农作物种子生产经营企业", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}