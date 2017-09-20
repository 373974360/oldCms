package com.deya.project.priceMonitor;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketTypeDAO
{
    public static String getMarketTypeCount(Map<String, String> m)
    {
        return DBManager.getString("getMarketTypeCount", m);
    }

    public static List<MarketTypeBean> getMarketTypeList(Map<String, String> m)
    {
        return DBManager.queryFList("getMarketTypeList", m);
    }

    public static MarketTypeBean getMarketTypeBean(String id)
    {
        Map m = new HashMap();
        m.put("id", id);
        return (MarketTypeBean)DBManager.queryFObj("getMarketTypeBean", m);
    }

    public static List<MarketTypeBean> getAllMarketTypeList()
    {
        return DBManager.queryFList("getAllMarketType", null);
    }

    public static boolean insertMarketType(MarketTypeBean mtb, SettingLogsBean stl)
    {
        if (DBManager.insert("insertMarketType", mtb))
        {
            PublicTableDAO.insertSettingLogs("添加", "市场分类信息", mtb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean insertMarketType(MarketTypeBean mtb)
    {
        return DBManager.insert("insertMarketType", mtb);
    }

    public static boolean updateMarketType(MarketTypeBean mtb, SettingLogsBean stl)
    {
        if (DBManager.update("updateMarketType", mtb))
        {
            PublicTableDAO.insertSettingLogs("修改", "市场分类信息", mtb.getId() + "", stl);
            return true;
        }
        return false;
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl)
    {
        if (DBManager.update("changeMarketTypeStatus", m))
        {
            PublicTableDAO.insertSettingLogs("状态设置", "市场分类信息", (String)m.get("ids"), stl);
            return true;
        }
        return false;
    }

    public static boolean deleteMarketType(Map<String, String> m, SettingLogsBean stl)
    {
        if (DBManager.delete("deleteMarketType", m))
        {
            PublicTableDAO.insertSettingLogs("删除", "市场分类信息", (String)m.get("ids"), stl);
            return true;
        }
        return false;
    }
}
