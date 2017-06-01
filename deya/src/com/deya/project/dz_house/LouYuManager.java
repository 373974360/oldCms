package com.deya.project.dz_house;

import java.util.List;
import java.util.Map;

public class LouYuManager {
	public static String getLouYuCount(Map<String,Object> map){
		return LouYuDAO.getLouYuCount(map);
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
	public static int deleteLouYu(String id){
		return LouYuDAO.deleteLouYu(id);
	}
	public static LouYuBean getLouYuById(String id){
		return LouYuDAO.getLouYuById(id);
	}
	public static String getLouYuTreeList(){
		return LouYuDAO.getLouYuTreeList();
	}
	public static LouYuBean getFirstLouYuByCode(String code){
		return LouYuDAO.getFirstLouYuByCode(code);
	}
	public static LouYuBean getLouYuByCode(String code){
		return LouYuDAO.getLouYuByCode(code);
	}
}
