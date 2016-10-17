package com.deya.project.priceMonitor;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {
    private static Map<String, String> m;

    public static String getProductCount(Map<String, String> m) {
        return DBManager.getString("getProductCount", m);
    }

    public static List<ProductBean> getProductList(Map<String, String> m) {
        ProductDAO.m = m;
        return DBManager.queryFList("getProductList", m);
    }
    
    public static ProductBean getProductBean(String id) {
        Map m = new HashMap();
        m.put("id", id);
        return (ProductBean) DBManager.queryFObj("getProductBean", m);
    }

    public static List<ProductBean> getAllProduct() {
        return DBManager.queryFList("getAllProduct", "");
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.update("changeProductStatus", m)) {
            PublicTableDAO.insertSettingLogs("修改状态", "农产品信息",  (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean insertProduct(ProductBean pdb, SettingLogsBean stl) {
        if (DBManager.insert("insertProduct", pdb)) {
            PublicTableDAO.insertSettingLogs("添加", "农产品信息", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertProduct(ProductBean pdb) {
        return DBManager.insert("insertProduct", pdb);
    }

    public static boolean updateProduct(ProductBean pdb, SettingLogsBean stl) {
        if (DBManager.update("updateProduct", pdb)) {
            PublicTableDAO.insertSettingLogs("修改", "农产品信息", pdb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean deleteProduct(Map<String, String> m, SettingLogsBean stl) {
        if (DBManager.delete("deleteProduct", m)) {
            PublicTableDAO.insertSettingLogs("删除", "农产品信息", (String) m.get("ids"), stl);
            return true;
        }
        return false;
    }
}