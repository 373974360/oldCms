package com.deya.project.priceMonitor;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class MarketTypeRPC
{
    public static String getMarketTypeCount(Map<String, String> m)
    {
        return MarketTypeManager.getMarketTypeCount(m);
    }

    public static List<MarketTypeBean> getMarketTypeList(Map<String, String> m)
    {
        return MarketTypeManager.getMarketTypeList(m);
    }

    public static List<MarketTypeBean> getAllMarketTypeList()
    {
        return MarketTypeManager.getAllMarketTypeList();
    }

    public static MarketTypeBean getMarketTypeBean(String id)
    {
        return MarketTypeManager.getMarketTypeBean(id);
    }

    public static boolean changeStatus(Map<String, String> m, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return MarketTypeManager.changeStatus(m, stl);
        }
        return false;
    }

    public static boolean insertMarketType(MarketTypeBean ptb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return MarketTypeManager.insertMarketType(ptb, stl);
        }
        return false;
    }

    public static boolean updateMarketType(MarketTypeBean ptb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return MarketTypeManager.updateMarketType(ptb, stl);
        }
        return false;
    }

    public static boolean deleteMarketType(Map<String, String> m, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return MarketTypeManager.deleteMarketType(m, stl);
        }
        return false;
    }
}
