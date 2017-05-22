package com.deya.project.dz_house;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.db.DBManager;

public class LouPanDAO {
	public static String getLouPanCount(Map<String,String> map) {
        return DBManager.getString("getLouPanCount", map);
    }
	
	public static List<LouPanBean> getLouPanAllList() {
        return DBManager.queryFList("getLouPanAllList",null);
    }
	
	public static List<LouPanBean> getLouPanList(Map<String,Object> map) {
        return DBManager.queryFList("getLouPanList",map);
    }
	
	public static boolean insertLouPan(LouPanBean loupan){
		loupan.setLpcode(getLouPanMaxCode());
		return DBManager.insert("insertLouPan", loupan);
	}
	
	public static LouPanBean getLouPanById(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return (LouPanBean) DBManager.queryFObj("getLouPanById",map);
	}
	
	public static boolean updateLouPan(LouPanBean loupan){
		return DBManager.update("updateLouPan", loupan);
	}
	
	public static int deleteLouPan(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		LouPanBean lp = getLouPanById(id);
		if(lp!=null){
			List<LouYuBean> list = LouYuManager.getLouyuListByCode(lp.getLpcode());
			if(!list.isEmpty()){
				return -1;//该楼盘下有设置楼宇信息删除失败
			}else{
				if(DBManager.delete("deleteLouPan", map)){
					return 1;//该楼盘下有设置楼宇信息删除失败
				}
			}
		}
		return 0;//楼盘信息不存在
	}
	
	public static String getLouPanTreeList(){
		List<LouPanBean> list = getLouPanAllList();
		String jsonStr = "[{\"id\":\"0\",\"text\":\"楼盘信息\",\"children\":[";
		if(!list.isEmpty()){
			for (LouPanBean loupan:list) {
				jsonStr+="{\"id\":\""+loupan.getLpcode()+"\",\"text\":\""+loupan.getName()+"\"},";
			}
		}
		jsonStr += "]}]";
		return jsonStr;
	}

	public static String getLouPanMaxCode(){
		String code = "";
		String maxCode = DBManager.getString("getLouPanMaxCode",null);
		if(maxCode==""&&maxCode.length()==0){
			maxCode="00";
		}
		String a = Integer.parseInt(maxCode)+1+"";
		if(a.length()<2){
			code="0"+a;
		}else{
			code=a;
		}
		return code;
	}

	public static List<Map<String,String>> getCountList(){
		return DBManager.queryFList("getCountList",null);
	}
}
