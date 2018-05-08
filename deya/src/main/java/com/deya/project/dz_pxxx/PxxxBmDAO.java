package com.deya.project.dz_pxxx;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PxxxBmDAO {
    public static String getPxxxBmCount(Map<String, String> m) {
        return DBManager.getString("getPxxxBmCount", m);
    }

    public static List<PxxxBmBean> getPxxxBmList(Map<String, String> m) {
        return DBManager.queryFList("getPxxxBmList", m);
    }

    public static PxxxBmBean getPxxxBmBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (PxxxBmBean) DBManager.queryFObj("getPxxxBmBean", m);
    }

    public static List<PxxxBmBean> getAllPxxxBmList() {
        return DBManager.queryFList("getAllPxxxBmList", "");
    }

    public static List<PxxxBmBean> getAllPxxxBmByPxID(Map<String, String> m) {
        return DBManager.queryFList("getAllPxxxBmByPxID", m);
    }

    public static boolean insertPxxxBm(PxxxBmBean xmk, SettingLogsBean stl) {
        if (DBManager.insert("insertPxxxBm", xmk)) {
            PublicTableDAO.insertSettingLogs("添加", "培训报名信息", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertPxxxBm(PxxxBmBean xmk) {
        return DBManager.insert("insertPxxxBm", xmk);
    }

    public static boolean updatePxxxBm(PxxxBmBean xmk, SettingLogsBean stl) {
        if (DBManager.update("updatePxxx", xmk)) {
            PublicTableDAO.insertSettingLogs("修改", "培训报名信息", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean publishPxxxBm(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("publishPxxxBm", m)) {
            PublicTableDAO.insertSettingLogs("发布设置", "培训报名信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean deletePxxxBm(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deletePxxxBm", m)) {
            PublicTableDAO.insertSettingLogs("删除", "培训报名信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}