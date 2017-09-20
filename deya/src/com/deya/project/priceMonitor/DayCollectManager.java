package com.deya.project.priceMonitor;

import com.deya.util.DateUtil;
import com.deya.wcm.db.DBManager;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayCollectManager
{
    public static boolean collectByDay(String date)
    {
        Map<String, String> m = new HashMap();
        m.put("collectDate", date);
        return (DBManager.update("deleteByDay", m)) && (DBManager.insert("collectByDay", m));
    }

    public static Map<Integer, List<DayCollectBean>> getDayCollect(String date)
    {
        Map<String, DayCollectData> lastMap = new HashMap();

        Map<String, String> m = new HashMap();
        m.put("marketId", "1");
        List<DayCollectData> lastList;
        for (int i = 1; i < 8; i++)
        {
            String lastDay = DateUtil.getDateBefore(date, i);
            m.remove("collectDate");
            m.put("collectDate", lastDay);
            lastList = DayCollectDAO.getDayCollectList(m);
            if ((lastList != null) && (lastList.size() > 0))
            {
                for (DayCollectData pb : lastList)
                {
                    String temp = pb.getProductId() + "-" + pb.getIsSell();
                    lastMap.put(temp, pb);
                }
                break;
            }
        }
        Map<String, DayCollectData> todayMap = new HashMap();
        m.remove("collectDate");
        m.put("collectDate", date);
        List<DayCollectData> todayList = DayCollectDAO.getDayCollectList(m);
        if ((todayList != null) && (todayList.size() > 0)) {
            for (DayCollectData pb : todayList)
            {
                todayMap.put(pb.getProductId() + "-" + pb.getIsSell(), pb);
            }
        }
        String temp;
        Map<Integer, List<DayCollectBean>> result = new HashMap();

        List<ProductTypeBean> ptList = ProductTypeManager.getProductTypeByMarketId("1");
        if ((ptList != null) && ptList.size() > 0) {
            for (ProductTypeBean ptb : ptList)
            {
                List<DayCollectBean> dcbList = new ArrayList();
                List<ProductBean> pdbList = ProductManager.getAllProductListByTypeId(ptb.getId() + "");
                if ((pdbList != null) && (pdbList.size() > 0)) {
                    for (ProductBean pdb : pdbList) {
                        if (("".equals(pdb.getComments())) || (pdb.getComments() == null))
                        {
                            DayCollectBean dcb = new DayCollectBean();
                            dcb.setTypeId(pdb.getTypeId());
                            dcb.setProductId(pdb.getId());
                            if (lastMap.get(pdb.getId() + "-0") != null) {
                                dcb.setLastPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(((DayCollectData)lastMap.get(new StringBuilder().append(pdb.getId()).append("-0").toString())).getPrice() + "")) }));
                            } else {
                                dcb.setLastPrice("0.00");
                            }
                            if (lastMap.get(pdb.getId() + "-1") != null) {
                                dcb.setLastSellPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(((DayCollectData)lastMap.get(new StringBuilder().append(pdb.getId()).append("-1").toString())).getPrice() + "")) }));
                            } else {
                                dcb.setLastSellPrice("0.00");
                            }
                            if (todayMap.get(pdb.getId() + "-0") != null) {
                                dcb.setTodayPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(((DayCollectData)todayMap.get(new StringBuilder().append(pdb.getId()).append("-0").toString())).getPrice() + "")) }));
                            } else {
                                dcb.setTodayPrice("0.00");
                            }
                            if (todayMap.get(pdb.getId() + "-1") != null) {
                                dcb.setTodaySellPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(((DayCollectData)todayMap.get(new StringBuilder().append(pdb.getId()).append("-1").toString())).getPrice() + "")) }));
                            } else {
                                dcb.setTodaySellPrice("0.00");
                            }
                            if ((dcb.getLastPrice() != null) && (!"0.00".equals(dcb.getLastPrice())))
                            {
                                String rose = (Double.parseDouble(dcb.getTodayPrice()) - Double.parseDouble(dcb.getLastPrice())) / Double.parseDouble(dcb.getLastPrice()) * 100.0D + "";
                                rose = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(rose)) });
                                dcb.setTodayRose(rose);
                            }
                            else
                            {
                                dcb.setTodayRose("-");
                            }
                            if ((dcb.getLastSellPrice() != null) && (!"0.00".equals(dcb.getLastSellPrice())))
                            {
                                String rose = (Double.parseDouble(dcb.getTodaySellPrice()) - Double.parseDouble(dcb.getLastSellPrice())) / Double.parseDouble(dcb.getLastSellPrice()) * 100.0D + "";
                                rose = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(rose)) });
                                dcb.setTodaySellRose(rose);
                            }
                            else
                            {
                                dcb.setTodaySellRose("-");
                            }
                            dcb.setTypeName(pdb.getTypeName());
                            dcb.setProductName(pdb.getProductName());
                            dcbList.add(dcb);
                        }
                    }
                }
                result.put(Integer.valueOf(ptb.getId()), dcbList);
            }
        }
        return result;
    }

    public static List<WeekCollectBean> getWeekCollect(Map<String, String> m)
    {
        Map<Integer, DayCollectData> lastMap = new HashMap();
        String lastDateStart = (String)m.get("lastDateStart");
        String lastDateEnd = (String)m.get("lastDateEnd");
        String thisDateStart = (String)m.get("thisDateStart");
        String thisDateEnd = (String)m.get("thisDateEnd");
        m.clear();
        m.put("marketId", "1");
        m.put("collectDateStart", lastDateStart);
        m.put("collectDateEnd", lastDateEnd);
        List<DayCollectData> lastList = DayCollectDAO.getWeekCollectList(m);
        if ((lastList != null) && (lastList.size() > 0)) {
            for (DayCollectData pb : lastList)
            {
                lastMap.put(Integer.valueOf(pb.getProductId()), pb);
            }
        }
        int temp;
        Object thisMap = new HashMap();
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart", thisDateStart);
        m.put("collectDateEnd", thisDateEnd);
        List<DayCollectData> todayList = DayCollectDAO.getWeekCollectList(m);
        if ((todayList != null) && (todayList.size() > 0)) {
            for (DayCollectData pb : todayList)
            {
                temp = pb.getProductId();
                ((Map)thisMap).put(Integer.valueOf(temp), pb);
            }
        }
        List<WeekCollectBean> result = new ArrayList();

        List<ProductBean> pdbList = ProductManager.getAllProductListByTypeId("3");
        if ((pdbList != null) && (pdbList.size() > 0)) {
            for (ProductBean pdb : pdbList)
            {
                WeekCollectBean wcb = new WeekCollectBean();
                wcb.setProductId(pdb.getId());
                if (lastMap.get(Integer.valueOf(pdb.getId())) != null) {
                    wcb.setLastPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(((DayCollectData)lastMap.get(Integer.valueOf(pdb.getId()))).getPrice() + "")) }));
                } else {
                    wcb.setLastPrice("0.00");
                }
                if (((Map)thisMap).get(Integer.valueOf(pdb.getId())) != null) {
                    wcb.setThisPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(((DayCollectData)((Map)thisMap).get(Integer.valueOf(pdb.getId()))).getPrice() + "")) }));
                } else {
                    wcb.setThisPrice("0.00");
                }
                if ((wcb.getLastPrice() != null) && (!"0.00".equals(wcb.getLastPrice())))
                {
                    String rose = (Double.parseDouble(wcb.getThisPrice()) - Double.parseDouble(wcb.getLastPrice())) / Double.parseDouble(wcb.getLastPrice()) * 100.0D + "";
                    rose = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(rose)) });
                    wcb.setRose(rose);
                }
                else
                {
                    wcb.setRose("-");
                }
                wcb.setProductName(pdb.getProductName());
                result.add(wcb);
            }
        }
        return result;
    }

    public static Map<String, List<TenCollectBean>> getTenCollectList(Map<String, String> m)
    {
        Map<String, TenCollectBean> lastMap = new HashMap();
        String lastDateStart = (String)m.get("lastDateStart");
        String lastDateEnd = (String)m.get("lastDateEnd");
        String thisDateStart = (String)m.get("thisDateStart");
        String thisDateEnd = (String)m.get("thisDateEnd");
        String lastYearStart = "";
        String lastYearEnd = "";
        int year;
        try
        {
            Date thisDateStartDate = DateUtil.getDate(thisDateStart, "yyyy-MM-dd");
            year = DateUtil.getYear(thisDateStartDate) - 1;
            lastYearStart = year + thisDateStart.substring(4, 10);
            lastYearEnd = year + thisDateEnd.substring(4, 10);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        m.clear();
        m.put("marketId", "1");
        m.put("collectDateStart", lastDateStart);
        m.put("collectDateEnd", lastDateEnd);
        List<TenCollectBean> lastList = DayCollectDAO.getTenCollectList(m);
        if ((lastList != null) && (lastList.size() > 0)) {
            for (TenCollectBean pb : lastList)
            {
                lastMap.put(pb.getProductId() + "-" + pb.getIsSell(), pb);
            }
        }
        Map<String, TenCollectBean> lastYearMap = new HashMap();
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart", lastYearStart);
        m.put("collectDateEnd", lastYearEnd);
        List<TenCollectBean> lastYearList = DayCollectDAO.getTenCollectList(m);
        if ((lastYearList != null) && (lastYearList.size() > 0)) {
            for (TenCollectBean pb : lastYearList)
            {
                lastYearMap.put(pb.getProductId() + "-" + pb.getIsSell(), pb);
            }
        }
        Map<String, TenCollectBean> thisMap = new HashMap();
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart", thisDateStart);
        m.put("collectDateEnd", thisDateEnd);
        List<TenCollectBean> todayList = DayCollectDAO.getTenCollectList(m);
        if ((todayList != null) && (todayList.size() > 0)) {
            for (TenCollectBean pb : todayList)
            {
                String temp = pb.getProductId() + "-" + pb.getIsSell();
                thisMap.put(temp, pb);
            }
        }
        List<TenCollectBean> pfResult = new ArrayList();
        List<TenCollectBean> csResult = new ArrayList();

        Map<String, List<TenCollectBean>> result = new HashMap();
        List<ProductBean> pdbList = ProductManager.getAllProductListByMarketId("1");
        if ((pdbList != null) && (pdbList.size() > 0))
        {
            for (ProductBean pdb : pdbList)
            {
                float thisAvg = 0.0F;
                float lastAvg = 0.0F;
                float lastYearAvg = 0.0F;
                TenCollectBean tcb = new TenCollectBean();
                tcb.setTypeId(pdb.getTypeId());
                tcb.setProductId(pdb.getId());
                tcb.setTypeName(pdb.getTypeName());
                tcb.setProductName(pdb.getProductName());
                TenCollectBean tb = (TenCollectBean)thisMap.get(pdb.getId() + "-0");
                if (tb != null)
                {
                    tcb.setIsSell(0);
                    tcb.setMaxPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(tb.getMaxPrice())) }));
                    tcb.setMinPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(tb.getMinPrice())) }));
                    tcb.setAvgPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(tb.getAvgPrice())) }));
                    thisAvg = Float.parseFloat(tb.getAvgPrice());
                    if (lastMap.get(pdb.getId() + "-0") != null) {
                        lastAvg = Float.parseFloat(((TenCollectBean)lastMap.get(pdb.getId() + "-0")).getAvgPrice());
                    } else {
                        lastAvg = 0.0F;
                    }
                    if (lastYearMap.get(pdb.getId() + "-0") != null) {
                        lastYearAvg = Float.parseFloat(((TenCollectBean)lastYearMap.get(pdb.getId() + "-0")).getAvgPrice());
                    } else {
                        lastYearAvg = 0.0F;
                    }
                    if (lastAvg != 0.0F)
                    {
                        String rose = (thisAvg - lastAvg) / lastAvg * 100.0F + "";
                        rose = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(rose)) });
                        tcb.setRose(rose);
                    }
                    else
                    {
                        tcb.setRose("-");
                    }
                    if (lastYearAvg != 0.0F)
                    {
                        String chain = (thisAvg - lastYearAvg) / lastYearAvg * 100.0F + "";
                        chain = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(chain)) });
                        tcb.setChain(chain);
                    }
                    else
                    {
                        tcb.setChain("-");
                    }
                }
                else
                {
                    tcb.setIsSell(0);
                    tcb.setMaxPrice("0.00");
                    tcb.setMinPrice("0.00");
                    tcb.setAvgPrice("0.00");
                    tcb.setRose("-");
                    tcb.setChain("-");
                }
                pfResult.add(tcb);
            }
            for (ProductBean pdb : pdbList)
            {
                float thisAvg = 0.0F;
                float lastAvg = 0.0F;
                float lastYearAvg = 0.0F;
                TenCollectBean tcb = new TenCollectBean();
                tcb.setTypeId(pdb.getTypeId());
                tcb.setProductId(pdb.getId());
                tcb.setTypeName(pdb.getTypeName());
                tcb.setProductName(pdb.getProductName());
                TenCollectBean tb = (TenCollectBean)thisMap.get(pdb.getId() + "-1");
                if (tb != null)
                {
                    tcb.setIsSell(1);
                    tcb.setMaxPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(tb.getMaxPrice())) }));
                    tcb.setMinPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(tb.getMinPrice())) }));
                    tcb.setAvgPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(tb.getAvgPrice())) }));
                    thisAvg = Float.parseFloat(tb.getAvgPrice());
                    if (lastMap.get(pdb.getId() + "-1") != null) {
                        lastAvg = Float.parseFloat(((TenCollectBean)lastMap.get(pdb.getId() + "-1")).getAvgPrice());
                    } else {
                        lastAvg = 0.0F;
                    }
                    if (lastYearMap.get(pdb.getId() + "-1") != null) {
                        lastYearAvg = Float.parseFloat(((TenCollectBean)lastYearMap.get(pdb.getId() + "-1")).getAvgPrice());
                    } else {
                        lastYearAvg = 0.0F;
                    }
                    if (lastAvg != 0.0F)
                    {
                        String rose = (thisAvg - lastAvg) / lastAvg * 100.0F + "";
                        rose = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(rose)) });
                        tcb.setRose(rose);
                    }
                    else
                    {
                        tcb.setRose("-");
                    }
                    if (lastYearAvg != 0.0F)
                    {
                        String chain = (thisAvg - lastYearAvg) / lastYearAvg * 100.0F + "";
                        chain = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(chain)) });
                        tcb.setChain(chain);
                    }
                    else
                    {
                        tcb.setChain("-");
                    }
                }
                else
                {
                    tcb.setIsSell(1);
                    tcb.setMaxPrice("0.00");
                    tcb.setMinPrice("0.00");
                    tcb.setAvgPrice("0.00");
                    tcb.setRose("-");
                    tcb.setChain("-");
                }
                csResult.add(tcb);
            }
        }
        result.put("0", pfResult);
        result.put("1", csResult);
        return result;
    }

    public static List<PriceCollectBean> getPriceCollectList(Map<String, String> m)
    {
        String selectDate = (String)m.get("date");
        String marketId = (String)m.get("marketId");
        String isSell = (String)m.get("isSell");
        String thisWeekDate = DateUtil.getDateBefore(selectDate, 7);
        String lastWeekDate = DateUtil.getDateBefore(selectDate, 14);
        String thisMonthDate = DateUtil.getDateBefore(selectDate, 30);
        String lastMonthDate = DateUtil.getDateBefore(selectDate, 60);
        String lastYearDate = "";
        try
        {
            Date date = DateUtil.getDate(selectDate, "yyyy-MM-dd");
            int year = DateUtil.getYear(date) - 1;
            lastYearDate = year + selectDate.substring(4, 10);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        m.put("marketId", "1");
        m.put("collectDateStart", selectDate);
        m.put("collectDateEnd", selectDate);
        Map<Integer, PriceCollectBean> selectMap = new HashMap();
        List<PriceCollectBean> selectList = DayCollectDAO.getPriceCollectList(m);
        if ((selectList != null) && (selectList.size() > 0)) {
            for (PriceCollectBean pb : selectList)
            {
                int temp = pb.getProductId();
                selectMap.put(Integer.valueOf(temp), pb);
            }
        }
        Object lastDayMap = new HashMap();
        List<PriceCollectBean> lastDayList;
        for (int i = 1; i < 8; i++)
        {
            String lastDate = DateUtil.getDateBefore(selectDate, i);
            m.remove("collectDateStart");
            m.remove("collectDateEnd");
            m.put("collectDateStart", lastDate);
            m.put("collectDateEnd", lastDate);
            lastDayList = DayCollectDAO.getPriceCollectList(m);
            if ((lastDayList != null) && (lastDayList.size() > 0))
            {
                for (PriceCollectBean pb : lastDayList)
                {
                    int temp = pb.getProductId();
                    ((Map)lastDayMap).put(Integer.valueOf(temp), pb);
                }
                break;
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart", thisWeekDate);
        m.put("collectDateEnd", selectDate);
        Map<Integer, PriceCollectBean> thisWeekMap = new HashMap();
        List<PriceCollectBean> thisWeekList = DayCollectDAO.getPriceCollectList(m);
        if ((thisWeekList != null) && (thisWeekList.size() > 0)) {
            for (PriceCollectBean pb : thisWeekList)
            {
                thisWeekMap.put(Integer.valueOf(pb.getProductId()), pb);
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart", lastWeekDate);
        m.put("collectDateEnd", thisWeekDate);
        Map<Integer, PriceCollectBean> lastWeekMap = new HashMap();
        List<PriceCollectBean> lastWeekList = DayCollectDAO.getPriceCollectList(m);
        if ((lastWeekList != null) && lastWeekList.size() > 0) {
            for (PriceCollectBean pb : lastWeekList){
                lastWeekMap.put(Integer.valueOf(pb.getProductId()), pb);
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart", thisMonthDate);
        m.put("collectDateEnd", selectDate);
        Map<Integer, PriceCollectBean> thisMonthMap = new HashMap();
        List<PriceCollectBean> thisMonthList = DayCollectDAO.getPriceCollectList(m);
        if ((thisMonthList != null) && (thisMonthList.size() > 0)) {
            for (PriceCollectBean pb : thisMonthList)
            {
                thisMonthMap.put(Integer.valueOf(pb.getProductId()), pb);
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart", lastMonthDate);
        m.put("collectDateEnd", thisMonthDate);
        Map<Integer, PriceCollectBean> lastMonthMap = new HashMap();
        List<PriceCollectBean> lastMonthList = DayCollectDAO.getPriceCollectList(m);
        if ((lastMonthList != null) && (lastMonthList.size() > 0)) {
            for (PriceCollectBean pb : lastMonthList)
            {
                lastMonthMap.put(Integer.valueOf(pb.getProductId()), pb);
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart", lastYearDate);
        m.put("collectDateEnd", lastYearDate);
        Map<Integer, PriceCollectBean> lastYearMap = new HashMap();
        List<PriceCollectBean> lastYearList = DayCollectDAO.getPriceCollectList(m);
        if ((lastYearList != null) && (lastYearList.size() > 0)) {
            for (PriceCollectBean pb : lastYearList)
            {
                lastYearMap.put(Integer.valueOf(pb.getProductId()), pb);
            }
        }
        List<PriceCollectBean> result = new ArrayList();

        List<ProductBean> pdbList = ProductManager.getAllProductListByMarketId(marketId);
        if ((pdbList != null) && (pdbList.size() > 0)) {
            for (ProductBean pdb : pdbList)
            {
                float selectAvg = 0.0F;
                float lastDayAvg = 0.0F;
                float thisWeekAvg = 0.0F;
                float lastWeekAvg = 0.0F;
                float thisMonthAvg = 0.0F;
                float lastMonthAvg = 0.0F;
                float lastYearAvg = 0.0F;
                PriceCollectBean pcb = new PriceCollectBean();

                pcb.setTypeId(pdb.getTypeId());
                pcb.setProductId(pdb.getId());
                pcb.setTypeName(pdb.getTypeName());
                pcb.setProductName(pdb.getProductName());
                if (selectMap.get(Integer.valueOf(pdb.getId())) != null) {
                    pcb.setUnit(((PriceCollectBean)selectMap.get(Integer.valueOf(pdb.getId()))).getUnit());
                }
                if (selectMap.get(Integer.valueOf(pdb.getId())) != null)
                {
                    pcb.setDayAvgPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(((PriceCollectBean)selectMap.get(Integer.valueOf(pdb.getId()))).getAvgPrice())) }));
                    selectAvg = Float.parseFloat(((PriceCollectBean)selectMap.get(Integer.valueOf(pdb.getId()))).getAvgPrice());
                }
                else
                {
                    pcb.setDayAvgPrice("0.00");
                }
                if (((Map)lastDayMap).get(Integer.valueOf(pdb.getId())) != null) {
                    lastDayAvg = Float.parseFloat(((PriceCollectBean)((Map)lastDayMap).get(Integer.valueOf(pdb.getId()))).getAvgPrice());
                }
                if (thisWeekMap.get(Integer.valueOf(pdb.getId())) != null)
                {
                    pcb.setWeekAvgPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(((PriceCollectBean)thisWeekMap.get(Integer.valueOf(pdb.getId()))).getAvgPrice())) }));
                    thisWeekAvg = Float.parseFloat(((PriceCollectBean)thisWeekMap.get(Integer.valueOf(pdb.getId()))).getAvgPrice());
                }
                else
                {
                    pcb.setWeekAvgPrice("0.00");
                }
                if (lastWeekMap.get(Integer.valueOf(pdb.getId())) != null) {
                    lastWeekAvg = Float.parseFloat(((PriceCollectBean)lastWeekMap.get(Integer.valueOf(pdb.getId()))).getAvgPrice());
                }
                if (thisMonthMap.get(Integer.valueOf(pdb.getId())) != null)
                {
                    pcb.setMonthAvgPrice(String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(((PriceCollectBean)thisMonthMap.get(Integer.valueOf(pdb.getId()))).getAvgPrice())) }));
                    thisMonthAvg = Float.parseFloat(((PriceCollectBean)thisMonthMap.get(Integer.valueOf(pdb.getId()))).getAvgPrice());
                }
                else
                {
                    pcb.setMonthAvgPrice("0.00");
                }
                if (lastMonthMap.get(Integer.valueOf(pdb.getId())) != null) {
                    lastMonthAvg = Float.parseFloat(((PriceCollectBean)lastMonthMap.get(Integer.valueOf(pdb.getId()))).getAvgPrice());
                }
                if (lastYearMap.get(Integer.valueOf(pdb.getId())) != null) {
                    lastYearAvg = Float.parseFloat(((PriceCollectBean)lastYearMap.get(Integer.valueOf(pdb.getId()))).getAvgPrice());
                }
                if (lastDayAvg != 0.0F)
                {
                    String rose = (selectAvg - lastDayAvg) / lastDayAvg * 100.0F + "";
                    rose = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(rose)) });
                    pcb.setDayRose(rose + "%");
                }
                else
                {
                    pcb.setDayRose("-");
                }
                if (lastWeekAvg != 0.0F)
                {
                    String rose = (thisWeekAvg - lastWeekAvg) / lastWeekAvg * 100.0F + "";
                    rose = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(rose)) });
                    pcb.setWeekRose(rose + "%");
                }
                else
                {
                    pcb.setWeekRose("-");
                }
                if (lastMonthAvg != 0.0F)
                {
                    String rose = (thisMonthAvg - lastMonthAvg) / lastMonthAvg * 100.0F + "";
                    rose = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(rose)) });
                    pcb.setMonthRose(rose + "%");
                }
                else
                {
                    pcb.setMonthRose("-");
                }
                if (lastYearAvg != 0.0F)
                {
                    String chain = (selectAvg - lastYearAvg) / lastYearAvg * 100.0F + "";
                    chain = String.format("%.2f", new Object[] { Double.valueOf(Double.parseDouble(chain)) });
                    pcb.setChain(chain + "%");
                }
                else
                {
                    pcb.setChain("-");
                }
                result.add(pcb);
            }
        }
        return result;
    }

    public static void main(String[] args)
    {
        System.out.println("2015-10-23".substring(3, 10));
    }
}
