package com.yinhai.restful;

import java.util.HashMap;
import java.util.Map;

public class CategoryMap {

    public static Map<String, String> cdgjjCatMap = new HashMap<String, String>();
    public static Map<String, String> cdgjjCatSourceMap = new HashMap<String, String>();

    static {

//        栏目映射
        cdgjjCatMap.put("C001", "10001");
        cdgjjCatMap.put("C002", "10005");
        cdgjjCatMap.put("C003", "10021");
        cdgjjCatMap.put("C004", "10025");
        cdgjjCatMap.put("C005", "10005");


//        渠道映射
        cdgjjCatSourceMap.put("03", "app");
        cdgjjCatSourceMap.put("04", "pc");
        cdgjjCatSourceMap.put("05", "wx");
    }

    /**
     * 转换参数为内部的栏目id，如果无法转换则直接返回参数
     *
     * @param colid
     * @return
     */
    public static String getCidByColid(String colid) {
        String s = cdgjjCatMap.get(colid);
        if (s == null) {
            System.out.println("没有[" + colid + "]对应的栏目");
            return colid;
        }
        return s;
    }

    /**
     * 转换参数为内部的渠道id，如果无法转换则直接返回参数
     *
     * @param value
     * @return
     */
    public static String getCategorySourceBySourceParamValue(String value) {

        String s = cdgjjCatSourceMap.get(value);
        if (s == null) {
            System.out.println(s + " 没有[" + value + "]对应的渠道");
        }
        return s;
    }
}
