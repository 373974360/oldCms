package com.deya.project.salarySearch;

import com.deya.project.priceMonitor.ProductBean;
import com.deya.project.priceMonitor.ProductDAO;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.appeal.count.CountBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.system.ware.WareBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.system.ware.WareDAO;
import com.deya.wcm.services.org.user.UserManager;

import java.util.*;

public class ExcelTitleManager implements ISyncCatch {

    public static TreeMap<Integer, ExcelTitleBean> etMap = new TreeMap<Integer, ExcelTitleBean>();

    static {
        reloadCatchHandl();
    }

    public void reloadCatch() {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl() {
        etMap.clear();
        try {
            List<ExcelTitleBean> etbList = getAllExcelTitleList();
            if (etbList != null && etbList.size() > 0) {
                for (int i = 0; i < etbList.size(); i++) {
                    etMap.put(etbList.get(i).getId(), etbList.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getExcelTitleCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return ExcelTitleDAO.getExcelTitleCount(m);
    }

    public static List<ExcelTitleBean> getExcelTitleList(Map<String, String> m) {

        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return ExcelTitleDAO.getExcelTitleList(m);
    }

    public static List<ExcelTitleBean> getAllExcelTitleList() {
        return ExcelTitleDAO.getAllExcelTitleList();
    }

    static List<ExcelTitleBean> getExcelTitleListByTypeID(String typeId) {
        List<ExcelTitleBean> result = new ArrayList<ExcelTitleBean>();
        Collection<ExcelTitleBean> list = etMap.values();
        if (list.size() > 0) {
            for (ExcelTitleBean etb : list) {
                if (etb.getTypeId() == Integer.parseInt(typeId)) {
                    result.add(etb);
                }
            }
        }
        Collections.sort(result, new ExcelTitleComparatorForDESC());
        return result;
    }


    public static ExcelTitleBean getExcelTitleBean(String id) {
        return etMap.get(Integer.parseInt(id));
    }

    public static boolean insertExcelTitle(ExcelTitleBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_excelTitle"));
        hb.setAddTime(DateUtil.getCurrentDateTime());
        boolean result = ExcelTitleDAO.insertExcelTitle(hb, stl);
        if(result){
            reloadCatchHandl();
        }
        return result;
    }

    public static boolean updateExcelTitle(ExcelTitleBean hb, SettingLogsBean stl) {
        boolean result = ExcelTitleDAO.updateExcelTitle(hb, stl);
        if(result){
            reloadCatchHandl();
        }
        return result;
    }

    public static boolean deleteExcelTitle(Map<String, String> m, SettingLogsBean stl) {
        boolean result = ExcelTitleDAO.deleteExcelTitle(m, stl);
        if(result){
            reloadCatchHandl();
        }
        return result;
    }

    /**
     * 保存信息标签的排序
     *
     * @param wb  信息标签
     * @param stl
     * @return true 成功| false 失败
     */
    public static boolean savaExcelTitleSort(String ids, SettingLogsBean stl) {
        boolean flg = true;
        String[] arrIDS = ids.split(",");
        for (int i = 0; i < arrIDS.length; i++) {
            ExcelTitleBean etb = new ExcelTitleBean();
            etb.setId(Integer.parseInt(arrIDS[i]));
            etb.setSortId(i);
            flg = ExcelTitleDAO.savaExcelTitleSort(etb, stl) ? flg : false;
        }
        if(flg){
            reloadCatchHandl();
        }
        return flg;
    }

    /**
     * 保存信息标签的排序
     *
     * @param wb  信息标签
     * @param stl
     * @return true 成功| false 失败
     */
    public static boolean updateIsShow(Map<String, String> m, SettingLogsBean stl) {
        boolean result = ExcelTitleDAO.updateIsShow(m, stl);
        if(result){
            reloadCatchHandl();
        }
        return result;
    }

    public static class ExcelTitleComparatorForDESC implements Comparator<Object> {
        public int compare(Object o1, Object o2) {

            ExcelTitleBean cgb1 = (ExcelTitleBean) o1;
            ExcelTitleBean cgb2 = (ExcelTitleBean) o2;
            if (cgb2.getSortId() > cgb1.getSortId()) {
                return 1;
            } else if (cgb2.getSortId() == cgb1.getSortId()) {
                if (cgb2.getId() > cgb1.getId()) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getDateBefore("2015-12-10", 1));
    }

}