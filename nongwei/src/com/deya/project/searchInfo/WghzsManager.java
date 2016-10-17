package com.deya.project.searchInfo;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class WghzsManager implements ISyncCatch {

    public static TreeMap<Integer,WghzsBean> ptMap = new TreeMap<Integer,WghzsBean>();

    static{
        reloadCatchHandl();
    }

    public void reloadCatch()
    {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl()
    {
        ptMap.clear();
        try{
            Map<String,String> m = new HashMap<String,String>();
            List<WghzsBean> pdbList = WghzsDAO.getAllWghzs();
            if(pdbList != null && pdbList.size() > 0)
            {
                for(int i=0;i<pdbList.size();i++)
                {
                    ptMap.put(pdbList.get(i).getId(), pdbList.get(i));
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getWghzsCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return WghzsDAO.getWghzsCount(m);
    }

    public static List<WghzsBean> getWghzsList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return WghzsDAO.getWghzsList(m);
    }

    public static WghzsBean getWghzsBean(String id) {

        return ptMap.get(Integer.parseInt(id));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(WghzsDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertWghzs(WghzsBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_wghzs"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(WghzsDAO.insertWghzs(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateWghzs(WghzsBean hb, SettingLogsBean stl) {
        if(WghzsDAO.updateWghzs(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteWghzs(Map<String, String> m, SettingLogsBean stl) {
        if(WghzsDAO.deleteWghzs(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }
}