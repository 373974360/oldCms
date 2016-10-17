package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SlgyRPC {
    public static String getSlgyCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return SlgyManager.getSlgyCount(m);
    }

    public static List<SlgyBean> getSlgyList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return SlgyManager.getSlgyList(m);
    }

    public static SlgyBean getSlgyBean(String id) {
        return SlgyManager.getSlgyBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return SlgyManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertSlgy(SlgyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return SlgyManager.insertSlgy(hb, stl);
        }else
            return false;
    }

    public static boolean updateSlgy(SlgyBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return SlgyManager.updateSlgy(hb, stl);
        }else
            return false;
    }

    public static boolean deleteSlgy(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return SlgyManager.deleteSlgy(m, stl);
        }else
            return false;
    }
}