package com.deya.project.dz_xxbs;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class XxbsManager {

    public static List<XxbsBean> getXxbsInfoList(Map<String,String> map){
        return XxbsDAO.getXxbsInfoList(map);
    }

    public static String getXxbsInfoCount(Map<String,String> map){
        return XxbsDAO.getXxbsInfoCount(map);
    }

    public static boolean insertXxbs(XxbsBean xxbs, SettingLogsBean stl){
        xxbs.setId(PublicTableDAO.getIDByTableName("dz_xxbs"));
        xxbs.setInput_dtime(DateUtil.getCurrentDateTime());
        if(StringUtils.isEmpty(xxbs.getReleased_dtime())){
            xxbs.setReleased_dtime(DateUtil.getCurrentDateTime());
        }
        return XxbsDAO.insertXxbs(xxbs, stl);
    }
}
