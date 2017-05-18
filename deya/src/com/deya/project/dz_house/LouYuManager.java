package com.deya.project.dz_house;

import java.util.List;
import java.util.Map;

public class LouYuManager {
	public static String getLouYuCount(String lpcode,String search){
		return LouYuDAO.getLouYuCount(lpcode,search);
	}
	public static List<LouYuBean> getLouyuList(Map<String,Object> map){
		return LouYuDAO.getLouyuList(map);
	}
	public static List<LouYuBean> getLouyuListByCode(String lpcode){
		return LouYuDAO.getLouyuListByCode(lpcode);
	}
	public static boolean insertLouYu(LouYuBean louyu){
		return LouYuDAO.insertLouYu(louyu);
	}
	public static boolean updateLouYu(LouYuBean louyu){
		return LouYuDAO.updateLouYu(louyu);
	}
	public static boolean deleteLouYu(String id){
		return LouYuDAO.deleteLouYu(id);
	}
	public static LouYuBean getLouYuById(String id){
		return LouYuDAO.getLouYuById(id);
	}
	public static String getLouYuTreeList(){
		return LouYuDAO.getLouYuTreeList();
	}
}
