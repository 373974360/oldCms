package com.deya.project.dz_house;


import java.util.List;
import java.util.Map;

/**
 * Created by MaChaoWei on 2017/5/17.
 */
public class HouseManager {
    public static String getHouseCount(Map<String,String> map){
        return HouseDAO.getHouseCount(map);
    }

    public static boolean insertHouse(HouseBean house){
        return HouseDAO.insertHouse(house);
    }
    public static boolean deleteHouse(String id){
        return HouseDAO.deleteHouse(id);
    }
    public static HouseBean getHouseById(String id){
        return HouseDAO.getHouseById(id);
    }
    public static boolean updateHouse(HouseBean house){
        return HouseDAO.updateHouse(house);
    }

    public static List<HouseBean> getHouseAllList(Map<String,String>  map){
        return HouseDAO.getHouseAllList(map);
    }

    public static  List<HouseBean> getHouselist(Map<String,String> map){
        return HouseDAO.getHouselist(map);
    }
}
