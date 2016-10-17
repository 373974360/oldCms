package com.deya.project.priceMonitor;

import com.deya.util.DateUtil;
import com.deya.util.FormatUtil;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.org.user.UserManager;

import java.text.ParseException;
import java.util.*;

public class DayCollectManager {

    public static boolean collectByDay(String date)
    {
        Map<String,String> m = new HashMap<String,String>();
        m.put("collectDate",date);
        return DBManager.update("deleteByDay",m) && DBManager.insert("collectByDay", m) ;
    }

    public static Map<Integer,List<DayCollectBean>> getDayCollect(String date)
    {
        Map<String,DayCollectData> lastMap = new HashMap<String,DayCollectData>();

        Map<String,String> m = new HashMap<String,String>();
        m.put("marketId","1");
        for(int i = 1; i < 8; i++){
            String lastDay = DateUtil.getDateBefore(date,i);
            m.remove("collectDate");
            m.put("collectDate",lastDay);
            List<DayCollectData> lastList = DayCollectDAO.getDayCollectList(m);
            if(lastList != null && lastList.size() > 0){
                for(DayCollectData pb : lastList)
                {
                    String temp = pb.getProductId() + "-" + pb.getIsSell();
                    lastMap.put(temp,pb);
                }
                break;
            }
        }
        Map<String,DayCollectData> todayMap = new HashMap<String,DayCollectData>();
        m.remove("collectDate");
        m.put("collectDate",date);
        List<DayCollectData> todayList = DayCollectDAO.getDayCollectList(m);
        if(todayList != null && todayList.size() > 0)
        {
            for(DayCollectData pb : todayList)
            {
                String temp = pb.getProductId() + "-" + pb.getIsSell();
                todayMap.put(temp,pb);
            }
        }

        Map<Integer,List<DayCollectBean>> result = new HashMap<Integer,List<DayCollectBean>>();

        List<ProductTypeBean> ptList = ProductTypeManager.getProductTypeByMarketId("1");
        if(ptList != null && ptList.size() > 0)
        {
            for(ProductTypeBean ptb : ptList)
            {
                List<DayCollectBean> dcbList = new ArrayList<DayCollectBean>();
                List<ProductBean> pdbList = ProductManager.getAllProductListByTypeId(ptb.getId() + "");
                if(pdbList != null && pdbList.size() > 0)
                {
                    for(ProductBean pdb : pdbList)
                    {
                        if("".equals(pdb.getComments()) || pdb.getComments() == null){
                            DayCollectBean dcb = new DayCollectBean();
                            dcb.setTypeId(pdb.getTypeId());
                            dcb.setProductId(pdb.getId());
                            if(lastMap.get(pdb.getId() + "-0") != null)
                            {
                                dcb.setLastPrice(String.format("%.2f", Double.parseDouble(lastMap.get(pdb.getId() + "-0").getPrice()+"")));
                            }
                            else {
                                dcb.setLastPrice("0.00");
                            }
                            if(lastMap.get(pdb.getId() + "-1") != null)
                            {
                                dcb.setLastSellPrice(String.format("%.2f", Double.parseDouble(lastMap.get(pdb.getId() + "-1").getPrice()+"")));
                            }
                            else {
                                dcb.setLastSellPrice("0.00");
                            }
                            if(todayMap.get(pdb.getId() + "-0") != null)
                            {
                                dcb.setTodayPrice(String.format("%.2f", Double.parseDouble(todayMap.get(pdb.getId() + "-0").getPrice()+"")));
                            }
                            else {
                                dcb.setTodayPrice("0.00");
                            }
                            if(todayMap.get(pdb.getId() + "-1") != null)
                            {
                                dcb.setTodaySellPrice(String.format("%.2f", Double.parseDouble(todayMap.get(pdb.getId() + "-1").getPrice()+"")));
                            }
                            else {
                                dcb.setTodaySellPrice("0.00");
                            }
                            if(dcb.getLastPrice() != null && !"0.00".equals(dcb.getLastPrice()))
                            {
                                String rose = (Double.parseDouble(dcb.getTodayPrice()) - Double.parseDouble(dcb.getLastPrice())) / Double.parseDouble(dcb.getLastPrice()) * 100 + "";
                                rose = String.format("%.2f", Double.parseDouble(rose));
                                dcb.setTodayRose(rose);
                            }
                            else {
                                dcb.setTodayRose("-");
                            }
                            if(dcb.getLastSellPrice() != null && !"0.00".equals(dcb.getLastSellPrice()))
                            {
                                String rose = (Double.parseDouble(dcb.getTodaySellPrice()) - Double.parseDouble(dcb.getLastSellPrice())) / Double.parseDouble(dcb.getLastSellPrice()) * 100 + "";
                                rose = String.format("%.2f", Double.parseDouble(rose));
                                dcb.setTodaySellRose(rose);
                            }
                            else {
                                dcb.setTodaySellRose("-");
                            }
                            dcb.setTypeName(pdb.getTypeName());
                            dcb.setProductName(pdb.getProductName());
                            dcbList.add(dcb);
                        }
                    }
                }
                result.put(ptb.getId(),dcbList);
            }
        }
        return result;
    }

