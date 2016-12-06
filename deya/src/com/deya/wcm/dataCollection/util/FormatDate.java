package com.deya.wcm.dataCollection.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatDate {
    private static String TIME_TYPE_1 = "\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}\\d{2}";
    private static String TIME_TYPE_2 = "\\d{4}\\d{2}\\d{2}\\d{2}\\d{2}";

    private static String TIME_TYPE_3 = "\\d{4}\\d{2}\\d{2}";

    public static String forDateStrType(String dateStr) {
        char[] c = dateStr.toCharArray();
        for (int i = 0; i < c.length; i++) {
            int num = c[i];
            if ((num >= 19968) && (num <= 171941))
                c[i] = ' ';
            else if (c[i] == '/')
                c[i] = ' ';
            else if (c[i] == '-') {
                c[i] = ' ';
            }
        }
        String newStr = new String(c);
        newStr = newStr.replaceAll("\\s|:", "");
        return newStr;
    }

    public static String formatTime(String dateStr, String datetimeformat) {
        if (FormatString.strIsNull(dateStr)) {


            Date date = null;
            try {
                date = DateUtils.parseDate(dateStr, datetimeformat);
                date = DateUtils.truncate(date, Calendar.DATE);
                return DateFormatUtils.format(date, "yyyy-MM-dd");
            } catch (ParseException e) {
                e.printStackTrace();
            }

//
//
//
//            String s = forDateStrType(dateStr);
//
//            Pattern p = Pattern.compile(TIME_TYPE_1);
//            Matcher m = p.matcher(s);
//            if (m.matches()) {
//                String year = s.substring(0, 4);
//                String month = s.substring(4, 6);
//                String day = s.substring(6, 8);
//                String hh = s.substring(8, 10);
//                String mm = s.substring(10, 12);
//                String ss = s.substring(12, 14);
//                s = year + "-" + month + "-" + day + " " + hh + ":" + mm + ":" + ss;
//            }
//
//            Pattern p2 = Pattern.compile(TIME_TYPE_2);
//            Matcher m2 = p2.matcher(s);
//            if (m2.matches()) {
//                String year = s.substring(0, 4);
//                String month = s.substring(4, 6);
//                String day = s.substring(6, 8);
//                String hh = s.substring(8, 10);
//                String mm = s.substring(10, 12);
//                s = year + "-" + month + "-" + day + " " + hh + ":" + mm + ":00";
//            }
//
//            Pattern p3 = Pattern.compile(TIME_TYPE_3);
//            Matcher m3 = p3.matcher(s);
//            if (m3.matches()) {
//                String year = s.substring(0, 4);
//                String month = s.substring(4, 6);
//                String day = s.substring(6, 8);
//                s = year + "-" + month + "-" + day + " 00:00:00";
//            }
//
//            return s;
        }
        return "";
    }

    public static void main(String[] args) throws ParseException {
//        formatTime("201409051406");
        String str = "发布： 时间：2016/10/21 点击：42";
//        System.out.println(str.regionMatches());
        Date date = DateUtils.parseDate(str, "发布： 时间：yyyy/MM/dd 点击：ss");
        date = DateUtils.truncate(date, Calendar.DATE);
        System.out.println(DateFormatUtils.format(date, "yyyy-MM-dd"));
    }
}