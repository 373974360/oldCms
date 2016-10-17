package com.deya.project.searchInfo;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class LmzmqyManager implements ISyncCatch {

    public static TreeMap<Integer,LmzmqyBean> ptMap = new TreeMap<Integer,LmzmqyBean>();

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
            List<LmzmqyBean> pdbList = LmzmqyDAO.getAllLmzmqy();
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

    public static String getLmzmqyCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return LmzmqyDAO.getLmzmqyCount(m);
    }

    public static List<LmzmqyBean> getLmzmqyList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return LmzmqyDAO.getLmzmqyList(m);
    }

    public static LmzmqyBean getLmzmqyBean(String id) {

        return ptMap.get(Integer.parseInt(id));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(LmzmqyDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertLmzmqy(LmzmqyBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_lmzmqy"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(LmzmqyDAO.insertLmzmqy(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateLmzmqy(LmzmqyBean hb, SettingLogsBean stl) {
        if(LmzmqyDAO.updateLmzmqy(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteLmzmqy(Map<String, String> m, SettingLogsBean stl) {
        if(LmzmqyDAO.deleteLmzmqy(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }
}