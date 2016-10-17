package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JyyRPC {
    public static String getJyyCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return JyyManager.getJyyCount(m);
    }

    public static List<JyyBean> getJyyList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return JyyManager.getJyyList(m);
    }

    public static JyyBean getJyyBean(String id) {
        return JyyManager.getJyyBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return JyyManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertJyy(JyyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return JyyManager.insertJyy(hb,stl);
        }else
            return false;
    }

    public static boolean updateJyy(JyyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return JyyManager.updateJyy(hb, stl);
        }else
            return false;
    }

    public static boolean deleteJyy(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return JyyManager.deleteJyy(m, stl);
        }else
            return false;
    }
}