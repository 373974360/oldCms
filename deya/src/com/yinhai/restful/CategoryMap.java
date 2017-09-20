package com.yinhai.restful;

import java.util.HashMap;
import java.util.Map;

public class CategoryMap {
    public static Map<String, String> cdgjjCatMap = new HashMap<String, String>();

    static {
        cdgjjCatMap.put("C001", "10001");
        cdgjjCatMap.put("C002", "10005");
        cdgjjCatMap.put("C003", "10021");
        cdgjjCatMap.put("C004", "10025");
        cdgjjCatMap.put("C005", "10005");
    }

    public static String getCidByColid(String colid){
        String s = cdgjjCatMap.get(colid);
        if (s == null) {
            System.out.println(s + " 没有对应的栏目");
            return colid;
        }
        return s;
    }
}
