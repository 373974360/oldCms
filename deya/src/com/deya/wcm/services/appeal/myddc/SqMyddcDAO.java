package com.deya.wcm.services.appeal.myddc;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqMyddcDAO {
    public static String getSqMyddcCount(Map<String, String> m) {
        return DBManager.getString("getSqMyddcCount", m);
    }

    public static List<SqMyddcBean> getSqMyddcList(Map<String, String> m) {
        return DBManager.queryFList("getSqMyddcList", m);
    }

    public static SqMyddcBean getSqMyddcBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (SqMyddcBean) DBManager.queryFObj("getSqMyddcBean", m);
    }

    public static List<SqMyddcBean> getAllSqMyddcList() {
        return DBManager.queryFList("getAllSqMyddcList", "");
    }

    public static boolean insertSqMyddc(SqMyddcBean xmk, SettingLogsBean stl) {
        if (DBManager.insert("insertSqMyddc", xmk)) {
            PublicTableDAO.insertSettingLogs("添加", "诉求满意度调查", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertSqMyddc(SqMyddcBean xmk) {
        return DBManager.insert("insertSqMyddc", xmk);
    }

    public static boolean updateSqMyddc(SqMyddcBean xmk, SettingLogsBean stl) {
        if (DBManager.update("updateSqMyddc", xmk)) {
            PublicTableDAO.insertSettingLogs("修改", "诉求满意度调查", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteSqMyddc(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteSqMyddc", m)) {
            PublicTableDAO.insertSettingLogs("删除", "诉求满意度调查", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}