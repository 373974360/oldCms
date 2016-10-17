package com.deya.project.dz_siteError;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SiteErrorDAO {
    public static String getSiteErrorCount(Map<String, String> m) {
        return DBManager.getString("getSiteErrorCount", m);
    }

    public static List<SiteErrorBean> getSiteErrorList(Map<String, String> m) {
        return DBManager.queryFList("getSiteErrorList", m);
    }

    public static SiteErrorBean getSiteErrorBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (SiteErrorBean) DBManager.queryFObj("getSiteErrorBean", m);
    }

    public static List<SiteErrorBean> getAllSiteErrorList() {
        return DBManager.queryFList("getAllSiteErrorList", "");
    }

    public static List<SiteErrorBean> getAllSiteErrorBySiteID(Map<String, String> m) {
        return DBManager.queryFList("getAllSiteErrorBySiteID", m);
    }

    public static boolean insertSiteError(SiteErrorBean seb, SettingLogsBean stl) {
        if (DBManager.insert("insertSiteError", seb)) {
            PublicTableDAO.insertSettingLogs("添加", "站点纠错信息", seb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertSiteError(SiteErrorBean seb) {
        return DBManager.insert("insertSiteError", seb);
    }

    public static boolean updateSiteError(SiteErrorBean seb, SettingLogsBean stl) {
        if (DBManager.update("updateSiteError", seb)) {
            PublicTableDAO.insertSettingLogs("修改", "站点纠错信息", seb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean publishSiteError(Map<String, String> m)
    {
        return DBManager.update("publishSiteError", m);
    }

    public static boolean publishSiteError(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("publishSiteError", m)) {
            PublicTableDAO.insertSettingLogs("发布设置", "站点纠错信息", (String) m.get("id"), stl);
            return true;
        }
        return false;
    }

    public static boolean deleteSiteError(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteSiteError", m)) {
            PublicTableDAO.insertSettingLogs("删除", "站点纠错信息", (String) m.get("id"), stl);
            return true;
        }
        return false;
    }
}