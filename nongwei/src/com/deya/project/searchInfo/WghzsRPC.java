package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WghzsRPC {
    public static String getWghzsCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return WghzsManager.getWghzsCount(m);
    }

    public static List<WghzsBean> getWghzsList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return WghzsManager.getWghzsList(m);
    }

    public static WghzsBean getWghzsBean(String id) {
        return WghzsManager.getWghzsBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return WghzsManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertWghzs(WghzsBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return WghzsManager.insertWghzs(hb,stl);
        }else
            return false;
    }

    public static boolean updateWghzs(WghzsBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return WghzsManager.updateWghzs(hb, stl);
        }else
            return false;
    }

    public static boolean deleteWghzs(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return WghzsManager.deleteWghzs(m, stl);
        }else
            return false;
    }
}