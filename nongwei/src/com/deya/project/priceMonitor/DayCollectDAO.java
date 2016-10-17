package com.deya.project.priceMonitor;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayCollectDAO {

    public static List<DayCollectData> getDayCollectList(Map<String,String> m)
    {
        return DBManager.queryFList("getDayCollectList", m);
    }

    public static List<DayCollectData> getWeekCollectList(Map<String,String> m)
    {
        return DBManager.queryFList("getWeekCollectList", m);
    }

    public static List<TenCollectBean> getTenCollectList(Map<String,String> m)
    {
        return DBManager.queryFList("getTenCollectList", m);
    }

    public static List<PriceCollectBean> getPriceCollectList(Map<String,String> m)
    {
        return DBManager.queryFList("getPriceCollectList", m);
    }

    public static List<DayCollectData> collectByDay(Map<String,String> m)
    {
        return DBManager.queryFList("collectByDay", m);
    }
}