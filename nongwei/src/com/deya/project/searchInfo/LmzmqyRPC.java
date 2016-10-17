package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LmzmqyRPC {
    public static String getLmzmqyCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return LmzmqyManager.getLmzmqyCount(m);
    }

    public static List<LmzmqyBean> getLmzmqyList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return LmzmqyManager.getLmzmqyList(m);
    }

    public static LmzmqyBean getLmzmqyBean(String id) {
        return LmzmqyManager.getLmzmqyBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LmzmqyManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertLmzmqy(LmzmqyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LmzmqyManager.insertLmzmqy(hb,stl);
        }else
            return false;
    }

    public static boolean updateLmzmqy(LmzmqyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LmzmqyManager.updateLmzmqy(hb, stl);
        }else
            return false;
    }

    public static boolean deleteLmzmqy(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LmzmqyManager.deleteLmzmqy(m, stl);
        }else
            return false;
    }
}