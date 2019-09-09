package com.deya.wcm.services.zwgk.ysqgk;

import com.deya.util.DateUtil;
import com.deya.util.RandomStrg;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkConfigBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkListBean;
import com.deya.wcm.bean.zwgk.ysqgk.YsqgkProcessBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.zwgk.ysqgk.YsqgkInfoDAO;
import com.deya.wcm.dao.zwgk.ysqgk.YsqgkProessDAO;
import com.deya.wcm.services.appeal.cpDept.CpDeptManager;
import com.deya.wcm.services.zwgk.node.GKNodeManager;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YsqgkInfoManager {
    private static String defalut_randon_str = "0-9";

    public static Map<String, String> insertYsqgkInfo(YsqgkBean ysqgk, SettingLogsBean stl) {
        YsqgkConfigBean ycf = new YsqgkConfigBean();

        String ysqCode = ycf.getCode_pre() + DateUtil.getCurrentDateTime(ycf.getCode_rule()) + RandomStrg.getRandomStr(defalut_randon_str, new StringBuilder().append(ycf.getCode_num()).append("").toString());
        String query_code = "";

        YsqgkConfigBean ycb = YsqgkConfigManager.getYsqgkConfigBeanBySiteId(ysqgk.getSite_id());
        query_code = RandomStrg.getRandomStr(defalut_randon_str, ycb.getQuery_num() + "");

        ysqgk.setYsq_code(ysqCode);
        ysqgk.setQuery_code(query_code);
        ysqgk.setYsq_id(PublicTableDAO.getIDByTableName(PublicTableDAO.YSQGK_INFO_TABLE_NAME));
        if ((ysqgk.getPut_dtime() == null) || ("".equals(ysqgk.getPut_dtime()))) {
            ysqgk.setPut_dtime(DateUtil.getCurrentDateTime().substring(0, 10));
        }
        ysqgk.setNode_name(CpDeptManager.getCpDeptName(Integer.parseInt(ysqgk.getNode_id())));
        if (YsqgkInfoDAO.insertYsqgkInfo(ysqgk, stl)) {
            Map<String, String> m = new HashMap();
            m.put("ysq_code", ysqCode);
            m.put("query_code", query_code);
            return m;
        }
        return null;
    }

    public static YsqgkBean getYsqgkBean(String ysq_id) {
        return YsqgkInfoDAO.getYsqgkBean(ysq_id);
    }

    public static YsqgkBean getYsqgkBeanForQuery(String ysq_code, String query_code) {
        Map<String, String> m = new HashMap();
        m.put("ysq_code", ysq_code);
        m.put("query_code", query_code);
        return YsqgkInfoDAO.getYsqgkBeanForQuery(m);
    }

    public static List<YsqgkListBean> getYsqgkLists(Map<String, String> m) {
        setTimeCon(m);
        return YsqgkInfoDAO.getYsqgkLists(m);
    }

    public static void setTimeCon(Map<String, String> m) {
        if (m.containsKey("put_dtime")) {
            if ("day".equals(m.get("put_dtime"))) {
                m.put("put_dtime", DateUtil.getCurrentDate() + " 00:00:00");
            } else if ("yestetoday".equals(m.get("put_dtime"))) {
                m.put("put_dtime", DateUtil.getDateBefore(DateUtil.getCurrentDate(), 1) + " 00:00:00");
            } else if ("week".equals(m.get("put_dtime"))) {
                m.put("put_dtime", DateUtil.getDateBefore(DateUtil.getCurrentDate(), 7) + " 00:00:00");
            } else if ("month".equals(m.get("put_dtime"))) {
                m.put("put_dtime", DateUtil.getDateBefore(DateUtil.getCurrentDate(), 30) + " 00:00:00");
            } else {
                m.remove("put_dtime");
            }
        }
    }

    public static int getYsqgkListsCount(Map<String, String> m) {
        setTimeCon(m);
        return YsqgkInfoDAO.getYsqgkListsCount(m);
    }

    public static boolean updateYsqgkInfo(YsqgkBean ysqgk, SettingLogsBean stl) {
        if (YsqgkInfoDAO.updateYsqgkInfo(ysqgk, stl)) {
            return true;
        }
        return false;
    }

    public static boolean updateStatus(Map<String, String> map, SettingLogsBean stl) {
        if(Integer.parseInt(map.get("do_state"))>-2){
            if (map.size() > 0) {
                if ("0".equals(map.get("dealtype"))) {
                    map.put("accept_dtime", DateUtil.getCurrentDateTime());
                    map.put("reply_dtime", "");
                } else if ("1".equals(map.get("dealtype"))) {
                    map.put("reply_dtime", DateUtil.getCurrentDateTime());
                } else if ("2".equals("dealtype")) {
                    map.put("accept_dtime", DateUtil.getCurrentDateTime());
                }
            }
        }else{
            YsqgkBean ysq = YsqgkInfoManager.getYsqgkBean(map.get("ysq_id"));
            Map<String,String> proessMap = new HashMap<>();
            proessMap.put("pro_id",PublicTableDAO.getIDByTableName("cs_gk_ysq_process")+"");
            proessMap.put("ysq_id",ysq.getYsq_id()+"");
            proessMap.put("do_dept",map.get("node_id"));
            proessMap.put("do_dept_name",map.get("node_name"));
            proessMap.put("old_dept",ysq.getNode_id());
            proessMap.put("old_dept_name",ysq.getNode_name());
            proessMap.put("pro_content",map.get("reply_content"));
            proessMap.put("pro_dtime",DateUtil.getCurrentDateTime());
            YsqgkProessDAO.insertYsqgkProess(proessMap);
            map.remove("reply_content");
            map.remove("do_state");
        }
        if (YsqgkInfoDAO.updateStatus(map, stl)) {
            return true;
        }
        return false;
    }

    public static boolean setDeleteState(Map<String, String> m, SettingLogsBean stl) {
        if (YsqgkInfoDAO.setDeleteState(m, stl)) {
            return true;
        }
        return false;
    }

    public static boolean reBackInfos(Map<String, String> m, SettingLogsBean stl) {
        if (YsqgkInfoDAO.reBackInfos(m, stl)) {
            return true;
        }
        return false;
    }

    public static boolean deleteYsqgkInfo(Map<String, String> m, SettingLogsBean stl) {
        if (YsqgkInfoDAO.deleteYsqgkInfo(m, stl)) {
            return true;
        }
        return false;
    }

    public static boolean clearDeleteYsqgkInfos(SettingLogsBean stl) {
        if (YsqgkInfoDAO.clearDeleteYsqgkInfos(stl)) {
            return true;
        }
        return false;
    }

    public static String getYsqStatistics(Map<String, String> m) {
        return YsqgkInfoDAO.getYsqStatistics(m);
    }

    public static void main(String[] args) {
        String d = "2011-09-01 00:11:38";
        System.out.println(getYsqgkBeanForQuery("YSQ201202108488", "577200"));
    }


    public static List<YsqgkProcessBean> getYsqgkProessList(Map<String,String> m)
    {
        return YsqgkProessDAO.getYsqgkProessList(m);
    }
}
