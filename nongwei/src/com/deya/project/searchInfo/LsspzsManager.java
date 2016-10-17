package com.deya.project.searchInfo;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class LsspzsManager implements ISyncCatch {

    public static TreeMap<Integer,LsspzsBean> ptMap = new TreeMap<Integer,LsspzsBean>();

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
            List<LsspzsBean> pdbList = LsspzsDAO.getAllLsspzs();
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

    public static String getLsspzsCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return LsspzsDAO.getLsspzsCount(m);
    }

    public static List<LsspzsBean> getLsspzsList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return LsspzsDAO.getLsspzsList(m);
    }

    public static LsspzsBean getLsspzsBean(String id) {

        return ptMap.get(Integer.parseInt(id));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(LsspzsDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertLsspzs(LsspzsBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_lsspzx"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(LsspzsDAO.insertLsspzs(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateLsspzs(LsspzsBean hb, SettingLogsBean stl) {
        if(LsspzsDAO.updateLsspzs(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteLsspzs(Map<String, String> m, SettingLogsBean stl) {
        if(LsspzsDAO.deleteLsspzs(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }
}