package com.deya.project.searchInfo;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class ZrbhqManager implements ISyncCatch {

    public static TreeMap<Integer,ZrbhqBean> ptMap = new TreeMap<Integer,ZrbhqBean>();

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
            List<ZrbhqBean> pdbList = ZrbhqDAO.getAllZrbhq();
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

    public static String getZrbhqCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return ZrbhqDAO.getZrbhqCount(m);
    }

    public static List<ZrbhqBean> getZrbhqList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return ZrbhqDAO.getZrbhqList(m);
    }

    public static ZrbhqBean getZrbhqBean(String id) {

        return ptMap.get(Integer.parseInt(id));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(ZrbhqDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertZrbhq(ZrbhqBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_zrbhq"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(ZrbhqDAO.insertZrbhq(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateZrbhq(ZrbhqBean hb, SettingLogsBean stl) {
        if(ZrbhqDAO.updateZrbhq(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteZrbhq(Map<String, String> m, SettingLogsBean stl) {
        if(ZrbhqDAO.deleteZrbhq(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }
}