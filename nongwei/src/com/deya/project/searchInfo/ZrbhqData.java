package com.deya.project.searchInfo;


import com.deya.util.FormatUtil;
import com.deya.wcm.bean.template.TurnPageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZrbhqData {
    private static int cur_page = 1;
    private static int page_size = 15;

    public static void getZrbhqSearchCon(String params, Map<String, String> con_map) {
        String orderby = "id desc";
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++) {
            if (tempA[i].toLowerCase().startsWith("kw=")) {
                String kw = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(kw)) && (!kw.startsWith("$kw")) && (FormatUtil.isValiditySQL(kw))) {
                    con_map.put("keyword", kw);
                }
            }
            if (tempA[i].toLowerCase().startsWith("bhqmc=")) {
                String bhqmc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(bhqmc)) && (!bhqmc.startsWith("$bhqmc")) && (FormatUtil.isValiditySQL(bhqmc))) {
                    con_map.put("bhqmc", bhqmc);
                }
            }
            if (tempA[i].toLowerCase().startsWith("jb=")) {
                String jb = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(jb)) && (!jb.startsWith("$jb")) && (FormatUtil.isValiditySQL(jb))) {
                    con_map.put("jb", jb);
                }
            }
            if (tempA[i].toLowerCase().startsWith("wz=")) {
                String wz = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(wz)) && (!wz.startsWith("$wz")) && (FormatUtil.isValiditySQL(wz))) {
                    con_map.put("wz", wz);
                }
            }
            if (tempA[i].toLowerCase().startsWith("mj=")) {
                String mj = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(mj)) && (!mj.startsWith("$mj")) && (FormatUtil.isValiditySQL(mj))) {
                    con_map.put("mj", mj);
                }
            }
            if (tempA[i].toLowerCase().startsWith("jj=")) {
                String jj = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(jj)) && (!jj.startsWith("$jj")) && (FormatUtil.isValiditySQL(jj))) {
                    con_map.put("jj", jj);
                }
            }
            if (tempA[i].toLowerCase().startsWith("mj=")) {
                String mj = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(mj)) && (!mj.startsWith("$mj")) && (FormatUtil.isValiditySQL(mj))) {
                    con_map.put("mj", mj);
                }
            }
            if (tempA[i].toLowerCase().startsWith("lxdh=")) {
                String lxdh = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(lxdh)) && (!lxdh.startsWith("$lxdh")) && (FormatUtil.isValiditySQL(lxdh))) {
                    con_map.put("lxdh", lxdh);
                }
            }
            if (tempA[i].toLowerCase().startsWith("txdz=")) {
                String txdz = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(txdz)) && (!txdz.startsWith("$txdz")) && (FormatUtil.isValiditySQL(txdz))) {
                    con_map.put("txdz", txdz);
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

    public static TurnPageBean getZrbhqCount(String params) {
        Map con_map = new HashMap();
        getZrbhqSearchCon(params, con_map);

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(ZrbhqManager.getZrbhqCount(con_map)));
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

    public static List<ZrbhqBean> getZrbhqList(String params) {
        Map con_map = new HashMap();
        getZrbhqSearchCon(params, con_map);
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return ZrbhqManager.getZrbhqList(con_map);
    }

    public static List<ZrbhqBean> getZrbhqHotList(String params) {
        Map con_map = new HashMap();
        getZrbhqSearchCon(params, con_map);
        con_map.put("current_page", "1");
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return ZrbhqManager.getZrbhqList(con_map);
    }

    public static ZrbhqBean getZrbhqObject(String id) {
        return ZrbhqManager.getZrbhqBean(id);
    }
}