package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlgyDAO {
    private static Map<String, String> m;

    public static String getSlgyCount(Map<String, String> m) {
        return DBManager.getString("getSlgyCount", m);
    }

    public static List<SlgyBean> getSlgyList(Map<String, String> m) {
        SlgyDAO.m = m;
        return DBManager.queryFList("getSlgyList", m);
    }
    
    public static SlgyBean getSlgyBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (SlgyBean) DBManager.queryFObj("getSlgyBean", m);
    }

    public static List<SlgyBean> getAllSlgy() {
        return DBManager.queryFList("getAllSlgy", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeSlgyStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "森林公园",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertSlgy(SlgyBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertSlgy", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "森林公园", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateSlgy(SlgyBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateSlgy", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "森林公园", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteSlgy(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteSlgy", m)) {
            PublicTableDAO.insertSettingLogs("删除", "森林公园", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}