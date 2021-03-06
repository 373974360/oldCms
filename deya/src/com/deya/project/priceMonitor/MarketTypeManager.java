package com.deya.project.priceMonitor;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MarketTypeManager
        implements ISyncCatch
{
    public static TreeMap<Integer, MarketTypeBean> mtMap = new TreeMap();

    static
    {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl()
    {
        mtMap.clear();
        try
        {
            Map<String, String> m = new HashMap();
            List<MarketTypeBean> mtb_list = MarketTypeDAO.getAllMarketTypeList();
            if ((mtb_list != null) && (mtb_list.size() > 0)) {
                for (int i = 0; i < mtb_list.size(); i++) {
                    mtMap.put(Integer.valueOf(((MarketTypeBean)mtb_list.get(i)).getId()), mtb_list.get(i));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getMarketTypeCount(Map<String, String> m)
    {
        if ((m.containsKey("key_word")) &&
                (!FormatUtil.isValiditySQL((String)m.get("key_word")))) {
            return "0";
        }
        return MarketTypeDAO.getMarketTypeCount(m);
    }

    public static List<MarketTypeBean> getMarketTypeList(Map<String, String> m)
    {
        if ((m.containsKey("key_word")) &&
                (!FormatUtil.isValiditySQL((String)m.get("key_word")))) {
            return new ArrayList();
        }
        return MarketTypeDAO.getMarketTypeList(m);
    }

    public static List<MarketTypeBean> getAllMarketTypeList()
    {
        List<MarketTypeBean> result = new ArrayList();
        Set<Integer> set = mtMap.keySet();
        for (Iterator localIterator = set.iterator(); localIterator.hasNext();)
        {
            int i = ((Integer)localIterator.next()).intValue();
            result.add(mtMap.get(Integer.valueOf(i)));
        }
        return result;
    }

    public static MarketTypeBean getMarketTypeBean(String id)
    {
        return (MarketTypeBean)mtMap.get(Integer.valueOf(Integer.parseInt(id)));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl)
    {
        if (MarketTypeDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertMarketType(MarketTypeBean mtb, SettingLogsBean stl)
    {
        mtb.setId(PublicTableDAO.getIDByTableName("dz_marketTpye"));
        mtb.setAddTime(DateUtil.getCurrentDateTime());
        if (MarketTypeDAO.insertMarketType(mtb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean updateMarketType(MarketTypeBean mtb, SettingLogsBean stl)
    {
        mtb.setAddTime(DateUtil.getCurrentDateTime());
        if (MarketTypeDAO.updateMarketType(mtb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteMarketType(Map<String, String> m, SettingLogsBean stl)
    {
        if (MarketTypeDAO.deleteMarketType(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public void reloadCatch() {}
}
