package com.deya.project.dz_pxxx;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class PxxxRPC {
    public static String getPxxxCount(Map<String, String> m) {
        return PxxxManager.getPxxxCount(m);
    }

    public static List<PxxxBean> getPxxxList(Map<String, String> m) {
        return PxxxManager.getPxxxList(m);
    }

    public static List<PxxxBean> getAllPxxxList() {
        return PxxxManager.getAllPxxxList();
    }

    public static PxxxBean getPxxxBean(String gq_id) {
        return PxxxManager.getPxxxBean(gq_id);
    }

    public static boolean updatePxxx(PxxxBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PxxxManager.updatePxxx(hb, stl);
        }
        return false;
    }

    public static boolean insertPxxx(PxxxBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PxxxManager.insertPxxx(hb, stl);
        }
        return false;
    }

    public static boolean deletePxxx(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PxxxManager.deletePxxx(m, stl);
        }
        return false;
    }
}