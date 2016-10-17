package com.deya.wcm.dataCollection.services;

import java.util.HashMap;
import java.util.Map;

public class CaijiBean {
    public static Map<String, String> map = new HashMap();

    public static void putMapString(String key, String state) {
        map.put(key, state);
    }

    public static String getMapString(String key) {
        return (String) map.get(key);
    }

    public static void removeMapString(String key) {
        map.remove(key);
    }
}
