package com.deya.project.priceMonitor;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class ProductRPC
{
    public static String getProductCount(Map<String, String> m)
    {
        if ((m.containsKey("key_word")) &&
                (!FormatUtil.isValiditySQL((String)m.get("key_word")))) {
            return "0";
        }
        return ProductManager.getProductCount(m);
    }

    public static List<ProductBean> getProductList(Map<String, String> m)
    {
        if ((m.containsKey("key_word")) &&
                (!FormatUtil.isValiditySQL((String)m.get("key_word")))) {
            return new ArrayList();
        }
        return ProductManager.getProductList(m);
    }

    public static List<ProductBean> getAllProductListByTypeId(String typeId)
    {
        return ProductManager.getAllProductListByTypeId(typeId);
    }

    public static ProductBean getProductBean(String id)
    {
        return ProductManager.getProductBean(id);
    }

    public static boolean changeStatus(Map<String, String> m, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ProductManager.changeStatus(m, stl);
        }
        return false;
    }

    public static boolean insertProduct(ProductBean hb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ProductManager.insertProduct(hb, stl);
        }
        return false;
    }

    public static boolean updateProduct(ProductBean hb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ProductManager.updateProduct(hb, stl);
        }
        return false;
    }

    public static boolean deleteProduct(Map<String, String> m, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ProductManager.deleteProduct(m, stl);
        }
        return false;
    }
}
