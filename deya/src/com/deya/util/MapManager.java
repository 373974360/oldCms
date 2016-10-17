package com.deya.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapManager {
	/**
	 * 将MAP中key值全部转换成小写
	 * @param Map
	 * @return map
	 */
	@SuppressWarnings("unchecked")
	public static Map mapKeyValueToLow(Map m)
	{
		if(m != null && m.size() > 0)
		{
			Map<String, Object> new_m = new HashMap<String, Object>();
			Iterator iter = m.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next(); 
			    String key = (String)entry.getKey(); 
			    Object val = entry.getValue();			    
			    new_m.put(key.toLowerCase(), val);
			} 
			return new_m;
		}
		else
			return m;
	}
	
	public static Object getValue(Map<String, String> map,String key){
		try{
			Object o = map.get(key);
			if(o == null ){
				o = map.get(key.toLowerCase());
				if(o == null){
					o = map.get(key.toUpperCase());
				}else{
					o = null;
				}
			}
			
			return o;
			
		}catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
         
	}

}
