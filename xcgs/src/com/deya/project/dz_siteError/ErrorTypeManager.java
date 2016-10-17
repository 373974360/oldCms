package com.deya.project.dz_siteError;


import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.List;
import java.util.Map;

public class ErrorTypeManager {

    public static List<ErrorTypeBean> getAllErrorTypeList(Map<String, String> m) {
        return ErrorTypeDAO.getAllErrorTypeList(m);
    }

    public static ErrorTypeBean getErrorTypeBean(String id) {
        return ErrorTypeDAO.getErrorTypeBean(id);
    }

    public static boolean insertErrorType(ErrorTypeBean hb, SettingLogsBean stl) {
        hb.setAddTime(DateUtil.getCurrentDateTime());
        hb.setId(PublicTableDAO.getIDByTableName("dz_errortype"));
        return ErrorTypeDAO.insertErrorType(hb, stl);
    }

    public static boolean updateErrorType(ErrorTypeBean hb, SettingLogsBean stl) {
        return ErrorTypeDAO.updateErrorType(hb, stl);
    }

    public static boolean saveErrorTypeSort(String ids, SettingLogsBean stl)
    {
        boolean flg = true;
        String arrIDS[] = ids.split(",");
        for(int i=0; i<arrIDS.length; i++)
        {
            ErrorTypeBean etb = getErrorTypeBean(arrIDS[i]);
            etb.setSort(i);
            if(!ErrorTypeDAO.updateErrorType(etb, stl))
            {
                flg = false;
            }
        }
        return flg;
    }

    public static boolean deleteErrorType(Map<String, String> m, SettingLogsBean stl) {
        return ErrorTypeDAO.deleteErrorType(m, stl);
    }
}