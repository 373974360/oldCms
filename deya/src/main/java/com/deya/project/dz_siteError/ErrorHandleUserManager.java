package com.deya.project.dz_siteError;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ErrorHandleUserManager {

    public static List<ErrorHandleUserBean> getAllErrorHandleUserList(Map<String, String> m) {
        return ErrorHandleUserDAO.getAllErrorHandleUserList(m);
    }

    public static ErrorHandleUserBean getErrorHandleUserBean(String id) {
        return ErrorHandleUserDAO.getErrorHandleUserBean(id);
    }

    public static boolean insertErrorHandleUser(ErrorHandleUserBean hb, SettingLogsBean stl) {
        hb.setAddTime(DateUtil.getCurrentDateTime());
        hb.setId(PublicTableDAO.getIDByTableName("dz_erroruser"));
        return ErrorHandleUserDAO.insertErrorHandleUser(hb, stl);
    }

    public static boolean updateErrorHandleUser(ErrorHandleUserBean hb, SettingLogsBean stl) {
        return ErrorHandleUserDAO.updateErrorHandleUser(hb, stl);
    }

    public static boolean deleteErrorHandleUser(Map<String, String> m, SettingLogsBean stl) {
        return ErrorHandleUserDAO.deleteErrorHandleUser(m, stl);
    }
}