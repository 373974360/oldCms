package com.deya.project.dz_pxxx;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PxxxBmManager {
    public static String getPxxxBmCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return PxxxBmDAO.getPxxxBmCount(m);
    }

    public static List<PxxxBmBean> getPxxxBmList(Map<String, String> m) {

        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return PxxxBmDAO.getPxxxBmList(m);
    }

    public static List<PxxxBmBean> getAllPxxxBmList() {
        return PxxxBmDAO.getAllPxxxBmList();
    }

    public static List<PxxxBmBean> getAllPxxxBmByPxID(Map<String, String> m) {
        return PxxxBmDAO.getAllPxxxBmByPxID(m);
    }

    public static PxxxBmBean getPxxxBmBean(String id) {
        return PxxxBmDAO.getPxxxBmBean(id);
    }

    public static boolean insertPxxxBm(PxxxBmBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_pxxx"));
        return PxxxBmDAO.insertPxxxBm(hb, stl);
    }

    public static boolean updatePxxxBm(PxxxBmBean hb, SettingLogsBean stl) {
        return PxxxBmDAO.updatePxxxBm(hb, stl);
    }

    public static boolean deletePxxxBm(Map<String, String> m, SettingLogsBean stl) {
        return PxxxBmDAO.deletePxxxBm(m, stl);
    }
}