package com.deya.project.dz_pxxx;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PxxxManager {
    public static String getPxxxCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return PxxxDAO.getPxxxCount(m);
    }

    public static List<PxxxBean> getPxxxList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return PxxxDAO.getPxxxList(m);
    }

    public static List<PxxxBean> getAllPxxxList() {
        return PxxxDAO.getAllPxxxList();
    }

    public static PxxxBean getPxxxBean(String id) {
        return PxxxDAO.getPxxxBean(id);
    }

    public static boolean insertPxxx(PxxxBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_pxxx"));
        return PxxxDAO.insertPxxx(hb, stl);
    }

    public static boolean insertSb_Pxxx(PxxxBean hb) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_pxxx"));
        return PxxxDAO.insertPxxx(hb);
    }

    public static boolean updatePxxx(PxxxBean hb, SettingLogsBean stl) {
        return PxxxDAO.updatePxxx(hb, stl);
    }

    public static boolean deletePxxx(Map<String, String> m, SettingLogsBean stl) {
        return PxxxDAO.deletePxxx(m, stl);
    }
}