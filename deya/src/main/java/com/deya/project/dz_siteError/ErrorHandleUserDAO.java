package com.deya.project.dz_siteError;


import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ErrorHandleUserDAO {

    public static ErrorHandleUserBean getErrorHandleUserBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (ErrorHandleUserBean) DBManager.queryFObj("getErrorHandleUserBean", m);
    }

    public static List<ErrorHandleUserBean> getAllErrorHandleUserList(Map<String, String> m) {
        return DBManager.queryFList("getAllErrorHandleUserList", m);
    }

    public static boolean insertErrorHandleUser(ErrorHandleUserBean ehu, SettingLogsBean stl) {
        if (DBManager.insert("insertErrorHandleUser", ehu)) {
            PublicTableDAO.insertSettingLogs("添加", "纠错处理用户", ehu.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean updateErrorHandleUser(ErrorHandleUserBean ehu, SettingLogsBean stl) {
        if (DBManager.update("updateErrorHandleUser", ehu)) {
            PublicTableDAO.insertSettingLogs("修改", "纠错处理用户", ehu.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteErrorHandleUser(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteErrorHandleUser", m)) {
            PublicTableDAO.insertSettingLogs("删除", "纠错处理用户", (String) m.get("id"), stl);
            return true;
        }
        return false;
    }
}