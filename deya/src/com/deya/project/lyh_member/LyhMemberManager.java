package com.deya.project.lyh_member;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.project.lyh_member.LyhMemberBean;
import com.deya.project.lyh_member.LyhMemberDAO;
import com.deya.util.CryptoTools;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.util.OutExcel;
import com.deya.util.jconfig.JconfigUtilContainer;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.services.appeal.count.CountUtil;
import com.deya.wcm.services.org.user.SessionManager;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class LyhMemberManager implements ISyncCatch {
    private static Map<String, LyhMemberBean> lyhmember_map = new HashMap();
    private static Map<String, LyhMemberBean> lyhmem_reg_map = new HashMap();

    static {
        reloadCatchHandl();
    }

    public LyhMemberManager() {
    }

    public void reloadCatch() {
        reloadCatchHandl();
    }

    public static void reloadCatchHandl() {
        lyhmember_map.clear();
        List ltMember = LyhMemberDAO.getAllMemberList();
        int i;
        if(ltMember != null && ltMember.size() > 0) {
            for(i = 0; i < ltMember.size(); ++i) {
                lyhmember_map.put(String.valueOf(((LyhMemberBean)ltMember.get(i)).getId()), (LyhMemberBean)ltMember.get(i));
            }
        }

        lyhmem_reg_map.clear();
        if(ltMember != null && ltMember.size() > 0) {
            for(i = 0; i < ltMember.size(); ++i) {
                lyhmem_reg_map.put(((LyhMemberBean)ltMember.get(i)).getUsername(), (LyhMemberBean)ltMember.get(i));
            }
        }

    }

    public static String getLyhMemberCount(Map<String, String> m) {
        return m.containsKey("key_word") && !FormatUtil.isValiditySQL((String)m.get("key_word"))?"0":LyhMemberDAO.getLyhMemberCount(m);
    }

    public static List<LyhMemberBean> getAllLyhMemberList(Map<String, String> m) {
        return m.containsKey("key_word") && !FormatUtil.isValiditySQL((String)m.get("key_word"))?null:LyhMemberDAO.getLyhMemberList(m);
    }

    public static LyhMemberBean getLyhMemberBean(String id, boolean is_browser) {
        return LyhMemberDAO.getLyhMemberBean(id, is_browser);
    }

    public static boolean insertLyhMember(LyhMemberBean mb) {
        mb.setId(PublicTableDAO.getIDByTableName("cs_info_lyhmember"));
        mb.setPublish_time("");
        boolean insertLyhMember = LyhMemberDAO.insertLyhMember(mb);
        reloadCatchHandl();
        return insertLyhMember;
    }

    public static boolean updateLyhMember(LyhMemberBean mb, SettingLogsBean stl) {
        boolean updateLyhMember = LyhMemberDAO.updateLyhMember(mb, stl);
        reloadCatchHandl();
        return updateLyhMember;
    }

    public static boolean publishLyhMember(Map<String, String> m, SettingLogsBean stl) {
        if("1".equals(m.get("publish_flag"))) {
            m.put("publish_time", DateUtil.getCurrentDateTime());
        } else {
            m.put("publish_time", "");
        }

        boolean publishLyhMember = LyhMemberDAO.publishLyhMember(m, stl);
        reloadCatchHandl();
        return publishLyhMember;
    }

    public static boolean deleteLyhMember(Map<String, String> m, SettingLogsBean stl) {
        boolean deleteLyhMember = LyhMemberDAO.deleteLyhMember(m, stl);
        reloadCatchHandl();
        return deleteLyhMember;
    }

    public static LyhMemberBean getMemberBySession(HttpServletRequest request) {
        return (LyhMemberBean)SessionManager.get(request, "cicro_wcm_lyhmember");
    }

    public static String memberLogin(String me_account, String me_password) {
        CryptoTools ct = new CryptoTools();
        me_password = ct.encode(me_password);
        if(lyhmem_reg_map.containsKey(me_account)) {
            //System.out.println(lyhmem_reg_map.keySet() + "********************");
            LyhMemberBean mrb = (LyhMemberBean)lyhmem_reg_map.get(me_account);
            return me_password.equals(mrb.getPassword()) && mrb != null?(mrb.getPublish_flag() == 1?"true":"false"):"false";
        } else {
            return "false";
        }
    }

    public static LyhMemberBean getLyhMemberBenaByAccount(String me_account) {
        LyhMemberBean mrb = (LyhMemberBean)lyhmem_reg_map.get(me_account);
        return mrb != null?mrb:null;
    }

    public static boolean isAccountExisted(String account) {
        return lyhmem_reg_map.keySet().contains(account);
    }

    public static String decode(String password) {
        CryptoTools ct = new CryptoTools();
        password = ct.decode(password);
        return password;
    }

    public static String encode(String password) {
        CryptoTools ct = new CryptoTools();
        password = ct.encode(password);
        return password;
    }

    public static String memberOutExcel(List listBean, List headerList) {
        try {
            String[] e = getFileUrl();
            int size = headerList.size();
            String[] head = (String[])headerList.toArray(new String[size]);
            String[][] data = new String[listBean.size()][7];

            for(int oe = 0; oe < listBean.size(); ++oe) {
                LyhMemberBean memberBean = (LyhMemberBean)listBean.get(oe);
                data[oe][0] = memberBean.getXm();
                data[oe][1] = memberBean.getGender();
                data[oe][2] = memberBean.getCsny();
                data[oe][3] = memberBean.getTel();
                data[oe][4] = memberBean.getGzdw();
                data[oe][5] = memberBean.getJiguan();
                data[oe][6] = memberBean.getJtzz();
            }

            OutExcel var9 = new OutExcel("绥德商会会员信息表");
            var9.doOut(e[0], head, data);
            return e[1];
        } catch (Exception var8) {
            var8.printStackTrace();
            return "";
        }
    }

    public static String[] getFileUrl() {
        String root_path = JconfigUtilContainer.bashConfig().getProperty("path", "", "manager_path");
        String path = FormatUtil.formatPath(root_path + "/project/lyhMember/file/");
        CountUtil.deleteFile(path);
        String nowDate = CountUtil.getNowDayDate();
        String fileTemp2 = FormatUtil.formatPath(path + File.separator + nowDate);
        File file2 = new File(fileTemp2);
        if(!file2.exists()) {
            file2.mkdir();
        }

        String nowTime = CountUtil.getNowDayDateTime();
        String xls = nowTime + CountUtil.getEnglish(1) + ".xls";
        String xlsFile = fileTemp2 + File.separator + xls;
        String urlFile = "/manager/project/lyhMember/file/" + nowDate + File.separator + xls;
        String[] str = new String[]{xlsFile, urlFile};
        return str;
    }
}
