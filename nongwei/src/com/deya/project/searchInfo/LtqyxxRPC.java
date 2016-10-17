package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LtqyxxRPC {
    public static String getLtqyxxCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return LtqyxxManager.getLtqyxxCount(m);
    }

    public static List<LtqyxxBean> getLtqyxxList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return LtqyxxManager.getLtqyxxList(m);
    }

    public static LtqyxxBean getLtqyxxBean(String id) {
        return LtqyxxManager.getLtqyxxBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LtqyxxManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertLtqyxx(LtqyxxBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LtqyxxManager.insertLtqyxx(hb,stl);
        }else
            return false;
    }

    public static boolean updateLtqyxx(LtqyxxBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LtqyxxManager.updateLtqyxx(hb, stl);
        }else
            return false;
    }

    public static boolean deleteLtqyxx(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return LtqyxxManager.deleteLtqyxx(m, stl);
        }else
            return false;
    }
}