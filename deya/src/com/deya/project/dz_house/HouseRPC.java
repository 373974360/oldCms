package com.deya.project.dz_house;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MaChaoWei on 2017/5/17.
 */
public class HouseRPC {
    public static String getHouseCount(String search,String code){
        Map<String,String> map = new HashMap<String,String>();
        map.put("search",search);
        map.put("code",code);
        return HouseManager.getHouseCount(map);
    }

    public static boolean insertHouse(HouseBean house){
        return HouseManager.insertHouse(house);
    }
    public static boolean deleteHouse(String id){
        return HouseManager.deleteHouse(id);
    }
    public static HouseBean getHouseById(String id){
        return HouseManager.getHouseById(id);
    }
    public static boolean updateHouse(HouseBean house){
        return HouseManager.updateHouse(house);
    }

    public static List<HouseBean> getHouseAllList(String search, String code){
        Map<String,String> map = new HashMap<String,String>();
        map.put("search",search);
        map.put("code",code);
        return HouseManager.getHouseAllList(map);
    }

    public static  List<HouseBean> getHouselist(String search,String code,String start_num, String page_size){
        Map<String,String> map = new HashMap<String,String>();
        map.put("search",search);
        map.put("code",code);
        map.put("start_num",start_num);
        map.put("page_size",page_size);
        return HouseManager.getHouselist(map);
    }
}
