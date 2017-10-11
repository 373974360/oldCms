package com.deya.project.priceMonitor;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class ProductTypeRPC
{
    public static String getProductTypeCount(Map<String, String> m)
    {
        return ProductTypeManager.getProductTypeCount(m);
    }

    public static List<ProductTypeBean> getProductTypeList(Map<String, String> m)
    {
        return ProductTypeManager.getProductTypeList(m);
    }

    public static List<ProductTypeBean> getAllProductTypeList()
    {
        return ProductTypeManager.getAllProductTypeList();
    }

    public static List<ProductTypeBean> getProductTypeByMarketId(String marketId)
    {
        return ProductTypeManager.getProductTypeByMarketId(marketId);
    }

    public static ProductTypeBean getProductTypeBean(String id)
    {
        return ProductTypeManager.getProductTypeBean(id);
    }

    public static boolean changeStatus(Map<String, String> m, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ProductTypeManager.changeStatus(m, stl);
        }
        return false;
    }

    public static boolean insertProductType(ProductTypeBean ptb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ProductTypeManager.insertProductType(ptb, stl);
        }
        return false;
    }

    public static boolean updateProductType(ProductTypeBean ptb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ProductTypeManager.updateProductType(ptb, stl);
        }
        return false;
    }

    public static boolean deleteProductType(Map<String, String> m, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return ProductTypeManager.deleteProductType(m, stl);
        }
        return false;
    }
}
