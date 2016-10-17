package com.deya.wcm.rmi.conf;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RmiServerConfDAO {

    public static List<RmiServerConfBean> getAllRmiServerConf() {
        return DBManager.queryFList("getAllRmiServerConf", "");
    }

}