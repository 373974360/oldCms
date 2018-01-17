package com.deya.project.dz_xxbs;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class XxbsRPC {

    public static List<XxbsBean> getXxbsInfoList(Map<String, String> map) {

        return XxbsManager.getXxbsInfoList(map);
    }

    public static int getXxbsInfoCount(Map<String, String> map) {
        return Integer.parseInt(XxbsManager.getXxbsInfoCount(map));
    }

    public static boolean insertXxbs(XxbsBean xxbs, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return XxbsManager.insertXxbs(xxbs, stl);
        }
        return false;
    }
}
