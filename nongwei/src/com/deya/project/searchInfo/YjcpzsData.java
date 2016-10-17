package com.deya.project.searchInfo;


import com.deya.util.FormatUtil;
import com.deya.wcm.bean.template.TurnPageBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class YjcpzsData {
    private static int cur_page = 1;
    private static int page_size = 15;

    public static void getYjcpzsSearchCon(String params, Map<String, String> con_map) {
        String orderby = "id desc";
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++) {
            if (tempA[i].toLowerCase().startsWith("kw=")) {
                String kw = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(kw)) && (!kw.startsWith("$kw")) && (FormatUtil.isValiditySQL(kw))) {
                    con_map.put("keyword", kw);
                }
            }
            if (tempA[i].toLowerCase().startsWith("cyrmc=")) {
                String cyrmc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(cyrmc)) && (!cyrmc.startsWith("$cyrmc")) && (FormatUtil.isValiditySQL(cyrmc))) {
                    con_map.put("cyrmc", cyrmc);
                }
            }
            if (tempA[i].toLowerCase().startsWith("dz=")) {
                String dz = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(dz)) && (!dz.startsWith("$dz")) && (FormatUtil.isValiditySQL(dz))) {
                    con_map.put("dz", dz);
                }
            }
            if (tempA[i].toLowerCase().startsWith("jdmc=")) {
                String jdmc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(jdmc)) && (!jdmc.startsWith("$jdmc")) && (FormatUtil.isValiditySQL(jdmc))) {
                    con_map.put("jdmc", jdmc);
                }
            }
            if (tempA[i].toLowerCase().startsWith("jddz=")) {
                String jddz = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(jddz)) && (!jddz.startsWith("$jddz")) && (FormatUtil.isValiditySQL(jddz))) {
                    con_map.put("jddz", jddz);
                }
            }
            if (tempA[i].toLowerCase().startsWith("mj=")) {
                String mj = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(mj)) && (!mj.startsWith("$mj")) && (FormatUtil.isValiditySQL(mj))) {
                    con_map.put("mj", mj);
                }
            }
            if (tempA[i].toLowerCase().startsWith("cpmc=")) {
                String cpmc = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(cpmc)) && (!cpmc.startsWith("$cpmc")) && (FormatUtil.isValiditySQL(cpmc))) {
                    con_map.put("cpmc", cpmc);
                }
            }
            if (tempA[i].toLowerCase().startsWith("cpms=")) {
                String cpms = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(cpms)) && (!cpms.startsWith("$cpms")) && (FormatUtil.isValiditySQL(cpms))) {
                    con_map.put("cpms", cpms);
                }
            }
            if (tempA[i].toLowerCase().startsWith("scgm=")) {
                String scgm = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(scgm)) && (!scgm.startsWith("$scgm")) && (FormatUtil.isValiditySQL(scgm))) {
                    con_map.put("scgm", scgm);
                }
            }
            if (tempA[i].toLowerCase().startsWith("cl=")) {
                String cl = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(cl)) && (!cl.startsWith("$cl")) && (FormatUtil.isValiditySQL(cl))) {
                    con_map.put("cl", cl);
                }
            }
            if (tempA[i].toLowerCase().startsWith("ccfzrq=")) {
                String ccfzrq = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(ccfzrq)) && (!ccfzrq.startsWith("$ccfzrq")) && (FormatUtil.isValiditySQL(ccfzrq))) {
                    con_map.put("ccfzrq", ccfzrq);
                }
            }
            if (tempA[i].toLowerCase().startsWith("bcfzrq=")) {
                String bcfzrq = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(bcfzrq)) && (!bcfzrq.startsWith("$bcfzrq")) && (FormatUtil.isValiditySQL(bcfzrq))) {
                    con_map.put("bcfzrq", bcfzrq);
                }
            }
            if (tempA[i].toLowerCase().startsWith("yxqx=")) {
                String yxqx = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));

                if ((!"".equals(yxqx)) && (!yxqx.startsWith("$yxqx")) && (FormatUtil.isValiditySQL(yxqx))) {
                    con_map.put("yxqx", yxqx);
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

    public static TurnPageBean getYjcpzsCount(String params) {
        Map con_map = new HashMap();
        getYjcpzsSearchCon(params, con_map);

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(YjcpzsManager.getYjcpzsCount(con_map)));
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

    public static List<YjcpzsBean> getYjcpzsList(String params) {
        Map con_map = new HashMap();
        getYjcpzsSearchCon(params, con_map);
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return YjcpzsManager.getYjcpzsList(con_map);
    }

    public static List<YjcpzsBean> getYjcpzsHotList(String params) {
        Map con_map = new HashMap();
        getYjcpzsSearchCon(params, con_map);
        con_map.put("current_page", "1");
        int start_page = Integer.parseInt((String) con_map.get("current_page"));
        int page_size = Integer.parseInt((String) con_map.get("page_size"));
        con_map.put("start_num", Integer.valueOf((start_page - 1) * page_size));
        con_map.put("page_size", Integer.valueOf(page_size));
        return YjcpzsManager.getYjcpzsList(con_map);
    }

    public static YjcpzsBean getYjcpzsObject(String id) {
        return YjcpzsManager.getYjcpzsBean(id);
    }
}