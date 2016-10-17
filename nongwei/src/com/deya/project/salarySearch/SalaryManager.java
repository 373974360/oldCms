package com.deya.project.salarySearch;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.services.org.user.SessionManager;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class SalaryManager {
    public static String getSalaryCount(Map<String, String> m) {
        return SalaryDAO.getSalaryCount(m);
    }

    public static List<SalaryBean> getSalaryList(Map<String, String> m) {
        return setExcelName(SalaryDAO.getSalaryList(m));
    }

    public static List<SalaryBean> getAllSalaryList() {
        return setExcelName(SalaryDAO.getAllSalaryList());
    }


    public static SalaryBean getSalaryBean(String id) {
        return setExcelName(SalaryDAO.getSalaryBean(id));
    }

    public static boolean insertSalary(SalaryBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_salary"));
        return SalaryDAO.insertSalary(hb, stl);
    }

    public static boolean updateSalary(SalaryBean hb, SettingLogsBean stl) {
        return SalaryDAO.updateSalary(hb, stl);
    }

    public static boolean deleteSalary(Map<String, String> m, SettingLogsBean stl) {
        return SalaryDAO.deleteSalary(m, stl);
    }

    public static SalaryBean setExcelName(SalaryBean sb)
    {
        ExcelTitleBean etb = ExcelTitleManager.getExcelTitleBean(sb.getExcelTitleId() + "");
        sb.setExcelName(etb.getCname());
        return sb;
    }

    public static List<SalaryBean> setExcelName(List<SalaryBean> salaryList)
    {
        List<SalaryBean> result = new ArrayList<SalaryBean>();
        if(salaryList != null && salaryList.size() > 0)
        {
            for(SalaryBean sb : salaryList)
            {
                ExcelTitleBean etb = ExcelTitleManager.getExcelTitleBean(sb.getExcelTitleId() + "");
                sb.setExcelName(etb.getCname());
                result.add(sb);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getDateBefore("2015-12-10",1));
    }

}