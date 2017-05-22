package com.deya.project.dz_house;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.util.FormatUtil;
import com.deya.wcm.db.DBManager;

public class LouYuDAO {



	public static String getLouYuCount(Map<String,Object> map){
        return DBManager.getString("getLouYuCount", map);
	}
	
	public static List<LouYuBean> getLouyuList(Map<String,Object> map){
		if (map.containsKey("search")) {
            if (!FormatUtil.isValiditySQL((String) map.get("search")))
                return new ArrayList();
        }
		return DBManager.queryFList("getLouyuList", map);
	}
	
	public static List<LouYuBean> getLouyuListByCode(String lpcode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", lpcode);
		return DBManager.queryFList("getLouyuListByCode", map);
	}
	
	public static boolean insertLouYu(LouYuBean louyu){
		louyu.setLycode(getLouYuMaxCode(louyu.getLycode()));
		return DBManager.insert("insertLouYu", louyu);
	}
	public static boolean updateLouYu(LouYuBean louyu){
		return DBManager.update("updateLouYu", louyu);
	}
	public static int deleteLouYu(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		LouYuBean ly = getLouYuById(id);
		if(ly!=null){
			Map<String, String> h_map = new HashMap<String, String>();
			h_map.put("search","");
			h_map.put("code",ly.getLycode());
			List<HouseBean> list = HouseManager.getHouseAllList(h_map);
			if(!list.isEmpty()){
				return -1;//该楼宇下有设置房屋信息删除失败
			}else{
				if(DBManager.delete("deleteLouYu",map)){
					return 1;//该楼盘下有设置楼宇信息删除失败
				}
			}
		}
		return 0;//楼宇信息不存在
	}
	public static LouYuBean getLouYuById(String id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		return (LouYuBean) DBManager.queryFObj("getLouYuById", map);
	}

	public static String getLouYuTreeList(){
		List<LouPanBean> list = LouPanManager.getLouPanAllList();
		String jsonStr = "[{\"id\":\"0\",\"text\":\"楼盘信息\",\"children\":[";
		if(!list.isEmpty()){
			for (LouPanBean loupan:list) {
				jsonStr+="{\"id\":\""+loupan.getLpcode()+"\",\"text\":\""+loupan.getName()+"\"";
				List<LouYuBean> lylist = LouYuManager.getLouyuListByCode(loupan.getLpcode());
				if(!lylist.isEmpty()){
					jsonStr+=",\"children\":[";
					for (LouYuBean louyu: lylist) {
						jsonStr+="{\"id\":\""+louyu.getLycode()+"\",\"text\":\""+louyu.getLdh()+"\"},";
					}
					jsonStr+="]";
				}
				jsonStr+="},";
			}
		}
		jsonStr += "]}]";
		return jsonStr;
	}

	public static String getLouYuMaxCode(String lpcode){
		String code = "";
		Map<String,String> map = new HashMap<String,String>();
		map.put("code",lpcode);
		String maxCode = DBManager.getString("getLouYuMaxCode",map);
		if(maxCode==""&&maxCode.length()==0){
			maxCode="0000";
		}
		String a = Integer.parseInt(maxCode.substring(2,4))+1+"";
		if(a.length()<2){
			code=lpcode+"0"+a;
		}else{
			code=lpcode+a;
		}
		return code;
	}
}
