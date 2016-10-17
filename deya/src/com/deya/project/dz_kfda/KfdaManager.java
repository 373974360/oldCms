package com.deya.project.dz_kfda;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KfdaManager {
    public static String getKfdaCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return KfdaDAO.getKfdaCount(m);
    }

    public static List<KfdaBean> getKfdaList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return KfdaDAO.getKfdaList(m);
    }

    public static List<KfdaBean> getAllKfdaList() {
        return KfdaDAO.getAllKfdaList();
    }

    public static KfdaBean getKfdaBean(String id) {
        return KfdaDAO.getKfdaBean(id);
    }

    public static boolean insertKfda(KfdaBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_kfda_sj"));
        return KfdaDAO.insertKfda(hb, stl);
    }

    public static boolean insertSb_Kfda(KfdaBean hb) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_kfda_sj"));
        hb.setInputTime(DateUtil.getCurrentDateTime());
        return KfdaDAO.insertKfda(hb);
    }

    public static boolean updateKfda(KfdaBean hb, SettingLogsBean stl) {
        return KfdaDAO.updateKfda(hb, stl);
    }

    public static boolean deleteKfda(Map<String, String> m, SettingLogsBean stl) {
        return KfdaDAO.deleteKfda(m, stl);
    }
}