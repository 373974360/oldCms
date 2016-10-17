package com.deya.project.salarySearch;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.system.ware.WareManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelTitleRPC {
    public static String getExcelTitleCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return ExcelTitleManager.getExcelTitleCount(m);
    }

    public static List<ExcelTitleBean> getExcelTitleList(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return ExcelTitleManager.getExcelTitleList(m);
    }

    public static List<ExcelTitleBean> getExcelTitleListByTypeID(String typeId) {
        return ExcelTitleManager.getExcelTitleListByTypeID(typeId);
    }

    public static List<ExcelTitleBean> getAllExcelTitleList() {
        return ExcelTitleManager.getAllExcelTitleList();
    }

    public static ExcelTitleBean getExcelTitleBean(String id) {
        return ExcelTitleManager.getExcelTitleBean(id);
    }

    public static boolean insertExcelTitle(ExcelTitleBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return ExcelTitleManager.insertExcelTitle(hb,stl);
        }else
            return false;
    }

    public static boolean updateExcelTitle(ExcelTitleBean hb, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return ExcelTitleManager.updateExcelTitle(hb, stl);
        }else
            return false;
    }

    public static boolean deleteExcelTitle(Map<String, String> m,HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null){
            return ExcelTitleManager.deleteExcelTitle(m, stl);
        }else
            return false;
    }

    /**
     * 保存信息标签排序
     * @param ids	信息标签IDS，多个之间使用","分隔
     * @param request
     * @return	true 成功| false 失败
     */
    public static boolean savaExcelTitleSort(String ids, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return ExcelTitleManager.savaExcelTitleSort(ids, stl);
        }
        else
        {
            return false;
        }
    }

    /**
     * 保存信息标签排序
     * @param ids	信息标签IDS，多个之间使用","分隔
     * @param request
     * @return	true 成功| false 失败
     */
    public static boolean updateIsShow(Map<String, String> m, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if(stl != null)
        {
            return ExcelTitleManager.updateIsShow(m, stl);
        }
        else
        {
            return false;
        }
    }

}