    public static List<WeekCollectBean> getWeekCollect(Map<String,String> m)
    {
        Map<Integer,DayCollectData> lastMap = new HashMap<Integer,DayCollectData>();
        String lastDateStart = m.get("lastDateStart");
        String lastDateEnd = m.get("lastDateEnd");
        String thisDateStart = m.get("thisDateStart");
        String thisDateEnd = m.get("thisDateEnd");
        m.clear();
        m.put("marketId","1");
        m.put("collectDateStart",lastDateStart);
        m.put("collectDateEnd",lastDateEnd);
        List<DayCollectData> lastList = DayCollectDAO.getWeekCollectList(m);
        if(lastList != null && lastList.size() > 0)
        {
            for(DayCollectData pb : lastList)
            {
                int temp = pb.getProductId();
                lastMap.put(temp,pb);
            }
        }
        Map<Integer,DayCollectData> thisMap = new HashMap<Integer,DayCollectData>();
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart",thisDateStart);
        m.put("collectDateEnd",thisDateEnd);
        List<DayCollectData> todayList = DayCollectDAO.getWeekCollectList(m);
        if(todayList != null && todayList.size() > 0)
        {
            for(DayCollectData pb : todayList)
            {
                int temp = pb.getProductId();
                thisMap.put(temp,pb);
            }
        }

        List<WeekCollectBean> result = new ArrayList<WeekCollectBean>();

        List<ProductBean> pdbList = ProductManager.getAllProductListByTypeId("3");
        if(pdbList != null && pdbList.size() > 0)
        {
            for(ProductBean pdb : pdbList)
            {
                WeekCollectBean wcb = new WeekCollectBean();
                wcb.setProductId(pdb.getId());
                if(lastMap.get(pdb.getId()) != null)
                {
                    wcb.setLastPrice(String.format("%.2f", Double.parseDouble(lastMap.get(pdb.getId()).getPrice()+"")));
                }
                else {
                    wcb.setLastPrice("0.00");
                }

                if(thisMap.get(pdb.getId()) != null)
                {
                    wcb.setThisPrice(String.format("%.2f", Double.parseDouble(thisMap.get(pdb.getId()).getPrice()+"")));
                }
                else {
                    wcb.setThisPrice("0.00");
                }
                if(wcb.getLastPrice() != null && !"0.00".equals(wcb.getLastPrice()))
                {
                    String rose = (Double.parseDouble(wcb.getThisPrice()) - Double.parseDouble(wcb.getLastPrice())) / Double.parseDouble(wcb.getLastPrice()) * 100 + "";
                    rose = String.format("%.2f", Double.parseDouble(rose));
                    wcb.setRose(rose);
                }else {
                    wcb.setRose("-");
                }

                wcb.setProductName(pdb.getProductName());
                result.add(wcb);
            }
        }
        return result;
    }

