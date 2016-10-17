package com.deya.project.priceMonitor;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceInfoDAO {
    public static String getPriceInfoCount(Map<String, String> m) {
        return DBManager.getString("getPriceInfoCount", m);
    }

    public static List<PriceInfoBean> getPriceInfoList(Map<String, String> m) {
        return DBManager.queryFList("getPriceInfoList", m);
    }

    public static PriceInfoBean getPriceInfoBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (PriceInfoBean) DBManager.queryFObj("getPriceInfoBean", m);
    }

    public static List<PriceInfoBean> getAllPriceInfoList() {
        return DBManager.queryFList("getAllPriceInfoList", "");
    }

    public static List<PriceInfoBean> getAllPriceInfoByProductID(Map<String, String> m) {
        return DBManager.queryFList("getAllPriceInfoByProductID", m);
    }

    public static List<PriceInfoBean> getAllPriceInfoListByDate(Map<String, String> m) {
        return DBManager.queryFList("getAllPriceInfoListByDate", m);
    }

    public static boolean insertPriceInfo(PriceInfoBean pb, SettingLogsBean stl) {
        if (DBManager.insert("insertPriceInfo", pb)) {
            PublicTableDAO.insertSettingLogs("添加", "价格监测信息", pb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertPriceInfo(PriceInfoBean pb) {
        return DBManager.insert("insertPriceInfo", pb);
    }

    public static boolean updatePriceInfo(PriceInfoBean pb, SettingLogsBean stl) {
        if (DBManager.update("updatePriceInfo", pb)) {
            PublicTableDAO.insertSettingLogs("修改", "价格监测信息", pb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean publishPriceInfo(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("publishPriceInfo", m)) {
            PublicTableDAO.insertSettingLogs("发布设置", "价格监测信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean deletePriceInfo(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deletePriceInfo", m)) {
            PublicTableDAO.insertSettingLogs("删除", "价格监测信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}