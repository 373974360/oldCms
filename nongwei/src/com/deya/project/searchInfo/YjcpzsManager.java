package com.deya.project.searchInfo;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.*;

public class YjcpzsManager implements ISyncCatch {

    public static TreeMap<Integer,YjcpzsBean> ptMap = new TreeMap<Integer,YjcpzsBean>();

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
            List<YjcpzsBean> pdbList = YjcpzsDAO.getAllYjcpzs();
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

    public static String getYjcpzsCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return YjcpzsDAO.getYjcpzsCount(m);
    }

    public static List<YjcpzsBean> getYjcpzsList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return YjcpzsDAO.getYjcpzsList(m);
    }

    public static YjcpzsBean getYjcpzsBean(String id) {

        return ptMap.get(Integer.parseInt(id));
    }

    public static boolean changeStatus(Map<String, String> m, SettingLogsBean stl) {
        if(YjcpzsDAO.changeStatus(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean insertYjcpzs(YjcpzsBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_yjcpzs"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        if(YjcpzsDAO.insertYjcpzs(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }


    public static boolean updateYjcpzs(YjcpzsBean hb, SettingLogsBean stl) {
        if(YjcpzsDAO.updateYjcpzs(hb, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }

    public static boolean deleteYjcpzs(Map<String, String> m, SettingLogsBean stl) {
        if(YjcpzsDAO.deleteYjcpzs(m, stl))
        {
            reloadCatchHandl();
            return true;
        }
        return false;
    }
}