package com.deya.project.dz_house;

import com.deya.wcm.db.DBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MaChaoWei on 2017/5/17.
 */
public class HouseDAO {

    public static String getHouseCount(Map<String,String> map){
        return DBManager.getString("getHouseCount", map);
    }

    public static boolean insertHouse(HouseBean house){
        house.setHousecode(getHouseMaxCode(house.getHousecode()));
        return DBManager.insert("insertHouse",house);
    }
    public static boolean deleteHouse(String id){
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",id);
        return DBManager.delete("deleteHouse",map);
    }
    public static HouseBean getHouseById(String id){
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",id);
        return (HouseBean)DBManager.queryFObj("getHouseById",map);
    }
    public static boolean updateHouse(HouseBean house){
        return DBManager.update("updateHouse",house);
    }

    public static List<HouseBean> getHouseAllList(Map<String,String> map){
        return DBManager.queryFList("getHouseAllList",map);
    }

    public static  List<HouseBean> getHouselist(Map<String,String> map){
        return DBManager.queryFList("getHouselist",map);
    }

    public static String getHouseMaxCode(String lycode){
        String code = "";
        Map<String,String> map = new HashMap<String,String>();
        map.put("code",lycode);
        String maxCode = DBManager.getString("getHouseMaxCode",map);
        if(maxCode==""&&maxCode.length()==0){
            maxCode="00000000";
        }
        String a = Integer.parseInt(maxCode.substring(4,8))+1+"";
        if(a.length()<4){
            code=lycode+"0"+a;
        }else{
            code=lycode+a;
        }
        return code;
    }

}
