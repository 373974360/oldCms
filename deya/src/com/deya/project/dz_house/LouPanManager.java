package com.deya.project.dz_house;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;

public class LouPanManager {
	public static String getLouPanCount(String name) {
		if(name!=null&&name.length()>0){
			if (!FormatUtil.isValiditySQL(name))
                return "0";
		}
        return LouPanDAO.getLouPanCount(name);
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
	public static boolean deleteLouPan(String id){
		return LouPanDAO.deleteLouPan(id);
	}
	public static String getLouPanTreeList(){
		return LouPanDAO.getLouPanTreeList();
	}
}
