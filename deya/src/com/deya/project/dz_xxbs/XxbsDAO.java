package com.deya.project.dz_xxbs;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

import java.util.List;
import java.util.Map;

public class XxbsDAO {

    public static List<XxbsBean> getXxbsInfoList(Map<String, String> map) {
        return DBManager.queryFList("getXxbsInfoList", map);
    }

    public static String getXxbsInfoCount(Map<String, String> map) {
        return DBManager.getString("getXxbsInfoCount", map);
    }

    public static boolean insertXxbs(XxbsBean xxbs, SettingLogsBean stl) {
        if (DBManager.insert("insertXxbs", xxbs)) {
            PublicTableDAO.insertSettingLogs("添加", "报送信息", xxbs.getId() + "", stl);
            return true;
        }
        return false;
    }
}
