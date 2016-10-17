package com.deya.project.searchInfo;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class NzwzzManager implements ISyncCatch {

    public static TreeMap<Integer,NzwzzBean> ptMap = new TreeMap<Integer,NzwzzBean>();

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
            List<NzwzzBean> pdbList = NzwzzDAO.getAllNzwzz();
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

    public static String getNzwzzCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return NzwzzDAO.getNzwzzCount(m);
    }

    public static List<NzwzzBean> getNzwzzList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return NzwzzDAO.getNzwzzList(m);
    }

    public static NzwzzBean getNzwzzBean(String id) {

        return ptMap.get(Integer.parseInt(id));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(NzwzzDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertNzwzz(NzwzzBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_nzwzz"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(NzwzzDAO.insertNzwzz(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateNzwzz(NzwzzBean hb, SettingLogsBean stl) {
        if(NzwzzDAO.updateNzwzz(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteNzwzz(Map<String, String> m, SettingLogsBean stl) {
        if(NzwzzDAO.deleteNzwzz(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }
}