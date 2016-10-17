package com.deya.project.lyh_member;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.project.lyh_member.LyhMemberBean;
import com.deya.project.lyh_member.LyhMemberManager;
import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.template.TurnPageBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LyhMemberData {
    private static int cur_page = 1;
    private static int page_size = 15;

    public LyhMemberData() {
    }

    public static void getHouseSearchCon(String params, Map<String, String> con_map) {
        con_map.put("is_browser", "true");
        String orderby = "publish_time desc";
        String[] tempA = params.split(";");

        for(int i = 0; i < tempA.length; ++i) {
            String cp;
            if(tempA[i].toLowerCase().startsWith("kw=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$kw") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("keyword", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("start_time=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$start_time") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("start_time", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("end_time=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$end_time") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("end_time", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("hylx=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$hylx") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("hylx", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("xm=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$xm") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("xm", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("gender=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$gender") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("gender", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("jtzz=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$jtzz") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("jtzz", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("hkszd=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$hkszd") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("hkszd", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("gzdw=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$gzdw") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("gzdw", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("dwdz=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$dwdz") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("dwdz", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("isphoto=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$isphoto") && FormatUtil.isValiditySQL(cp)) {
                    con_map.put("isphoto", cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("orderby=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$orderby") && FormatUtil.isValiditySQL(cp)) {
                    orderby = cp;
                }
            }

            if(tempA[i].toLowerCase().startsWith("size=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$size") && FormatUtil.isNumeric(cp)) {
                    page_size = Integer.parseInt(cp);
                }
            }

            if(tempA[i].toLowerCase().startsWith("cur_page=")) {
                cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if(!"".equals(cp) && !cp.startsWith("$cur_page") && FormatUtil.isNumeric(cp)) {
                    cur_page = Integer.parseInt(cp);
                }
            }
        }

        con_map.put("current_data", DateUtil.getCurrentDate());
        con_map.put("page_size", String.valueOf(page_size));
        con_map.put("current_page", String.valueOf(cur_page));
        con_map.put("orderby", orderby);
        //System.out.println(con_map);
    }

    public static TurnPageBean getLyhMemberCount(String params) {
        HashMap con_map = new HashMap();
        getHouseSearchCon(params, con_map);
        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(LyhMemberManager.getLyhMemberCount(con_map)));
        tpb.setCur_page(cur_page);
        tpb.setPage_size(page_size);
        tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);
        if(tpb.getCount() % tpb.getPage_size() == 0 && tpb.getPage_count() > 1) {
            tpb.setPage_count(tpb.getPage_count() - 1);
        }

        if(cur_page > 1) {
            tpb.setPrev_num(cur_page - 1);
        }

        tpb.setNext_num(tpb.getPage_count());
        if(cur_page < tpb.getPage_count()) {
            tpb.setNext_num(cur_page + 1);
        }

        if(tpb.getPage_count() > 10 && cur_page > 5) {
            if(cur_page > tpb.getPage_count() - 4) {
                tpb.setCurr_start_num(tpb.getPage_count() - 6);
            } else {
                tpb.setCurr_start_num(cur_page - 2);
            }
        }

        return tpb;
    }

    public static List<LyhMemberBean> getLyhMemberList(String params) {
        HashMap con_map = new HashMap();
        getHouseSearchCon(params, con_map);
        int start_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return LyhMemberManager.getAllLyhMemberList(con_map);
    }

    public static List<LyhMemberBean> getLyhMemberHotList(String params) {
        HashMap con_map = new HashMap();
        getHouseSearchCon(params, con_map);
        con_map.put("current_page", "1");
        int start_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return LyhMemberManager.getAllLyhMemberList(con_map);
    }

    public static LyhMemberBean getLyhMemberObject(String id) {
        return LyhMemberManager.getLyhMemberBean(id, true);
    }
}
