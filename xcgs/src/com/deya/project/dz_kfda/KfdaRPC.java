package com.deya.project.dz_kfda;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class KfdaRPC {
    public static String getKfdaCount(Map<String, String> m) {
        return KfdaManager.getKfdaCount(m);
    }

    public static List<KfdaBean> getKfdaList(Map<String, String> m) {
        return KfdaManager.getKfdaList(m);
    }

    public static List<KfdaBean> getAllKfdaList() {
        return KfdaManager.getAllKfdaList();
    }

    public static KfdaBean getKfdaBean(String gq_id) {
        return KfdaManager.getKfdaBean(gq_id);
    }

    public static boolean updateKfda(KfdaBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return KfdaManager.updateKfda(hb, stl);
        }
        return false;
    }

    public static boolean insertKfda(KfdaBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return KfdaManager.insertKfda(hb, stl);
        }
        return false;
    }

    public static boolean deleteKfda(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return KfdaManager.deleteKfda(m, stl);
        }
        return false;
    }
}