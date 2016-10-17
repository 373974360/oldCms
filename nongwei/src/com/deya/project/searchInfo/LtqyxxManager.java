package com.deya.project.searchInfo;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class LtqyxxManager implements ISyncCatch {

    public static TreeMap<Integer,LtqyxxBean> ptMap = new TreeMap<Integer,LtqyxxBean>();

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
            List<LtqyxxBean> pdbList = LtqyxxDAO.getAllLtqyxx();
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

    public static String getLtqyxxCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return LtqyxxDAO.getLtqyxxCount(m);
    }

    public static List<LtqyxxBean> getLtqyxxList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return LtqyxxDAO.getLtqyxxList(m);
    }

    public static LtqyxxBean getLtqyxxBean(String id) {

        return ptMap.get(Integer.parseInt(id));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(LtqyxxDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertLtqyxx(LtqyxxBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_ltqyxx"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(LtqyxxDAO.insertLtqyxx(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateLtqyxx(LtqyxxBean hb, SettingLogsBean stl) {
        if(LtqyxxDAO.updateLtqyxx(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteLtqyxx(Map<String, String> m, SettingLogsBean stl) {
        if(LtqyxxDAO.deleteLtqyxx(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }
}