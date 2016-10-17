package com.deya.project.dz_aqwzxx;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WzxxManager {
    public static String getWzxxCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return WzxxDAO.getWzxxCount(m);
    }

    public static List<WzxxBean> getWzxxList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return WzxxDAO.getWzxxList(m);
    }

    public static List<WzxxBean> getAllWzxxList() {
        return WzxxDAO.getAllWzxxList();
    }

    public static WzxxBean getWzxxBean(String id, boolean is_browser) {
        return WzxxDAO.getWzxxBean(id, is_browser);
    }

    public static boolean insertWzxx(WzxxBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_aqwzxx"));
        return WzxxDAO.insertWzxx(hb, stl);
    }

    public static boolean updateWzxx(WzxxBean hb, SettingLogsBean stl) {
        return WzxxDAO.updateWzxx(hb, stl);
    }

    public static boolean deleteWzxx(Map<String, String> m, SettingLogsBean stl) {
        return WzxxDAO.deleteWzxx(m, stl);

    }
}