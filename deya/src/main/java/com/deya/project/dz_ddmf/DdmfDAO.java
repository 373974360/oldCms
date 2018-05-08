package com.deya.project.dz_ddmf;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DdmfDAO {
    public static String getDdmfCount(Map<String, String> m) {
        return DBManager.getString("getDdmfCount", m);
    }

    public static List<DdmfBean> getDdmfList(Map<String, String> m) {
        return DBManager.queryFList("getDdmfList", m);
    }

    public static DdmfBean getDdmfBean(String id, boolean is_browser) {
        Map m = new HashMap();
        m.put("id", id);
        return (DdmfBean) DBManager.queryFObj("getDdmfBean", m);
    }

    public static List<DdmfBean> getAllDdmfList() {
        return DBManager.queryFList("getAllDdmfList", "");
    }

    public static boolean insertDdmf(DdmfBean Ddmf, SettingLogsBean stl) {
        if (DBManager.insert("insertDdmf", Ddmf)) {
            PublicTableDAO.insertSettingLogs("添加", "道德模范", Ddmf.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertDdmf(DdmfBean Ddmf) {
        return DBManager.insert("insertDdmf", Ddmf);
    }

    public static boolean updateDdmf(DdmfBean Ddmf, SettingLogsBean stl) {
        if (DBManager.update("updateDdmf", Ddmf)) {
            PublicTableDAO.insertSettingLogs("修改", "道德模范", Ddmf.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteDdmf(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteDdmf", m)) {
            PublicTableDAO.insertSettingLogs("删除", "道德模范", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}