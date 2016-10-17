package com.deya.project.searchInfo;


import com.deya.util.FormatUtil;
import com.deya.wcm.bean.template.TurnPageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SlgyData {
    private static int cur_page = 1;
    private static int page_size = 15;

    public static void getSlgySearchCon(String params, Map<String, String> con_map) {
        String orderby = "id desc";
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++) {
            if (tempA[i].toLowerCase().startsWith("kw=")) {
                String kw = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(kw)) && (!kw.startsWith("$kw")) && (FormatUtil.isValiditySQL(kw))) {
                    con_map.put("keyword", kw);
                }
            }
            if (tempA[i].toLowerCase().startsWith("gymc=")) {
                String gymc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(gymc)) && (!gymc.startsWith("$gymc")) && (FormatUtil.isValiditySQL(gymc))) {
                    con_map.put("gymc", gymc);
                }
            }
            if (tempA[i].toLowerCase().startsWith("zdmj=")) {
                String zdmj = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(zdmj)) && (!zdmj.startsWith("$zdmj")) && (FormatUtil.isValiditySQL(zdmj))) {
                    con_map.put("zdmj", zdmj);
                }
            }
            if (tempA[i].toLowerCase().startsWith("gyjb=")) {
                String gyjb = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(gyjb)) && (!gyjb.startsWith("$gyjb")) && (FormatUtil.isValiditySQL(gyjb))) {
                    con_map.put("gyjb", gyjb);
                }
            }
            if (tempA[i].toLowerCase().startsWith("zgbmmc=")) {
                String zgbmmc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(zgbmmc)) && (!zgbmmc.startsWith("$zgbmmc")) && (FormatUtil.isValiditySQL(zgbmmc))) {
                    con_map.put("zgbmmc", zgbmmc);
                }
            }
            if (tempA[i].toLowerCase().startsWith("gydz=")) {
                String gydz = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(gydz)) && (!gydz.startsWith("$gydz")) && (FormatUtil.isValiditySQL(gydz))) {
                    con_map.put("gydz", gydz);
                }
            }
            if (tempA[i].toLowerCase().startsWith("lxdh=")) {
                String lxdh = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(lxdh)) && (!lxdh.startsWith("$lxdh")) && (FormatUtil.isValiditySQL(lxdh))) {
                    con_map.put("lxdh", lxdh);
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

    public static TurnPageBean getSlgyCount(String params) {
        Map con_map = new HashMap();
        getSlgySearchCon(params, con_map);

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(SlgyManager.getSlgyCount(con_map)));
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

    public static List<SlgyBean> getSlgyList(String params) {
        Map con_map = new HashMap();
        getSlgySearchCon(params, con_map);
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return SlgyManager.getSlgyList(con_map);
    }

    public static List<SlgyBean> getSlgyHotList(String params) {
        Map con_map = new HashMap();
        getSlgySearchCon(params, con_map);
        con_map.put("current_page", "1");
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return SlgyManager.getSlgyList(con_map);
    }

    public static SlgyBean getSlgyObject(String id) {
        return SlgyManager.getSlgyBean(id);
    }
}