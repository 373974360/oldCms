package com.deya.project.dz_aqwzxx;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WzxxDAO {
    public static String getWzxxCount(Map<String, String> m) {
        return DBManager.getString("getWzxxCount", m);
    }

    public static List<WzxxBean> getWzxxList(Map<String, String> m) {
        return DBManager.queryFList("getWzxxList", m);
    }

    public static WzxxBean getWzxxBean(String id, boolean is_browser) {
        Map m = new HashMap();
        m.put("id", id);
        return (WzxxBean) DBManager.queryFObj("getWzxxBean", m);
    }

    public static List<WzxxBean> getAllWzxxList() {
        return DBManager.queryFList("getAllWzxxList", "");
    }

    public static boolean insertWzxx(WzxxBean Wzxx, SettingLogsBean stl) {
        if (DBManager.insert("insertWzxx", Wzxx)) {
            PublicTableDAO.insertSettingLogs("添加", "网站信息", Wzxx.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertWzxx(WzxxBean Wzxx) {
        return DBManager.insert("insertWzxx", Wzxx);
    }

    public static boolean updateWzxx(WzxxBean Wzxx, SettingLogsBean stl) {
        if (DBManager.update("updateWzxx", Wzxx)) {
            PublicTableDAO.insertSettingLogs("修改", "网站信息", Wzxx.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteWzxx(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteWzxx", m)) {
            PublicTableDAO.insertSettingLogs("删除", "网站信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}