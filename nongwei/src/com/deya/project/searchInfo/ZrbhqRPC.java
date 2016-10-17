package com.deya.project.searchInfo;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZrbhqRPC {
    public static String getZrbhqCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return ZrbhqManager.getZrbhqCount(m);
    }

    public static List<ZrbhqBean> getZrbhqList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return ZrbhqManager.getZrbhqList(m);
    }

    public static ZrbhqBean getZrbhqBean(String id) {
        return ZrbhqManager.getZrbhqBean(id);
    }

    public static boolean changeStatus(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return ZrbhqManager.changeStatus(m,stl);
        }else
            return false;
    }

    public static boolean insertZrbhq(ZrbhqBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return ZrbhqManager.insertZrbhq(hb,stl);
        }else
            return false;
    }

    public static boolean updateZrbhq(ZrbhqBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return ZrbhqManager.updateZrbhq(hb, stl);
        }else
            return false;
    }

    public static boolean deleteZrbhq(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return ZrbhqManager.deleteZrbhq(m, stl);
        }else
            return false;
    }
}