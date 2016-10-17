package com.deya.project.searchInfo;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YjcpzsDAO {
    private static Map<String, String> m;

    public static String getYjcpzsCount(Map<String, String> m) {
        return DBManager.getString("getYjcpzsCount", m);
    }

    public static List<YjcpzsBean> getYjcpzsList(Map<String, String> m) {
        YjcpzsDAO.m = m;
        return DBManager.queryFList("getYjcpzsList", m);
    }
    
    public static YjcpzsBean getYjcpzsBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (YjcpzsBean) DBManager.queryFObj("getYjcpzsBean", m);
    }

    public static List<YjcpzsBean> getAllYjcpzs() {
        return DBManager.queryFList("getAllYjcpzs", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeYjcpzsStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "有机产品认证证书",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertYjcpzs(YjcpzsBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertYjcpzs", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "有机产品认证证书", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateYjcpzs(YjcpzsBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateYjcpzs", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "有机产品认证证书", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteYjcpzs(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteYjcpzs", m)) {
            PublicTableDAO.insertSettingLogs("删除", "有机产品认证证书", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}