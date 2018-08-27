package com.deya.project.dz_jyhgl;

import java.util.List;
import java.util.Map;

public class InfoUpdateResultRPC {
    public static List<InfoUpdateResultBean> getInfoUpdateResultList(Map<String, String> map){
        return InfoUpdateResultManager.getInfoUpdateResultList(map);
    }
}
