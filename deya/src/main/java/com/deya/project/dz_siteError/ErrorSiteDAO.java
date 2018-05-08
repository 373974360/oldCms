package com.deya.project.dz_siteError;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorSiteDAO {

    public static ErrorSiteBean getErrorSiteBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (ErrorSiteBean) DBManager.queryFObj("getErrorSiteBean", m);
    }

    public static List<ErrorSiteBean> getAllErrorSiteList(Map<String, String> m) {
        return DBManager.queryFList("getAllErrorSiteList", m);
    }

    public static boolean insertErrorSite(ErrorSiteBean esb, SettingLogsBean stl) {
        if (DBManager.insert("insertErrorSite", esb)) {
            PublicTableDAO.insertSettingLogs("添加", "纠错站点信息", esb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertErrorSite(ErrorSiteBean esb) {
        return DBManager.insert("insertErrorSite", esb);
    }

    public static boolean updateErrorSite(ErrorSiteBean esb, SettingLogsBean stl) {
        if (DBManager.update("updateErrorSite", esb)) {
            PublicTableDAO.insertSettingLogs("修改", "纠错站点信息", esb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteErrorSite(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteErrorSite", m)) {
            PublicTableDAO.insertSettingLogs("删除", "纠错站点信息", (String) m.get("id"), stl);
            return true;
        }
        return false;
    }
}
