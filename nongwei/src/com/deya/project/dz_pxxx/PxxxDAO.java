package com.deya.project.dz_pxxx;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PxxxDAO {
    public static String getPxxxCount(Map<String, String> m) {
        return DBManager.getString("getPxxxCount", m);
    }

    public static List<PxxxBean> getPxxxList(Map<String, String> m) {
        return DBManager.queryFList("getPxxxList", m);
    }

    public static PxxxBean getPxxxBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (PxxxBean) DBManager.queryFObj("getPxxxBean", m);
    }

    public static List<PxxxBean> getAllPxxxList() {
        return DBManager.queryFList("getAllPxxxList", "");
    }

    public static boolean insertPxxx(PxxxBean xmk, SettingLogsBean stl) {
        if (DBManager.insert("insertPxxx", xmk)) {
            PublicTableDAO.insertSettingLogs("添加", "培训信息", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertPxxx(PxxxBean xmk) {
        return DBManager.insert("insertPxxx", xmk);
    }

    public static boolean updatePxxx(PxxxBean xmk, SettingLogsBean stl) {
        if (DBManager.update("updatePxxx", xmk)) {
            PublicTableDAO.insertSettingLogs("修改", "培训信息", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean publishPxxx(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("publishPxxx", m)) {
            PublicTableDAO.insertSettingLogs("发布设置", "培训信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean deletePxxx(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deletePxxx", m)) {
            PublicTableDAO.insertSettingLogs("删除", "培训信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}