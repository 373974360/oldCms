package com.deya.project.dz_house;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;

public class LouPanManager {
	public static String getLouPanCount(Map<String,String> map) {
		if (map.containsKey("name")) {
			if (!FormatUtil.isValiditySQL((String) map.get("name")))
				return "0";
		}
        return LouPanDAO.getLouPanCount(map);
	}
	public static List<LouPanBean> getLouPanAllList() {
        return LouPanDAO.getLouPanAllList();
    }
	public static List<LouPanBean> getLouPanList(Map<String,Object> map) {
		if (map.containsKey("name")) {
            if (!FormatUtil.isValiditySQL((String) map.get("name")))
                return new ArrayList();
        }
        return LouPanDAO.getLouPanList(map);
    }
	public static boolean insertLouPan(LouPanBean loupan){
		return LouPanDAO.insertLouPan(loupan);
	}
	public static LouPanBean getLouPanById(String id){
		return LouPanDAO.getLouPanById(id);
	}
	public static boolean updateLouPan(LouPanBean loupan){
		return LouPanDAO.updateLouPan(loupan);
	}
	public static int deleteLouPan(String id){
		return LouPanDAO.deleteLouPan(id);
	}
	public static String getLouPanTreeList(){
		return LouPanDAO.getLouPanTreeList();
	}
	public static List<Map<String,String>> getCountList(){
		return LouPanDAO.getCountList();
	}
}
