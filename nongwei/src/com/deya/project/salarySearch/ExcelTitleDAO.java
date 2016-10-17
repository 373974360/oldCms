package com.deya.project.salarySearch;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelTitleDAO {
    public static String getExcelTitleCount(Map<String, String> m) {
        return DBManager.getString("getExcelTitleCount", m);
    }

    public static List<ExcelTitleBean> getExcelTitleList(Map<String, String> m) {
        return DBManager.queryFList("getExcelTitleList", m);
    }

    public static ExcelTitleBean getExcelTitleBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (ExcelTitleBean) DBManager.queryFObj("getExcelTitleBean", m);
    }

    public static List<ExcelTitleBean> getAllExcelTitleList() {
        return DBManager.queryFList("getAllExcelTitleList", "");
    }

    public static List<ExcelTitleBean> getAllExcelTitleByTypeID(Map<String, String> m) {
        return DBManager.queryFList("getAllExcelTitleByTypeID", m);
    }

    public static boolean insertExcelTitle(ExcelTitleBean pb, SettingLogsBean stl) {
        if (DBManager.insert("insertExcelTitle", pb)) {
            PublicTableDAO.insertSettingLogs("添加", "Excel表头信息", pb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertExcelTitle(ExcelTitleBean pb) {
        return DBManager.insert("insertExcelTitle", pb);
    }

    public static boolean updateExcelTitle(ExcelTitleBean pb, SettingLogsBean stl) {
        if (DBManager.update("updateExcelTitle", pb)) {
            PublicTableDAO.insertSettingLogs("修改", "Excel表头信息", pb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteExcelTitle(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteExcelTitle", m)) {
            PublicTableDAO.insertSettingLogs("删除", "Excel表头信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    /**
     * 保存信息标签排序
     * @param wb	信息标签对象
     * @param stl
     * @return	true 成功| false 失败
     */
    public static boolean savaExcelTitleSort(ExcelTitleBean etb, SettingLogsBean stl)
    {
        if(DBManager.update("savaExcelTitleSort", etb))
        {
            PublicTableDAO.insertSettingLogs("修改", "Excel表头排序", etb.getId()+"", stl);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean updateIsShow(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("updateIsShow", m)) {
            PublicTableDAO.insertSettingLogs("修改", "Excel表头是否显示", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}