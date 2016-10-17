package com.deya.wcm.dao.search.impl;

import java.util.HashMap;
import java.util.Map;


import com.deya.wcm.db.DBManager;

public class InfoServiceUtil {
    
	/**
     * 得到信息总数
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static String getInfoTypeById(int id) { 
		Map map = new HashMap(); 
		map.put("id",id);   
		return getInfoTypeById(map);
	}
	
	/**
     * 得到信息总数
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public static String getInfoTypeById(Map map) {  
		return (String)DBManager.queryFObj("search.getInfoCountById", map);
	}
	
}
