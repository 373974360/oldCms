package com.deya.project.dz_house;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LouYuRPC {

	public static String getLouYuCount(String lpcode,String search){
		return LouYuManager.getLouYuCount(lpcode,search);
	}
	public static List<LouYuBean> getLouyuList(String search,String lpcode,int start_num,int page_size){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("search", search);
		m.put("lpcode", lpcode);
		m.put("start_num",start_num);
		m.put("page_size", page_size);
		return LouYuManager.getLouyuList(m);
	}
	public static List<LouYuBean> getLouyuListByCode(String lpcode){
		return LouYuManager.getLouyuListByCode(lpcode);
	}
	public static boolean insertLouYu(LouYuBean louyu){
		return LouYuManager.insertLouYu(louyu);
	}
	public static boolean updateLouYu(LouYuBean louyu){
		return LouYuManager.updateLouYu(louyu);
	}
	public static boolean deleteLouYu(String id){
		return LouYuManager.deleteLouYu(id);
	}
	public static LouYuBean getLouYuById(String id){
		return LouYuManager.getLouYuById(id);
	}
	public static String getLouYuTreeList(){
		return LouYuManager.getLouYuTreeList();
	}
}
