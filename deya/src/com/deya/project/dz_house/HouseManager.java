package com.deya.project.dz_house;


import java.util.List;

/**
 * Created by MaChaoWei on 2017/5/17.
 */
public class HouseManager {
    public static String getHouseCount(String search,String code){
        return HouseDAO.getHouseCount(search,code);
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

    public static List<HouseBean> getHouseAllList(String search, String code){
        return HouseDAO.getHouseAllList(search, code);
    }

    public static  List<HouseBean> getHouselist(String search,String code,String start_num, String page_size){
        return HouseDAO.getHouselist(search,code, start_num, page_size);
    }
}
