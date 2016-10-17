package com.deya.project.dz_ddmf;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class DdmfRPC {
    public static String getDdmfCount(Map<String, String> m) {
        return DdmfManager.getDdmfCount(m);
    }

    public static List<DdmfBean> getDdmfList(Map<String, String> m) {
        return DdmfManager.getDdmfList(m);
    }

    public static List<DdmfBean> getAllDdmfList() {
        return DdmfManager.getAllDdmfList();
    }

    public static DdmfBean getDdmfBean(String gq_id, boolean is_browser) {
        return DdmfManager.getDdmfBean(gq_id, is_browser);
    }

    public static boolean updateDdmf(DdmfBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return DdmfManager.updateDdmf(hb, stl);
        }
        return false;
    }

    public static boolean insertDdmf(DdmfBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return DdmfManager.insertDdmf(hb, stl);
        }
        return false;
    }

    public static boolean deleteDdmf(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return DdmfManager.deleteDdmf(m, stl);
        }
        return false;
    }
}