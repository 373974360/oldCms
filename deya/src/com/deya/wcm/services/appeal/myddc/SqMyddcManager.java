package com.deya.wcm.services.appeal.myddc;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqMyddcManager {
    public static String getSqMyddcCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return SqMyddcDAO.getSqMyddcCount(m);
    }

    public static List<SqMyddcBean> getSqMyddcList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return SqMyddcDAO.getSqMyddcList(m);
    }

    public static List<SqMyddcBean> getAllSqMyddcList() {
        return SqMyddcDAO.getAllSqMyddcList();
    }

    public static SqMyddcBean getSqMyddcBean(String id) {
        return SqMyddcDAO.getSqMyddcBean(id);
    }

    public static boolean insertSqMyddc(SqMyddcBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("cp_sq_myddc"));
        return SqMyddcDAO.insertSqMyddc(hb, stl);
    }

    public static boolean updateSqMyddc(SqMyddcBean hb, SettingLogsBean stl) {
        return SqMyddcDAO.updateSqMyddc(hb, stl);
    }

    public static boolean deleteSqMyddc(Map<String, String> m, SettingLogsBean stl) {
        return SqMyddcDAO.deleteSqMyddc(m, stl);
    }
}