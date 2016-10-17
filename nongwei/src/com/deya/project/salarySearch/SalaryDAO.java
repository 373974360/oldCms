package com.deya.project.salarySearch;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaryDAO {
    public static String getSalaryCount(Map<String, String> m) {
        return DBManager.getString("getSalaryCount", m);
    }

    public static List<SalaryBean> getSalaryList(Map<String, String> m) {
        return DBManager.queryFList("getSalaryList", m);
    }

    public static SalaryBean getSalaryBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (SalaryBean) DBManager.queryFObj("getSalaryBean", m);
    }

    public static List<SalaryBean> getAllSalaryList() {
        return DBManager.queryFList("getAllSalaryList", "");
    }


    public static boolean insertSalary(SalaryBean pb, SettingLogsBean stl) {
        if (DBManager.insert("insertSalary", pb)) {
            PublicTableDAO.insertSettingLogs("添加", "工资信息", pb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertSalary(SalaryBean pb) {
        return DBManager.insert("insertSalary", pb);
    }

    public static boolean updateSalary(SalaryBean pb, SettingLogsBean stl) {
        if (DBManager.update("updateSalary", pb)) {
            PublicTableDAO.insertSettingLogs("修改", "工资信息", pb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteSalary(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteSalary", m)) {
            PublicTableDAO.insertSettingLogs("删除", "工资信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}