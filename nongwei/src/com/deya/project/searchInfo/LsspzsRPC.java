package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LsspzsRPC {
    public static String getLsspzsCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return LsspzsManager.getLsspzsCount(m);
    }

    public static List<LsspzsBean> getLsspzsList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return LsspzsManager.getLsspzsList(m);
    }

    public static LsspzsBean getLsspzsBean(String id) {
        return LsspzsManager.getLsspzsBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LsspzsManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertLsspzs(LsspzsBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LsspzsManager.insertLsspzs(hb,stl);
        }else
            return false;
    }

    public static boolean updateLsspzs(LsspzsBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LsspzsManager.updateLsspzs(hb, stl);
        }else
            return false;
    }

    public static boolean deleteLsspzs(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LsspzsManager.deleteLsspzs(m, stl);
        }else
            return false;
    }
}