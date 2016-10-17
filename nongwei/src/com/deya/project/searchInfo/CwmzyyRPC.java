package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CwmzyyRPC {
    public static String getCwmzyyCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return CwmzyyManager.getCwmzyyCount(m);
    }

    public static List<CwmzyyBean> getCwmzyyList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return CwmzyyManager.getCwmzyyList(m);
    }

    public static CwmzyyBean getCwmzyyBean(String id) {
        return CwmzyyManager.getCwmzyyBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return CwmzyyManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertCwmzyy(CwmzyyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return CwmzyyManager.insertCwmzyy(hb,stl);
        }else
            return false;
    }

    public static boolean updateCwmzyy(CwmzyyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return CwmzyyManager.updateCwmzyy(hb, stl);
        }else
            return false;
    }

    public static boolean deleteCwmzyy(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return CwmzyyManager.deleteCwmzyy(m, stl);
        }else
            return false;
    }
}