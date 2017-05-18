package com.deya.project.dz_house;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LouPanRPC {
    public static String getLouPanCount(String name) {
        return LouPanManager.getLouPanCount(name);
    }

    public static List<LouPanBean> getLouPanList(String con, int start_num, int page_size) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("name", con);
        m.put("start_num", start_num);
        m.put("page_size", page_size);
        return LouPanManager.getLouPanList(m);
    }

    public static List<LouPanBean> getLouPanAllList() {
        return LouPanManager.getLouPanAllList();
    }

    public static boolean insertLouPan(LouPanBean loupan) {
        return LouPanManager.insertLouPan(loupan);
    }

    public static LouPanBean getLouPanById(String id) {
        return LouPanManager.getLouPanById(id);
    }

    public static boolean updateLouPan(LouPanBean loupan) {
        return LouPanManager.updateLouPan(loupan);
    }

    public static List<LouPanBean> getLouPanList() {
        return LouPanManager.getLouPanAllList();
    }

    public static boolean deleteLouPan(String id) {
        return LouPanManager.deleteLouPan(id);
    }
    public static String getLouPanTreeList(){
        return LouPanManager.getLouPanTreeList();
    }
}
