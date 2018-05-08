package com.deya.wcm.rmi.conf;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RmiServerConfManager {

    public static List<RmiServerConfBean> getAllRmiServerConf() {
        return RmiServerConfDAO.getAllRmiServerConf();
    }

}