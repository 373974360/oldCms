package com.deya.project.dz_siteError;


import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SiteErrorManager {
    public static String getSiteErrorCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return SiteErrorDAO.getSiteErrorCount(m);
    }

    public static List<SiteErrorBean> getSiteErrorList(Map<String, String> m) {

        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return setNames(SiteErrorDAO.getSiteErrorList(m));
    }

    public static List<SiteErrorBean> getAllSiteErrorList() {
        return setNames(SiteErrorDAO.getAllSiteErrorList());
    }

    public static List<SiteErrorBean> getAllSiteErrorBySiteID(Map<String, String> m) {
        return setNames(SiteErrorDAO.getAllSiteErrorBySiteID(m));
    }

    public static SiteErrorBean getSiteErrorBean(String id) {
        return setNames(SiteErrorDAO.getSiteErrorBean(id));
    }

    public static boolean insertSiteError(SiteErrorBean hb, SettingLogsBean stl) {
        hb.setAddTime(DateUtil.getCurrentDateTime());
        hb.setId(PublicTableDAO.getIDByTableName("dz_siteerror"));
        return SiteErrorDAO.insertSiteError(hb, stl);
    }

    public static boolean updateSiteError(SiteErrorBean hb, SettingLogsBean stl) {
        if("2".equals(hb.getStatus()))
        {
            hb.setHandleTime(DateUtil.getCurrentDateTime());
        }
        return SiteErrorDAO.updateSiteError(hb, stl);
    }

    public static boolean publishSiteError(Map<String, String> m)
    {
        String status = m.get("status");
        if("2".equals(status))
        {
            m.put("handleTime",DateUtil.getCurrentDateTime());
        }
        return SiteErrorDAO.publishSiteError(m);
    }

    public static boolean deleteSiteError(Map<String, String> m, SettingLogsBean stl) {
        return SiteErrorDAO.deleteSiteError(m, stl);
    }

    private static List<SiteErrorBean> setNames(List<SiteErrorBean> old)
    {
        List<SiteErrorBean> returnList = new ArrayList<SiteErrorBean>();
        if(old != null && old.size() > 0)
        {
            for(SiteErrorBean seb : old)
            {
                seb.setTypeName(ErrorTypeManager.getErrorTypeBean(seb.getTypeId()+"").getTypeName());
                seb.setSiteName(ErrorSiteManager.getErrorSiteBean(seb.getSiteId() + "").getSiteName());
                returnList.add(seb);
            }
        }
        return returnList;
    }

    private static SiteErrorBean setNames(SiteErrorBean seb)
    {
        seb.setTypeName(ErrorTypeManager.getErrorTypeBean(seb.getTypeId() + "").getTypeName());
        seb.setSiteName(ErrorSiteManager.getErrorSiteBean(seb.getSiteId() + "").getSiteName());
        return seb;
    }
}