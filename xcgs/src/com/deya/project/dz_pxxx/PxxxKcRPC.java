package com.deya.project.dz_pxxx;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class PxxxKcRPC {
    public static String getPxxxKcCount(Map<String, String> m) {
        return PxxxKcManager.getPxxxKcCount(m);
    }

    public static List<PxxxKcBean> getPxxxKcList(Map<String, String> m) {
        return PxxxKcManager.getPxxxKcList(m);
    }

    public static List<PxxxKcBean> getAllPxxxKcList() {
        return PxxxKcManager.getAllPxxxKcList();
    }

    public static List<PxxxKcBean> getAllPxxxKcByPxID(String pxid) {
        return PxxxKcManager.getAllPxxxKcByPxID(pxid);
    }


    public static PxxxKcBean getPxxxKcBean(String gq_id) {
        return PxxxKcManager.getPxxxKcBean(gq_id);
    }

    public static boolean updatePxxxKc(PxxxKcBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PxxxKcManager.updatePxxxKc(hb, stl);
        }
        return false;
    }

    public static boolean insertPxxxKc(PxxxKcBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PxxxKcManager.insertPxxxKc(hb, stl);
        }
        return false;
    }

    public static boolean deletePxxxKc(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PxxxKcManager.deletePxxxKc(m, stl);
        }
        return false;
    }
}