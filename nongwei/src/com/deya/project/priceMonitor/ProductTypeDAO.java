package com.deya.project.priceMonitor;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductTypeDAO {

    
    public static String getProductTypeCount(Map<String, String> m) {
        return DBManager.getString("getProductTypeCount", m);
    }

    public static List<ProductTypeBean> getProductTypeList(Map<String, String> m) {
        return DBManager.queryFList("getProductTypeList", m);
    }

    public static ProductTypeBean getProductTypeBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (ProductTypeBean) DBManager.queryFObj("getProductTypeBean", m);
    }

    public static List<ProductTypeBean> getAllProductTypeList() {
        return DBManager.queryFList("getAllProductType","");
    }

    public static boolean insertProductType(ProductTypeBean ptb, SettingLogsBean stl) {
        if (DBManager.insert("insertProductType", ptb)) {
            PublicTableDAO.insertSettingLogs("添加", "农产品类别信息", ptb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertProductType(ProductTypeBean ptb) {
        return DBManager.insert("insertProductType", ptb);
    }

    public static boolean updateProductType(ProductTypeBean ptb, SettingLogsBean stl) {
        if (DBManager.update("updateProductType", ptb)) {
            PublicTableDAO.insertSettingLogs("修改", "农产品类别信息", ptb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeStatus", m)) {
            PublicTableDAO.insertSettingLogs("状态设置", "农产品类别信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean deleteProductType(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteProductType", m)) {
            PublicTableDAO.insertSettingLogs("删除", "农产品类别信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}