package com.deya.project.searchInfo;


import com.deya.util.FormatUtil;
import com.deya.wcm.bean.template.TurnPageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NzwzzData {
    private static int cur_page = 1;
    private static int page_size = 15;

    public static void getNzwzzSearchCon(String params, Map<String, String> con_map) {
        String orderby = "id desc";
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++) {
            if (tempA[i].toLowerCase().startsWith("kw=")) {
                String kw = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(kw)) && (!kw.startsWith("$kw")) && (FormatUtil.isValiditySQL(kw))) {
                    con_map.put("keyword", kw);
                }
            }
            if (tempA[i].toLowerCase().startsWith("dwmc=")) {
                String dwmc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(dwmc)) && (!dwmc.startsWith("$dwmc")) && (FormatUtil.isValiditySQL(dwmc))) {
                    con_map.put("dwmc", dwmc);
                }
            }
            if (tempA[i].toLowerCase().startsWith("zyfzr=")) {
                String zyfzr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(zyfzr)) && (!zyfzr.startsWith("$zyfzr")) && (FormatUtil.isValiditySQL(zyfzr))) {
                    con_map.put("zyfzr", zyfzr);
                }
            }
            if (tempA[i].toLowerCase().startsWith("zyyw=")) {
                String zyyw = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(zyyw)) && (!zyyw.startsWith("$zyyw")) && (FormatUtil.isValiditySQL(zyyw))) {
                    con_map.put("zyyw", zyyw);
                }
            }
            if (tempA[i].toLowerCase().startsWith("dh=")) {
                String dh = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(dh)) && (!dh.startsWith("$dh")) && (FormatUtil.isValiditySQL(dh))) {
                    con_map.put("dh", dh);
                }
            }
            if (tempA[i].toLowerCase().startsWith("dz=")) {
                String dz = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(dz)) && (!dz.startsWith("$dz")) && (FormatUtil.isValiditySQL(dz))) {
                    con_map.put("dz", dz);
                }
            }
            if (tempA[i].toLowerCase().startsWith("yb=")) {
                String yb = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(yb)) && (!yb.startsWith("$yb")) && (FormatUtil.isValiditySQL(yb))) {
                    con_map.put("yb", yb);
                }
            }
            if (tempA[i].toLowerCase().startsWith("status=")) {
                String status = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(status)) && (!status.startsWith("$status")) && (FormatUtil.isValiditySQL(status))) {
                    con_map.put("status", status);
                }
            }
            if (tempA[i].toLowerCase().startsWith("orderby=")) {
                String o_by = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(o_by)) && (!o_by.startsWith("$orderby")) && (FormatUtil.isValiditySQL(o_by))) {
                    orderby = o_by;
                }
            }
            if (tempA[i].toLowerCase().startsWith("size=")) {
                String ps = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(ps)) && (!ps.startsWith("$size")) && (FormatUtil.isNumeric(ps)))
                    page_size = Integer.parseInt(ps);
            }
            if (tempA[i].toLowerCase().startsWith("cur_page=")) {
                String cp = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cp)) && (!cp.startsWith("$cur_page")) && (FormatUtil.isNumeric(cp)))
                    cur_page = Integer.parseInt(cp);
            }
        }
        con_map.put("page_size", page_size + "");
        con_map.put("current_page", cur_page + "");
        con_map.put("orderby", orderby);
    }

    public static TurnPageBean getNzwzzCount(String params) {
        Map con_map = new HashMap();
        getNzwzzSearchCon(params, con_map);

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(NzwzzManager.getNzwzzCount(con_map)));
        tpb.setCur_page(cur_page);
        tpb.setPage_size(page_size);
        tpb.setPage_count(tpb.getCount() / tpb.getPage_size() + 1);

        if ((tpb.getCount() % tpb.getPage_size() == 0) && (tpb.getPage_count() > 1)) {
            tpb.setPage_count(tpb.getPage_count() - 1);
        }
        if (cur_page > 1) {
            tpb.setPrev_num(cur_page - 1);
        }
        tpb.setNext_num(tpb.getPage_count());
        if (cur_page < tpb.getPage_count()) {
            tpb.setNext_num(cur_page + 1);
        }

        if (tpb.getPage_count() > 10) {
            if (cur_page > 5) {
                if (cur_page > tpb.getPage_count() - 4)
                    tpb.setCurr_start_num(tpb.getPage_count() - 6);
                else
                    tpb.setCurr_start_num(cur_page - 2);
            }
        }
        return tpb;
    }

    public static List<NzwzzBean> getNzwzzList(String params) {
        Map con_map = new HashMap();
        getNzwzzSearchCon(params, con_map);
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return NzwzzManager.getNzwzzList(con_map);
    }

    public static List<NzwzzBean> getNzwzzHotList(String params) {
        Map con_map = new HashMap();
        getNzwzzSearchCon(params, con_map);
        con_map.put("current_page", "1");
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return NzwzzManager.getNzwzzList(con_map);
    }

    public static NzwzzBean getNzwzzObject(String id) {
        return NzwzzManager.getNzwzzBean(id);
    }
}