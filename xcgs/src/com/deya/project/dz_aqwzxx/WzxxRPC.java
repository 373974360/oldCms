package com.deya.project.dz_aqwzxx;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class WzxxRPC {
    public static String getWzxxCount(Map<String, String> m) {
        return WzxxManager.getWzxxCount(m);
    }

    public static List<WzxxBean> getWzxxList(Map<String, String> m) {
        return WzxxManager.getWzxxList(m);
    }

    public static List<WzxxBean> getAllWzxxList() {
        return WzxxManager.getAllWzxxList();
    }

    public static WzxxBean getWzxxBean(String gq_id, boolean is_browser) {
        return WzxxManager.getWzxxBean(gq_id, is_browser);
    }

    public static boolean updateWzxx(WzxxBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return WzxxManager.updateWzxx(hb, stl);
        }
        return false;
    }

    public static boolean insertWzxx(WzxxBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return WzxxManager.insertWzxx(hb, stl);
        }
        return false;
    }

    public static boolean deleteWzxx(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return WzxxManager.deleteWzxx(m, stl);
        }
        return false;
    }
}