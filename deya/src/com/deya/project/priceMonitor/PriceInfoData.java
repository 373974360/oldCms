package com.deya.project.priceMonitor;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.cms.info.InfoBean;
import com.deya.wcm.bean.template.TurnPageBean;
import com.deya.wcm.services.cms.info.InfoBaseManager;
import com.deya.wcm.services.cms.info.ModelUtil;
import com.deya.wcm.services.system.formodel.ModelManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceInfoData
{
    public static List<PriceInfoBean> getPriceInfoList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getPriceInfoSearchCon(params, con_map);
        return PriceInfoManager.getPriceInfoList(con_map);
    }

    public static TurnPageBean getPriceInfoCount(String params)
    {
        Map<String, String> con_map = new HashMap();
        getPriceInfoSearchCon(params, con_map);

        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(Integer.parseInt(PriceInfoManager.getPriceInfoCount(con_map)));
        int cur_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));

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
                if (cur_page > tpb.getPage_count() - 4) {
                    tpb.setCurr_start_num(tpb.getPage_count() - 6);
                } else {
                    tpb.setCurr_start_num(cur_page - 2);
                }
            }
        }
        return tpb;
    }

    public static Object getInfoObject(String info_id)
    {
        InfoBean ib = InfoBaseManager.getInfoById(info_id);
        if (ib != null) {
            return ModelUtil.select(info_id, ib.getSite_id(), ModelManager.getModelEName(ib.getModel_id()));
        }
        return null;
    }

    public static void getPriceInfoSearchCon(String params, Map<String, String> con_map)
    {
        int cur_page = 1;
        int page_size = 15;
        String orderby = "id desc";
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("orderby="))
            {
                String o_by = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(o_by)) && (!o_by.startsWith("$orderby")) && (FormatUtil.isValiditySQL(o_by)))
                {
                    orderby = o_by;
                    con_map.put("orderby", orderby);
                }
            }
            if (tempA[i].toLowerCase().startsWith("marketid="))
            {
                String marketId = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(marketId)) && (!marketId.startsWith("$marketId")) && (FormatUtil.isValiditySQL(marketId))) {
                    con_map.put("marketId", marketId);
                }
            }
            if (tempA[i].toLowerCase().startsWith("issell="))
            {
                String isSell = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(isSell)) && (!isSell.startsWith("$isSell")) && (FormatUtil.isValiditySQL(isSell))) {
                    con_map.put("isSell", isSell);
                } else {
                    con_map.put("isSell", "0");
                }
            }
            if (tempA[i].toLowerCase().startsWith("typeid="))
            {
                String typeId = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(typeId)) && (!typeId.startsWith("$typeId")) && (FormatUtil.isValiditySQL(typeId))) {
                    con_map.put("typeId", typeId);
                }
            }
            if (tempA[i].toLowerCase().startsWith("productid="))
            {
                String productId = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(productId)) && (!productId.startsWith("$productId")) && (FormatUtil.isValiditySQL(productId))) {
                    con_map.put("productId", productId);
                }
            }
            if (tempA[i].toLowerCase().startsWith("adduser="))
            {
                String addUser = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(addUser)) && (!addUser.startsWith("$addUser")) && (FormatUtil.isValiditySQL(addUser))) {
                    con_map.put("addUser", addUser);
                }
            }
            if (tempA[i].toLowerCase().startsWith("starttime="))
            {
                String startTime = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(startTime)) && (!startTime.startsWith("$startTime")) && (FormatUtil.isValiditySQL(startTime))) {
                    con_map.put("startTime", startTime);
                }
            }
            if (tempA[i].toLowerCase().startsWith("endtime="))
            {
                String endTime = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(endTime)) && (!endTime.startsWith("$endTime")) && (FormatUtil.isValiditySQL(endTime))) {
                    con_map.put("endTime", endTime);
                }
            }
            if (tempA[i].toLowerCase().startsWith("status="))
            {
                String status = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(status)) && (!status.startsWith("$status")) && (FormatUtil.isValiditySQL(status))) {
                    con_map.put("status", status);
                }
            }
            if (tempA[i].toLowerCase().startsWith("cur_page="))
            {
                String cur_pageStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cur_pageStr)) && (!cur_pageStr.startsWith("$cur_page"))) {
                    cur_page = Integer.parseInt(cur_pageStr);
                }
            }
            if (tempA[i].toLowerCase().startsWith("size="))
            {
                String sizeStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(sizeStr)) && (!sizeStr.startsWith("$size"))) {
                    page_size = Integer.parseInt(sizeStr);
                }
            }
        }
        con_map.put("start_num", (cur_page - 1) * page_size + "");
        con_map.put("page_size", page_size + "");
        con_map.put("current_page", cur_page + "");
    }

    public static void getPriceCollectSearchCon(String params, Map<String, String> con_map)
    {
        int cur_page = 1;
        int page_size = 15;
        String orderby = "id desc";
        String[] tempA = params.split(";");
        for (int i = 0; i < tempA.length; i++)
        {
            if (tempA[i].toLowerCase().startsWith("date="))
            {
                String date = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(date)) && (!date.startsWith("$date")) && (FormatUtil.isValiditySQL(date))) {
                    con_map.put("date", date);
                } else {
                    con_map.put("date", DateUtil.getCurrentDate());
                }
            }
            if (tempA[i].toLowerCase().startsWith("marketid="))
            {
                String marketId = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(marketId)) && (!marketId.startsWith("$marketId")) && (FormatUtil.isValiditySQL(marketId))) {
                    con_map.put("marketId", marketId);
                } else {
                    con_map.put("marketId", "1");
                }
            }
            if (tempA[i].toLowerCase().startsWith("issell="))
            {
                String isSell = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(isSell)) && (!isSell.startsWith("$isSell")) && (FormatUtil.isValiditySQL(isSell))) {
                    con_map.put("isSell", isSell);
                } else {
                    con_map.put("isSell", "0");
                }
            }
            if (tempA[i].toLowerCase().startsWith("cur_page="))
            {
                String cur_pageStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(cur_pageStr)) && (!cur_pageStr.startsWith("$cur_page"))) {
                    cur_page = Integer.parseInt(cur_pageStr);
                }
            }
            if (tempA[i].toLowerCase().startsWith("size="))
            {
                String sizeStr = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(sizeStr)) && (!sizeStr.startsWith("$size"))) {
                    page_size = Integer.parseInt(sizeStr);
                }
            }
            if (tempA[i].toLowerCase().startsWith("orderby="))
            {
                String o_by = FormatUtil.formatNullString(tempA[i].substring(tempA[i].indexOf("=") + 1));
                if ((!"".equals(o_by)) && (!o_by.startsWith("$orderby")) && (FormatUtil.isValiditySQL(o_by)))
                {
                    orderby = o_by;
                    con_map.put("orderby", orderby);
                }
            }
        }
        con_map.put("start_num", (cur_page - 1) * page_size + "");
        con_map.put("page_size", page_size + "");
        con_map.put("current_page", cur_page + "");
    }

    public static TurnPageBean getPriceCollectCount(String params)
    {
        Map<String, String> con_map = new HashMap();
        getPriceCollectSearchCon(params, con_map);

        List<PriceCollectBean> result = DayCollectManager.getPriceCollectList(con_map);
        TurnPageBean tpb = new TurnPageBean();
        tpb.setCount(result.size());
        int cur_page = Integer.parseInt((String)con_map.get("current_page"));
        int page_size = Integer.parseInt((String)con_map.get("page_size"));

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
                if (cur_page > tpb.getPage_count() - 4) {
                    tpb.setCurr_start_num(tpb.getPage_count() - 6);
                } else {
                    tpb.setCurr_start_num(cur_page - 2);
                }
            }
        }
        return tpb;
    }

    public static List<PriceCollectBean> getPriceCollectList(String params)
    {
        Map<String, String> con_map = new HashMap();
        getPriceCollectSearchCon(params, con_map);
        int start_num = Integer.parseInt((String)con_map.get("start_num"));
        int page_size = Integer.parseInt((String)con_map.get("page_size")) + start_num;
        List<PriceCollectBean> result = DayCollectManager.getPriceCollectList(con_map);
        List<PriceCollectBean> returnResult = new ArrayList();
        for (int i = start_num; (i < result.size()) && (i < page_size); i++) {
            returnResult.add(result.get(i));
        }
        return returnResult;
    }

    public static void main(String[] args) {}
}
