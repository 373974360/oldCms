package com.deya.project.dz_house;

import java.util.List;

/**
 * Created by MaChaoWei on 2017/5/17.
 */
public class HouseRPC {
    public static String getHouseCount(String search,String code){
        return HouseManager.getHouseCount(search,code);
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
        return HouseManager.getHouseAllList(search, code);
    }

    public static  List<HouseBean> getHouselist(String search,String code,String start_num, String page_size){
        return HouseManager.getHouselist(search, code, start_num, page_size);
    }
}
