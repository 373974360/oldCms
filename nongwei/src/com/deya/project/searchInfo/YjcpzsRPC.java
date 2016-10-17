package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YjcpzsRPC {
    public static String getYjcpzsCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return YjcpzsManager.getYjcpzsCount(m);
    }

    public static List<YjcpzsBean> getYjcpzsList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return YjcpzsManager.getYjcpzsList(m);
    }

    public static YjcpzsBean getYjcpzsBean(String id) {
        return YjcpzsManager.getYjcpzsBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return YjcpzsManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertYjcpzs(YjcpzsBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return YjcpzsManager.insertYjcpzs(hb,stl);
        }else
            return false;
    }

    public static boolean updateYjcpzs(YjcpzsBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return YjcpzsManager.updateYjcpzs(hb, stl);
        }else
            return false;
    }

    public static boolean deleteYjcpzs(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return YjcpzsManager.deleteYjcpzs(m, stl);
        }else
            return false;
    }
}