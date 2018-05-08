package com.deya.project.dz_kfda;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KfdaDAO {
    public static String getKfdaCount(Map<String, String> m) {
        return DBManager.getString("getKfdaCount", m);
    }

    public static List<KfdaBean> getKfdaList(Map<String, String> m) {
        return DBManager.queryFList("getKfdaList", m);
    }

    public static KfdaBean getKfdaBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (KfdaBean) DBManager.queryFObj("getKfdaBean", m);
    }

    public static List<KfdaBean> getAllKfdaList() {
        return DBManager.queryFList("getAllKfdaList", "");
    }

    public static boolean insertKfda(KfdaBean xmk, SettingLogsBean stl) {
        if (DBManager.insert("insertKfda", xmk)) {
            PublicTableDAO.insertSettingLogs("添加", "开放档案", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertKfda(KfdaBean xmk) {
        return DBManager.insert("insertKfda", xmk);
    }

    public static boolean updateKfda(KfdaBean xmk, SettingLogsBean stl) {
        if (DBManager.update("updateKfda", xmk)) {
            PublicTableDAO.insertSettingLogs("修改", "开放档案", xmk.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean publishKfda(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("publishKfda", m)) {
            PublicTableDAO.insertSettingLogs("发布设置", "开放档案", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean deleteKfda(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteKfda", m)) {
            PublicTableDAO.insertSettingLogs("删除", "开放档案", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}