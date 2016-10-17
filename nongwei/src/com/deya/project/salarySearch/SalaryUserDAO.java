package com.deya.project.salarySearch;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaryUserDAO {
    public static String getSalaryUserCount(Map<String, String> m) {
        return DBManager.getString("getSalaryUserCount", m);
    }

    public static List<SalaryUserBean> getSalaryUserList(Map<String, String> m) {
        return DBManager.queryFList("getSalaryUserList", m);
    }

    public static SalaryUserBean getSalaryUserBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (SalaryUserBean) DBManager.queryFObj("getSalaryUserBean", m);
    }

    public static List<SalaryUserBean> getAllSalaryUserList() {
        return DBManager.queryFList("getAllSalaryUserList", "");
    }

    public static List<SalaryUserBean> getAllSalaryUserByProductID(Map<String, String> m) {
        return DBManager.queryFList("getAllSalaryUserByProductID", m);
    }

    public static boolean insertSalaryUser(SalaryUserBean pb, SettingLogsBean stl) {
        if (DBManager.insert("insertSalaryUser", pb)) {
            PublicTableDAO.insertSettingLogs("添加", "用户信息", pb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertSalaryUser(SalaryUserBean pb) {
        return DBManager.insert("insertSalaryUser", pb);
    }

    public static boolean updateSalaryUser(SalaryUserBean pb, SettingLogsBean stl) {
        if (DBManager.update("updateSalaryUser", pb)) {
            PublicTableDAO.insertSettingLogs("修改", "用户信息", pb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteSalaryUser(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteSalaryUser", m)) {
            PublicTableDAO.insertSettingLogs("删除", "用户信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}