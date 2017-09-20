package com.deya.project.priceMonitor;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.services.Log.LogManager;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class PriceInfoRPC
{
    public static String getPriceInfoCount(Map<String, String> m)
    {
        return PriceInfoManager.getPriceInfoCount(m);
    }

    public static List<PriceInfoBean> getPriceInfoList(Map<String, String> m)
    {
        return PriceInfoManager.getPriceInfoList(m);
    }

    public static List<PriceInfoBean> getAllPriceInfoList()
    {
        return PriceInfoManager.getAllPriceInfoList();
    }

    public static List<PriceInfoBean> getAllPriceInfoByProductID(Map<String, String> m)
    {
        return PriceInfoManager.getAllPriceInfoByProductID(m);
    }

    public static PriceInfoBean getPriceInfoBean(String gq_id)
    {
        return PriceInfoManager.getPriceInfoBean(gq_id);
    }

    public static boolean updatePriceInfo(PriceInfoBean hb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PriceInfoManager.updatePriceInfo(hb, stl);
        }
        return false;
    }

    public static boolean insertPriceInfo(PriceInfoBean hb, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PriceInfoManager.insertPriceInfo(hb, stl);
        }
        return false;
    }

    public static boolean deletePriceInfo(Map<String, String> m, HttpServletRequest request)
    {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        if (stl != null) {
            return PriceInfoManager.deletePriceInfo(m, stl);
        }
        return false;
    }

    public static boolean importPfLs(String excel_path, String userId, String site_id, String scName)
    {
        return ExcelHandleUtil.importPfLs(excel_path, userId, site_id, scName);
    }

    public static boolean importCdjg(String excel_path, String userId, String site_id, String scName)
    {
        return ExcelHandleUtil.importCdjg(excel_path, userId, site_id, scName);
    }

    public static boolean importNzjg(String excel_path, String userId, String site_id, String scName)
    {
        return ExcelHandleUtil.importNzjg(excel_path, userId, site_id, scName);
    }

    public static String exportInfoOutExcel(Map<String, String> m)
    {
        return ExcelHandleUtil.exportInfoOutExcel(m);
    }
}
