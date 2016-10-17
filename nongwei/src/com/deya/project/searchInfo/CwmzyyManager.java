package com.deya.project.searchInfo;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class CwmzyyManager implements ISyncCatch {

    public static TreeMap<Integer,CwmzyyBean> ptMap = new TreeMap<Integer,CwmzyyBean>();

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
            List<CwmzyyBean> pdbList = CwmzyyDAO.getAllCwmzyy();
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

    public static String getCwmzyyCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return CwmzyyDAO.getCwmzyyCount(m);
    }

    public static List<CwmzyyBean> getCwmzyyList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return CwmzyyDAO.getCwmzyyList(m);
    }

    public static CwmzyyBean getCwmzyyBean(String id) {

        return ptMap.get(Integer.parseInt(id));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(CwmzyyDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertCwmzyy(CwmzyyBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_cwmzyy"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(CwmzyyDAO.insertCwmzyy(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateCwmzyy(CwmzyyBean hb, SettingLogsBean stl) {
        if(CwmzyyDAO.updateCwmzyy(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteCwmzyy(Map<String, String> m, SettingLogsBean stl) {
        if(CwmzyyDAO.deleteCwmzyy(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }
}