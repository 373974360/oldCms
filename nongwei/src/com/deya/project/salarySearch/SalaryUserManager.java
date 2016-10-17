package com.deya.project.salarySearch;

import com.deya.util.CryptoTools;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.org.user.LoginUserBean;
import com.deya.wcm.bean.page.PageBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.services.org.user.SessionManager;
import jxl.Cell;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class SalaryUserManager implements ISyncCatch {

    public static TreeMap<Integer, SalaryUserBean> subMap = new TreeMap<Integer, SalaryUserBean>();

    static {
        reloadCatchHandl();
    }

    public void reloadCatch() {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl() {
        subMap.clear();
        try {
            List<SalaryUserBean> etbList = getAllSalaryUserList();
            if (etbList != null && etbList.size() > 0) {
                for (int i = 0; i < etbList.size(); i++) {
                    subMap.put(etbList.get(i).getId(), etbList.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSalaryUserCount(Map<String, String> m) {
        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return "0";
        }
        return SalaryUserDAO.getSalaryUserCount(m);
    }

    public static List<SalaryUserBean> getSalaryUserList(Map<String, String> m) {

        if (m.containsKey("key_word")) {
            if (!FormatUtil.isValiditySQL((String) m.get("key_word")))
                return new ArrayList();
        }
        return SalaryUserDAO.getSalaryUserList(m);
    }

    public static List<SalaryUserBean> getAllSalaryUserList() {
        return SalaryUserDAO.getAllSalaryUserList();
    }

    public static List<SalaryUserBean> getAllSalaryUserByProductID(Map<String, String> m) {
        return SalaryUserDAO.getAllSalaryUserByProductID(m);
    }

    public static SalaryUserBean getSalaryUserBean(String id) {
        return SalaryUserDAO.getSalaryUserBean(id);
    }

    public static boolean insertSalaryUser(SalaryUserBean hb, SettingLogsBean stl) {
        hb.setId(PublicTableDAO.getIDByTableName("dz_salaryUser"));
        hb.setPassword(new CryptoTools().encode(hb.getPassword()));
        boolean result = SalaryUserDAO.insertSalaryUser(hb, stl);
        if(result){
            reloadCatchHandl();
        }
        return result;
    }

    public static boolean updateSalaryUser(SalaryUserBean hb, SettingLogsBean stl) {
        hb.setPassword(new CryptoTools().encode(hb.getPassword()));
        boolean result = SalaryUserDAO.updateSalaryUser(hb, stl);
        if(result){
            reloadCatchHandl();
        }
        return result;
    }

    public static boolean deleteSalaryUser(Map<String, String> m, SettingLogsBean stl) {
        boolean result = SalaryUserDAO.deleteSalaryUser(m, stl);
        if(result){
            reloadCatchHandl();
        }
        return result;
    }

    public static SalaryUserBean getUserByCell(Cell[] cell)
    {
        String name = cell[1].getContents().trim();
        String department = cell[2].getContents().trim();
        String identify = cell[3].getContents().trim();
        Set set = subMap.keySet();
        SalaryUserBean newBean = null;
        for (Iterator localIterator = set.iterator(); localIterator.hasNext();)
        {
            int i = ((Integer)localIterator.next()).intValue();
            SalaryUserBean sub = (SalaryUserBean)subMap.get(i);
            if(name.equals(sub.getName()) && identify.equals(sub.getIdentify()))
            {
                newBean = sub;
                return newBean;
            }
        }
        if(newBean == null)
        {
            newBean = new SalaryUserBean();
            newBean.setName(name);
            newBean.setDepartment(department);
            newBean.setIdentify(identify);
            newBean.setPassword(new CryptoTools().encode("000000"));
            newBean.setAddTime(DateUtil.getCurrentDateTime());
            newBean.setStatus("0");
            if(insertSalaryUser(newBean,null))
            {
                return newBean;
            }
        }
        return newBean;
    }

    /**
     * 登录成功，将用户对象写入session
     *
     * @param UserBean ub
     * @param HttpServletRequest request
     * @return boolean
     */
    public static SalaryUserBean findSalaryUser(String name, String identify,String password)
    {
        password = new CryptoTools().encode(password);
        Set set = subMap.keySet();
        SalaryUserBean newBean = null;
        for (Iterator localIterator = set.iterator(); localIterator.hasNext();)
        {
            int i = ((Integer)localIterator.next()).intValue();
            SalaryUserBean sub = (SalaryUserBean)subMap.get(i);
            if(name.equals(sub.getName()) && identify.equals(sub.getIdentify()) && password.equals(sub.getPassword()))
            {
                newBean = sub;
                return newBean;
            }
        }
        return newBean;
    }

    public static SalaryUserBean salaryUserLogin(Map<String,String> m,HttpServletRequest request)
    {
        SalaryUserBean sub = SalaryUserManager.getUserBySession(request);
        if(sub == null)
        {
            String name = m.get("name");
            String identify = m.get("identify");
            String password = m.get("password");
            sub = SalaryUserManager.findSalaryUser(name,identify,password);
            if(sub != null)
            {
                SalaryUserManager.setSalarySession(sub,request);
            }
            else {
            }
        }
        return sub;
    }

    /**
     * 登录成功，将用户对象写入session
     *
     * @param UserBean ub
     * @param HttpServletRequest request
     * @return boolean
     */
    public static void setSalarySession(SalaryUserBean lub, HttpServletRequest request)
    {
        SessionManager.set(request, "cicro_salary_user", lub);
    }

    /**
     * 从session得到用户对象
     *
     * @param HttpServletRequest request
     * @return boolean
     */
    public static SalaryUserBean getUserBySession(HttpServletRequest request)
    {
        return (SalaryUserBean)SessionManager.get(request, "cicro_salary_user");
    }

    /**
     * 注销登陆
     *
     * @param HttpServletRequest request
     * @return boolean
     */
    public static void logout(HttpServletRequest request)
    {
        SessionManager.remove(request, "cicro_salary_user");
    }

    public static SalaryUserBean changePassword(String id,String password,HttpServletRequest request){
        SalaryUserBean sub = getSalaryUserBean(id);
        if(sub != null)
        {
            sub.setPassword(password);
            updateSalaryUser(sub,null);
            reloadCatchHandl();
            SessionManager.remove(request,"cicro_salary_user");
        }
        return sub;
    };

    public static void main(String[] args) {
        System.out.println(DateUtil.getDateBefore("2015-12-10",1));
    }

}