package com.deya.project.fd_dacx;


import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DacxManager {
    public static DacxBean getDacxBean(Map<String, String> con_map) {
        return DacxDAO.getDacxBean(con_map);
    }

}