package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyjyqyRPC {
    public static String getSyjyqyCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return SyjyqyManager.getSyjyqyCount(m);
    }

    public static List<SyjyqyBean> getSyjyqyList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return SyjyqyManager.getSyjyqyList(m);
    }

    public static SyjyqyBean getSyjyqyBean(String id) {
        return SyjyqyManager.getSyjyqyBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return SyjyqyManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertSyjyqy(SyjyqyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return SyjyqyManager.insertSyjyqy(hb,stl);
        }else
            return false;
    }

    public static boolean updateSyjyqy(SyjyqyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return SyjyqyManager.updateSyjyqy(hb, stl);
        }else
            return false;
    }

    public static boolean deleteSyjyqy(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return SyjyqyManager.deleteSyjyqy(m, stl);
        }else
            return false;
    }
}