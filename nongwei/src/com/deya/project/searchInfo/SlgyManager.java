package com.deya.project.searchInfo;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class SlgyManager implements ISyncCatch {

    public static TreeMap<Integer,SlgyBean> ptMap = new TreeMap<Integer,SlgyBean>();

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
            List<SlgyBean> pdbList = SlgyDAO.getAllSlgy();
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

    public static String getSlgyCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return SlgyDAO.getSlgyCount(m);
    }

    public static List<SlgyBean> getSlgyList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return SlgyDAO.getSlgyList(m);
    }

    public static SlgyBean getSlgyBean(String id) {

        return ptMap.get(Integer.parseInt(id));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(SlgyDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertSlgy(SlgyBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_slgy"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(SlgyDAO.insertSlgy(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateSlgy(SlgyBean hb, SettingLogsBean stl) {
        if(SlgyDAO.updateSlgy(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteSlgy(Map<String, String> m, SettingLogsBean stl) {
        if(SlgyDAO.deleteSlgy(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }
}