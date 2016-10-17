package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NzwzzRPC {
    public static String getNzwzzCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return NzwzzManager.getNzwzzCount(m);
    }

    public static List<NzwzzBean> getNzwzzList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return NzwzzManager.getNzwzzList(m);
    }

    public static NzwzzBean getNzwzzBean(String id) {
        return NzwzzManager.getNzwzzBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return NzwzzManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertNzwzz(NzwzzBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return NzwzzManager.insertNzwzz(hb,stl);
        }else
            return false;
    }

    public static boolean updateNzwzz(NzwzzBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return NzwzzManager.updateNzwzz(hb, stl);
        }else
            return false;
    }

    public static boolean deleteNzwzz(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return NzwzzManager.deleteNzwzz(m, stl);
        }else
            return false;
    }
}