    public static Map<String,List<TenCollectBean>> getTenCollectList(Map<String,String> m)
    {
        Map<String,TenCollectBean> lastMap = new HashMap<String,TenCollectBean>();
        String lastDateStart = m.get("lastDateStart");
        String lastDateEnd = m.get("lastDateEnd");
        String thisDateStart = m.get("thisDateStart");
        String thisDateEnd = m.get("thisDateEnd");
        String lastYearStart = "";
        String lastYearEnd = "";
        try {
            Date thisDateStartDate = DateUtil.getDate(thisDateStart, "yyyy-MM-dd");
            int year = DateUtil.getYear(thisDateStartDate) - 1;
            lastYearStart = year + thisDateStart.substring(4,10);
            lastYearEnd = year + thisDateEnd.substring(4,10);

        } catch (Exception e){
            e.printStackTrace();
        }
        m.clear();
        m.put("marketId","1");
        m.put("collectDateStart",lastDateStart);
        m.put("collectDateEnd",lastDateEnd);
        List<TenCollectBean> lastList = DayCollectDAO.getTenCollectList(m);
        if(lastList != null && lastList.size() > 0)
        {
            for(TenCollectBean pb : lastList)
            {
                String temp = pb.getProductId() + "-" + pb.getIsSell();
                lastMap.put(temp,pb);
            }
        }
        Map<String,TenCollectBean> lastYearMap = new HashMap<String,TenCollectBean>();
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart",lastYearStart);
        m.put("collectDateEnd",lastYearEnd);
        List<TenCollectBean> lastYearList = DayCollectDAO.getTenCollectList(m);
        if(lastYearList != null && lastYearList.size() > 0)
        {
            for(TenCollectBean pb : lastYearList)
            {
                String temp = pb.getProductId() + "-" + pb.getIsSell();
                lastYearMap.put(temp, pb);
            }
        }
        Map<String,TenCollectBean> thisMap = new HashMap<String,TenCollectBean>();
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart",thisDateStart);
        m.put("collectDateEnd",thisDateEnd);
        List<TenCollectBean> todayList = DayCollectDAO.getTenCollectList(m);
        if(todayList != null && todayList.size() > 0)
        {
            for(TenCollectBean pb : todayList)
            {
                String temp = pb.getProductId() + "-" + pb.getIsSell();
                thisMap.put(temp,pb);
            }
        }

        List<TenCollectBean> pfResult = new ArrayList<TenCollectBean>();
        List<TenCollectBean> csResult = new ArrayList<TenCollectBean>();

        Map<String,List<TenCollectBean>> result = new HashMap<String,List<TenCollectBean>>();
        List<ProductBean> pdbList = ProductManager.getAllProductListByMarketId("1");
        if(pdbList != null && pdbList.size() > 0)
        {
            for(ProductBean pdb : pdbList)
            {
                float thisAvg = 0;
                float lastAvg = 0;
                float lastYearAvg = 0;
                TenCollectBean tcb = new TenCollectBean();
                tcb.setTypeId(pdb.getTypeId());
                tcb.setProductId(pdb.getId());
                tcb.setTypeName(pdb.getTypeName());
                tcb.setProductName(pdb.getProductName());
                TenCollectBean tb = thisMap.get(pdb.getId()+ "-0");
                if( tb != null)
                {
                    tcb.setIsSell(0);
                    tcb.setMaxPrice(String.format("%.2f", Double.parseDouble(tb.getMaxPrice())));
                    tcb.setMinPrice(String.format("%.2f", Double.parseDouble(tb.getMinPrice())));
                    tcb.setAvgPrice(String.format("%.2f", Double.parseDouble(tb.getAvgPrice())));
                    thisAvg = Float.parseFloat(tb.getAvgPrice());
                    if(lastMap.get(pdb.getId()+ "-0") != null)
                    {
                        lastAvg = Float.parseFloat(lastMap.get(pdb.getId()+ "-0").getAvgPrice());
                    }
                    else{
                        lastAvg = 0;
                    }
                    if(lastYearMap.get(pdb.getId()+ "-0") != null)
                    {
                        lastYearAvg = Float.parseFloat(lastYearMap.get(pdb.getId()+ "-0").getAvgPrice());
                    }
                    else{
                        lastYearAvg = 0;
                    }
                    if(lastAvg != 0)
                    {
                        String rose = (thisAvg - lastAvg) / lastAvg  * 100 + "";
                        rose = String.format("%.2f", Double.parseDouble(rose));
                        tcb.setRose(rose);
                    }else{
                        tcb.setRose("-");
                    }
                    if(lastYearAvg != 0)
                    {
                        String chain = (thisAvg - lastYearAvg) / lastYearAvg * 100 + "";
                        chain = String.format("%.2f", Double.parseDouble(chain));
                        tcb.setChain(chain);
                    }else{
                        tcb.setChain("-");
                    }
                }else {
                    tcb.setIsSell(0);
                    tcb.setMaxPrice("0.00");
                    tcb.setMinPrice("0.00");
                    tcb.setAvgPrice("0.00");
                    tcb.setRose("-");
                    tcb.setChain("-");
                }
                pfResult.add(tcb);
            }

            for(ProductBean pdb : pdbList)
            {
                float thisAvg = 0;
                float lastAvg = 0;
                float lastYearAvg = 0;
                TenCollectBean tcb = new TenCollectBean();
                tcb.setTypeId(pdb.getTypeId());
                tcb.setProductId(pdb.getId());
                tcb.setTypeName(pdb.getTypeName());
                tcb.setProductName(pdb.getProductName());
                TenCollectBean tb = thisMap.get(pdb.getId()+ "-1");
                if(tb != null)
                {
                    tcb.setIsSell(1);
                    tcb.setMaxPrice(String.format("%.2f", Double.parseDouble(tb.getMaxPrice())));
                    tcb.setMinPrice(String.format("%.2f", Double.parseDouble(tb.getMinPrice())));
                    tcb.setAvgPrice(String.format("%.2f", Double.parseDouble(tb.getAvgPrice())));
                    thisAvg = Float.parseFloat(tb.getAvgPrice());
                    if(lastMap.get(pdb.getId()+ "-1") != null)
                    {
                        lastAvg = Float.parseFloat(lastMap.get(pdb.getId()+ "-1").getAvgPrice());
                    }
                    else{
                        lastAvg = 0;
                    }
                    if(lastYearMap.get(pdb.getId()+ "-1") != null)
                    {
                        lastYearAvg = Float.parseFloat(lastYearMap.get(pdb.getId()+ "-1").getAvgPrice());
                    }else{
                        lastYearAvg = 0;
                    }
                    if(lastAvg != 0)
                    {
                        String rose = (thisAvg - lastAvg) / lastAvg * 100 + "";
                        rose = String.format("%.2f", Double.parseDouble(rose));
                        tcb.setRose(rose);
                    }else{
                        tcb.setRose("-");
                    }
                    if(lastYearAvg != 0)
                    {
                        String chain = (thisAvg - lastYearAvg) / lastYearAvg * 100 + "";
                        chain = String.format("%.2f", Double.parseDouble(chain));
                        tcb.setChain(chain);
                    }else{
                        tcb.setChain("-");
                    }
                }else {
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
        result.put("0",pfResult);
        result.put("1",csResult);
        return result;
    }

    public static List<PriceCollectBean> getPriceCollectList(Map<String,String> m)
    {
        String selectDate = m.get("date");
        String marketId = m.get("marketId");
        String isSell = m.get("isSell");
        String thisWeekDate = DateUtil.getDateBefore(selectDate,7);
        String lastWeekDate = DateUtil.getDateBefore(selectDate,14);
        String thisMonthDate = DateUtil.getDateBefore(selectDate,30);
        String lastMonthDate = DateUtil.getDateBefore(selectDate,60);
        String lastYearDate = "";
        try {
            Date date = DateUtil.getDate(selectDate,"yyyy-MM-dd");
            int year = DateUtil.getYear(date) - 1;
            lastYearDate = year + selectDate.substring(4,10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        m.put("marketId","1");
        m.put("collectDateStart",selectDate);
        m.put("collectDateEnd",selectDate);
        Map<Integer,PriceCollectBean> selectMap = new HashMap<Integer,PriceCollectBean>();
        List<PriceCollectBean> selectList = DayCollectDAO.getPriceCollectList(m);
        if(selectList != null && selectList.size() > 0)
        {
            for(PriceCollectBean pb : selectList)
            {
                int temp = pb.getProductId();
                selectMap.put(temp,pb);
            }
        }
        Map<Integer,PriceCollectBean> lastDayMap = new HashMap<Integer,PriceCollectBean>();
        for(int i = 1; i < 8; i++){
            String lastDate = DateUtil.getDateBefore(selectDate,i);
            m.remove("collectDateStart");
            m.remove("collectDateEnd");
            m.put("collectDateStart",lastDate);
            m.put("collectDateEnd",lastDate);
            List<PriceCollectBean> lastDayList = DayCollectDAO.getPriceCollectList(m);
            if(lastDayList != null && lastDayList.size() > 0)
            {
                for(PriceCollectBean pb : lastDayList)
                {
                    int temp = pb.getProductId();
                    lastDayMap.put(temp,pb);
                }
                break;
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart",thisWeekDate);
        m.put("collectDateEnd",selectDate);
        Map<Integer,PriceCollectBean> thisWeekMap = new HashMap<Integer,PriceCollectBean>();
        List<PriceCollectBean> thisWeekList = DayCollectDAO.getPriceCollectList(m);
        if(thisWeekList != null && thisWeekList.size() > 0)
        {
            for(PriceCollectBean pb : thisWeekList)
            {
                int temp = pb.getProductId();
                thisWeekMap.put(temp,pb);
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart",lastWeekDate);
        m.put("collectDateEnd",thisWeekDate);
        Map<Integer,PriceCollectBean> lastWeekMap = new HashMap<Integer,PriceCollectBean>();
        List<PriceCollectBean> lastWeekList = DayCollectDAO.getPriceCollectList(m);
        if(lastWeekList != null && lastWeekList.size() > 0)
        {
            for(PriceCollectBean pb : lastWeekList)
            {
                int temp = pb.getProductId();
                lastWeekMap.put(temp,pb);
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart",thisMonthDate);
        m.put("collectDateEnd",selectDate);
        Map<Integer,PriceCollectBean> thisMonthMap = new HashMap<Integer,PriceCollectBean>();
        List<PriceCollectBean> thisMonthList = DayCollectDAO.getPriceCollectList(m);
        if(thisMonthList != null && thisMonthList.size() > 0)
        {
            for(PriceCollectBean pb : thisMonthList)
            {
                int temp = pb.getProductId();
                thisMonthMap.put(temp,pb);
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart",lastMonthDate);
        m.put("collectDateEnd",thisMonthDate);
        Map<Integer,PriceCollectBean> lastMonthMap = new HashMap<Integer,PriceCollectBean>();
        List<PriceCollectBean> lastMonthList = DayCollectDAO.getPriceCollectList(m);
        if(lastMonthList != null && lastMonthList.size() > 0)
        {
            for(PriceCollectBean pb : lastMonthList)
            {
                int temp = pb.getProductId();
                lastMonthMap.put(temp,pb);
            }
        }
        m.remove("collectDateStart");
        m.remove("collectDateEnd");
        m.put("collectDateStart",lastYearDate);
        m.put("collectDateEnd",lastYearDate);
        Map<Integer,PriceCollectBean> lastYearMap = new HashMap<Integer,PriceCollectBean>();
        List<PriceCollectBean> lastYearList = DayCollectDAO.getPriceCollectList(m);
        if(lastYearList != null && lastYearList.size() > 0)
        {
            for(PriceCollectBean pb : lastYearList)
            {
                int temp = pb.getProductId();
                lastYearMap.put(temp,pb);
            }
        }

        List<PriceCollectBean> result = new ArrayList<PriceCollectBean>();

        List<ProductBean> pdbList = ProductManager.getAllProductListByMarketId(marketId);
        if(pdbList != null && pdbList.size() > 0)
        {
            for(ProductBean pdb : pdbList)
            {
                float selectAvg = 0;
                float lastDayAvg = 0;
                float thisWeekAvg = 0;
                float lastWeekAvg = 0;
                float thisMonthAvg = 0;
                float lastMonthAvg = 0;
                float lastYearAvg = 0;
                PriceCollectBean pcb = new PriceCollectBean();

                pcb.setTypeId(pdb.getTypeId());
                pcb.setProductId(pdb.getId());
                pcb.setTypeName(pdb.getTypeName());
                pcb.setProductName(pdb.getProductName());
                if(selectMap.get(pdb.getId()) != null)
                {
                    pcb.setDayAvgPrice(String.format("%.2f", Double.parseDouble(selectMap.get(pdb.getId()).getAvgPrice())));
                    selectAvg = Float.parseFloat(selectMap.get(pdb.getId()).getAvgPrice());
                }
                else {
                    pcb.setDayAvgPrice("0.00");
                }
                if(lastDayMap.get(pdb.getId()) != null)
                {
                    lastDayAvg = Float.parseFloat(lastDayMap.get(pdb.getId()).getAvgPrice());
                }
                if(thisWeekMap.get(pdb.getId()) != null)
                {
                    pcb.setWeekAvgPrice(String.format("%.2f", Double.parseDouble(thisWeekMap.get(pdb.getId()).getAvgPrice())));
                    thisWeekAvg = Float.parseFloat(thisWeekMap.get(pdb.getId()).getAvgPrice());
                }
                else {
                    pcb.setWeekAvgPrice("0.00");
                }
                if(lastWeekMap.get(pdb.getId()) != null)
                {
                    lastWeekAvg = Float.parseFloat(lastWeekMap.get(pdb.getId()).getAvgPrice());
                }
                if(thisMonthMap.get(pdb.getId()) != null)
                {
                    pcb.setMonthAvgPrice(String.format("%.2f", Double.parseDouble(thisMonthMap.get(pdb.getId()).getAvgPrice())));
                    thisMonthAvg = Float.parseFloat(thisMonthMap.get(pdb.getId()).getAvgPrice());
                }
                else {
                    pcb.setMonthAvgPrice("0.00");
                }
                if(lastMonthMap.get(pdb.getId()) != null)
                {
                    lastMonthAvg = Float.parseFloat(lastMonthMap.get(pdb.getId()).getAvgPrice());
                }
                if(lastYearMap.get(pdb.getId()) != null)
                {
                    lastYearAvg = Float.parseFloat(lastYearMap.get(pdb.getId()).getAvgPrice());
                }
                if(lastDayAvg != 0)
                {
                    String rose = (selectAvg - lastDayAvg) / lastDayAvg * 100 + "";
                    rose = String.format("%.2f", Double.parseDouble(rose));
                    pcb.setDayRose(rose + "%");
                }
                else{
                    pcb.setDayRose("-");
                }

                if(lastWeekAvg != 0)
                {
                    String rose = (thisWeekAvg - lastWeekAvg) / lastWeekAvg * 100 + "";
                    rose = String.format("%.2f", Double.parseDouble(rose));
                    pcb.setWeekRose(rose + "%");
                }
                else{
                    pcb.setWeekRose("-");
                }
                if(lastMonthAvg != 0)
                {
                    String rose = (thisMonthAvg - lastMonthAvg) / lastMonthAvg * 100 + "";
                    rose = String.format("%.2f", Double.parseDouble(rose));
                    pcb.setMonthRose(rose + "%");
                }
                else{
                    pcb.setMonthRose("-");
                }
                if(lastYearAvg != 0)
                {
                    String chain = (selectAvg - lastYearAvg) / lastYearAvg * 100 + "";
                    chain = String.format("%.2f", Double.parseDouble(chain));
                    pcb.setChain(chain + "%");
                }
                else{
                    pcb.setChain("-");
                }
                result.add(pcb);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        //System.out.println(DateUtil.getDateBefore("2015-12-10",1));
        //System.out.println();
        System.out.println("2015-10-23".substring(3,10));
    }

}