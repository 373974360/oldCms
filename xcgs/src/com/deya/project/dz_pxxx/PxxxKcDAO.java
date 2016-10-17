package com.deya.project.dz_pxxx;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PxxxKcDAO {
    public static String getPxxxKcCount(Map<String, String> m) {
        return DBManager.getString("getPxxxKcCount", m);
    }

    public static List<PxxxKcBean> getPxxxKcList(Map<String, String> m) {
        return DBManager.queryFList("getPxxxKcList", m);
    }

    public static PxxxKcBean getPxxxKcBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (PxxxKcBean) DBManager.queryFObj("getPxxxKcBean", m);
    }

    public static List<PxxxKcBean> getAllPxxxKcList() {
        return DBManager.queryFList("getAllPxxxKcList", "");
    }

    public static List<PxxxKcBean> getAllPxxxKcByPxID(String pxid) {
        Map m = new HashMap();
        m.put("pxid", pxid);
        return DBManager.queryFList("getAllPxxxKcByPxID", m);
    }

    public static boolean insertPxxxKc(PxxxKcBean xmk, SettingLogsBean stl) {
        if (DBManager.insert("insertPxxxKc", xmk)) {
            PublicTableDAO.insertSettingLogs("添加", "培训课程信息", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertPxxxKc(PxxxKcBean xmk) {
        return DBManager.insert("insertPxxxKc", xmk);
    }

    public static boolean updatePxxxKc(PxxxKcBean xmk, SettingLogsBean stl) {
        if (DBManager.update("updatePxxxKc", xmk)) {
            PublicTableDAO.insertSettingLogs("修改", "培训课程信息", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean publishPxxxKc(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("publishPxxxKc", m)) {
            PublicTableDAO.insertSettingLogs("发布设置", "培训课程信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean deletePxxxKc(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deletePxxxKc", m)) {
            PublicTableDAO.insertSettingLogs("删除", "培训课程信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}