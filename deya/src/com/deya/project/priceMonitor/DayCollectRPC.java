package com.deya.project.priceMonitor;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class DayCollectRPC
{
    public static Map<Integer, List<DayCollectBean>> getDayCollect(String date)
    {
        return DayCollectManager.getDayCollect(date);
    }

    public static List<WeekCollectBean> getWeekCollect(Map<String, String> m)
    {
        return DayCollectManager.getWeekCollect(m);
    }

    public static Map<String, List<TenCollectBean>> getTenCollectList(Map<String, String> m)
    {
        return DayCollectManager.getTenCollectList(m);
    }

    public static List<PriceCollectBean> getPriceCollectList(Map<String, String> m)
    {
        return DayCollectManager.getPriceCollectList(m);
    }

    public static boolean collectByDay(String date)
    {
        return DayCollectManager.collectByDay(date);
    }

    public static void main(String[] args)
    {
        System.out.println("2015-12-10".substring(3, 10));
    }
}
