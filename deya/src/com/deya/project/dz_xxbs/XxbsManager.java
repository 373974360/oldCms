package com.deya.project.dz_xxbs;

import com.deya.util.DateUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class XxbsManager {

    public static List<XxbsBean> getXxbsInfoList(Map<String, String> map) {
        return XxbsDAO.getXxbsInfoList(map);
    }

    public static String getXxbsInfoCount(Map<String, String> map) {
        return XxbsDAO.getXxbsInfoCount(map);
    }

    public static XxbsBean getXxbs(String id) {
        return XxbsDAO.getXxbs(id);
    }

    public static boolean insertXxbs(XxbsBean xxbs, SettingLogsBean stl) {
        xxbs.setId(PublicTableDAO.getIDByTableName("dz_xxbs"));
        xxbs.setInput_dtime(DateUtil.getCurrentDateTime());
        if (StringUtils.isEmpty(xxbs.getReleased_dtime())) {
            xxbs.setReleased_dtime(DateUtil.getCurrentDateTime());
        }
        return XxbsDAO.insertXxbs(xxbs, stl);
    }

    public static boolean updateXxbs(XxbsBean xxbs, SettingLogsBean stl) {
        xxbs.setModify_dtime(DateUtil.getCurrentDateTime());
        if (StringUtils.isEmpty(xxbs.getReleased_dtime())) {
            xxbs.setReleased_dtime(DateUtil.getCurrentDateTime());
        }
        return XxbsDAO.updateXxbs(xxbs, stl);
    }

    public static boolean deleteXxbs(List<XxbsBean> list, SettingLogsBean stl) {
        return XxbsDAO.deleteXxbs(list, stl);
    }

    public static boolean updateInfoStatus(List<XxbsBean> list,String status, SettingLogsBean stl){
        return XxbsDAO.updateInfoStatus(list, status, stl);
    }
    public static boolean updateNoPassStatus(List<XxbsBean> list,String desc, SettingLogsBean stl) {
        return XxbsDAO.updateNoPassStatus(list, desc, stl);
    }

    public static boolean infoPass(List<XxbsBean> list,String cat_id,String cat_name, SettingLogsBean stl){
        return XxbsDAO.infoPass(list, cat_id, cat_name, stl);
    }

    public static String getXxbsDeptCount(Map<String, String> map) {
        return XxbsDAO.getXxbsDeptCount(map);
    }

}
