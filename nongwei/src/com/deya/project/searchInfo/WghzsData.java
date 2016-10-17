package com.deya.project.searchInfo;


import com.deya.util.FormatUtil;
import com.deya.wcm.bean.template.TurnPageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WghzsData {
    private static int cur_page = 1;
    private static int page_size = 15;

    public static void getWghzsSearchCon(String params, Map<String, String> con_map) {
        String orderby = "id desc";
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++) {
            if (tempA[i].toLowerCase().startsWith("kw=")) {
                String kw = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(kw)) && (!kw.startsWith("$kw")) && (FormatUtil.isValiditySQL(kw))) {
                    con_map.put("keyword", kw);
                }
            }
            if (tempA[i].toLowerCase().startsWith("sqrqc=")) {
                String sqrqc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(sqrqc)) && (!sqrqc.startsWith("$sqrqc")) && (FormatUtil.isValiditySQL(sqrqc))) {
                    con_map.put("sqrqc", sqrqc);
                }
            }
            if (tempA[i].toLowerCase().startsWith("cpmc=")) {
                String cpmc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(cpmc)) && (!cpmc.startsWith("$cpmc")) && (FormatUtil.isValiditySQL(cpmc))) {
                    con_map.put("cpmc", cpmc);
                }
            }
            if (tempA[i].toLowerCase().startsWith("shb=")) {
                String shb = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(shb)) && (!shb.startsWith("$shb")) && (FormatUtil.isValiditySQL(shb))) {
                    con_map.put("shb", shb);
                }
            }
            if (tempA[i].toLowerCase().startsWith("ncl=")) {
                String ncl = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(ncl)) && (!ncl.startsWith("$ncl")) && (FormatUtil.isValiditySQL(ncl))) {
                    con_map.put("ncl", ncl);
                }
            }
            if (tempA[i].toLowerCase().startsWith("zsbh=")) {
                String zsbh = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(zsbh)) && (!zsbh.startsWith("$zsbh")) && (FormatUtil.isValiditySQL(zsbh))) {
                    con_map.put("zsbh", zsbh);
                }
            }
            if (tempA[i].toLowerCase().startsWith("zsyxq=")) {
                String zsyxq = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(zsyxq)) && (!zsyxq.startsWith("$zsyxq")) && (FormatUtil.isValiditySQL(zsyxq))) {
                    con_map.put("zsyxq", zsyxq);
                }
            }
            if (tempA[i].toLowerCase().startsWith("sflq=")) {
                String sflq = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(sflq)) && (!sflq.startsWith("$sflq")) && (FormatUtil.isValiditySQL(sflq))) {
                    con_map.put("sflq", sflq);
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

    public static TurnPageBean getWghzsCount(String params) {
        Map con_map = new HashMap();
        getWghzsSearchCon(params, con_map);

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(WghzsManager.getWghzsCount(con_map)));
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

    public static List<WghzsBean> getWghzsList(String params) {
        Map con_map = new HashMap();
        getWghzsSearchCon(params, con_map);
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return WghzsManager.getWghzsList(con_map);
    }

    public static List<WghzsBean> getWghzsHotList(String params) {
        Map con_map = new HashMap();
        getWghzsSearchCon(params, con_map);
        con_map.put("current_page", "1");
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return WghzsManager.getWghzsList(con_map);
    }

    public static WghzsBean getWghzsObject(String id) {
        return WghzsManager.getWghzsBean(id);
    }
}