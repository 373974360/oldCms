package com.deya.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SorterMap {
    
	//按map中的value排序 -- 降序
	public static List<Map.Entry<String, Integer>> sortMapValue(Map maps){
		List<Map.Entry<String, Integer>> info = new ArrayList<Map.Entry<String, Integer>>(maps.entrySet());
		Collections.sort(info, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
			return obj2.getValue() - obj1.getValue();
			}
    	});
		return info;
	}
	
}
