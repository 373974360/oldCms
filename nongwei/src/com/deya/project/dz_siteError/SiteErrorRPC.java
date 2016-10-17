package com.deya.project.dz_siteError;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class SiteErrorRPC {
    public static String getSiteErrorCount(Map<String, String> m) {
        return SiteErrorManager.getSiteErrorCount(m);
    }

    public static List<SiteErrorBean> getSiteErrorList(Map<String, String> m) {
        return SiteErrorManager.getSiteErrorList(m);
    }

    public static List<SiteErrorBean> getAllSiteErrorList() {
        return SiteErrorManager.getAllSiteErrorList();
    }

    public static List<SiteErrorBean> getAllSiteErrorBySiteID(Map<String, String> m) {
        return SiteErrorManager.getAllSiteErrorBySiteID(m);
    }

    public static SiteErrorBean getSiteErrorBean(String gq_id) {
        return SiteErrorManager.getSiteErrorBean(gq_id);
    }

    public static boolean updateSiteError(SiteErrorBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return SiteErrorManager.updateSiteError(hb, stl);
        }
        return false;
    }

    public static boolean publishSiteError(Map<String, String> m)
    {
        return SiteErrorManager.publishSiteError(m);
    }

    public static boolean insertSiteError(SiteErrorBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return SiteErrorManager.insertSiteError(hb, stl);
        }
        return false;
    }

    public static boolean deleteSiteError(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return SiteErrorManager.deleteSiteError(m, stl);
        }
        return false;
    }

    public static List<ErrorHandleUserBean> getAllErrorHandleUserList(Map<String, String> m) {
        return ErrorHandleUserManager.getAllErrorHandleUserList(m);
    }

    public static ErrorHandleUserBean getErrorHandleUserBean(String id) {
        return ErrorHandleUserManager.getErrorHandleUserBean(id);
    }

    public static boolean insertErrorHandleUser(ErrorHandleUserBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorHandleUserManager.insertErrorHandleUser(hb, stl);
        }
        return false;
    }

    public static boolean updateErrorHandleUser(ErrorHandleUserBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorHandleUserManager.updateErrorHandleUser(hb, stl);
        }
        return false;
    }

    public static boolean deleteErrorHandleUser(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorHandleUserManager.deleteErrorHandleUser(m, stl);
        }
        return false;
    }

    public static List<ErrorSiteBean> getAllErrorSiteList(Map<String, String> m) {
        return ErrorSiteManager.getAllErrorSiteList(m);
    }

    public static ErrorSiteBean getErrorSiteBean(String id) {

        return ErrorSiteManager.getErrorSiteBean(id);
    }

    public static boolean insertErrorSite(ErrorSiteBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorSiteManager.insertErrorSite(hb, stl);
        }
        return false;
    }

    public static boolean updateErrorSite(ErrorSiteBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorSiteManager.updateErrorSite(hb, stl);
        }
        return false;
    }

    public static boolean deleteErrorSite(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorSiteManager.deleteErrorSite(m, stl);
        }
        return false;
    }

    public static List<ErrorTypeBean> getAllErrorTypeList(Map<String, String> m) {
        return ErrorTypeManager.getAllErrorTypeList(m);
    }

    public static ErrorTypeBean getErrorTypeBean(String id) {
        return ErrorTypeManager.getErrorTypeBean(id);
    }

    public static boolean insertErrorType(ErrorTypeBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorTypeManager.insertErrorType(hb, stl);
        }
        return false;
    }

    public static boolean updateErrorType(ErrorTypeBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorTypeManager.updateErrorType(hb, stl);
        }
        return false;
    }

    public static boolean saveErrorTypeSort(String ids, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorTypeManager.saveErrorTypeSort(ids, stl);
        }
        return false;
    }

    public static boolean deleteErrorType(Map<String, String> m, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ErrorTypeManager.deleteErrorType(m, stl);
        }
        return false;
    }

}