package com.deya.project.dz_siteError;


import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ErrorTypeDAO {

    public static ErrorTypeBean getErrorTypeBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (ErrorTypeBean) DBManager.queryFObj("getErrorTypeBean", m);
    }

    public static List<ErrorTypeBean> getAllErrorTypeList(Map<String, String> m) {
        return DBManager.queryFList("getAllErrorTypeList", m);
    }

    public static boolean insertErrorType(ErrorTypeBean etb, SettingLogsBean stl) {
        if (DBManager.insert("insertErrorType", etb)) {
            PublicTableDAO.insertSettingLogs("添加", "纠错分类信息", etb.getId() + "", stl);
            return true;
        }
        return false;
    }


    public static boolean updateErrorType(ErrorTypeBean etb, SettingLogsBean stl) {
        if (DBManager.update("updateErrorType", etb)) {
            PublicTableDAO.insertSettingLogs("修改", "纠错分类信息", etb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteErrorType(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteErrorType", m)) {
            PublicTableDAO.insertSettingLogs("删除", "纠错分类信息", (String) m.get("id"), stl);
            return true;
        }
        return false;
    }
}