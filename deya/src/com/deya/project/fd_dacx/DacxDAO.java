package com.deya.project.fd_dacx;


import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DacxDAO {

    public static DacxBean getDacxBean(Map<String, String> con_map) {
        return (DacxBean) DBManager.queryFObj("getDacxBean", con_map);
    }
}
