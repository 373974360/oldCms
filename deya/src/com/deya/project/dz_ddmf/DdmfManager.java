package com.deya.project.dz_ddmf;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DdmfManager {
    public static String getDdmfCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return DdmfDAO.getDdmfCount(m);
    }

    public static List<DdmfBean> getDdmfList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return DdmfDAO.getDdmfList(m);
    }

    public static List<DdmfBean> getAllDdmfList() {
        return DdmfDAO.getAllDdmfList();
    }

    public static DdmfBean getDdmfBean(String id, boolean is_browser) {
        return DdmfDAO.getDdmfBean(id, is_browser);
    }

    public static boolean insertDdmf(DdmfBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_ddmf"));
        return DdmfDAO.insertDdmf(hb, stl);
    }

    public static boolean updateDdmf(DdmfBean hb, SettingLogsBean stl) {
        return DdmfDAO.updateDdmf(hb, stl);
    }

    public static boolean deleteDdmf(Map<String, String> m, SettingLogsBean stl) {
        return DdmfDAO.deleteDdmf(m, stl);

    }
}