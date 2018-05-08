package com.deya.project.dz_pxxx;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PxxxKcManager {
    public static String getPxxxKcCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return PxxxKcDAO.getPxxxKcCount(m);
    }

    public static List<PxxxKcBean> getPxxxKcList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return PxxxKcDAO.getPxxxKcList(m);
    }

    public static List<PxxxKcBean> getAllPxxxKcList() {
        return PxxxKcDAO.getAllPxxxKcList();
    }

    public static List<PxxxKcBean> getAllPxxxKcByPxID(String pxid) {
        return PxxxKcDAO.getAllPxxxKcByPxID(pxid);
    }

    public static PxxxKcBean getPxxxKcBean(String id) {
        return PxxxKcDAO.getPxxxKcBean(id);
    }

    public static boolean insertPxxxKc(PxxxKcBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_pxxx"));
        return PxxxKcDAO.insertPxxxKc(hb, stl);
    }

    public static boolean updatePxxxKc(PxxxKcBean hb, SettingLogsBean stl) {
        return PxxxKcDAO.updatePxxxKc(hb, stl);
    }

    public static boolean deletePxxxKc(Map<String, String> m, SettingLogsBean stl) {
        return PxxxKcDAO.deletePxxxKc(m, stl);
    }
}