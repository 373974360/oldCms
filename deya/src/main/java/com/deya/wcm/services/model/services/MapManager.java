package com.deya.wcm.services.model.services;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapManager {
	
	/**
	 * 将List<MAP>中key值全部转换成小写
	 * @param List<Map>
	 * @return map
	 */
	public static List<Map> listMapKeyValueToLow(List<Map> list){
		List<Map> result = new ArrayList<Map>();
		for(Map map : list){
			Map map2 = mapKeyValueToLow(map);
			result.add(map2);
		}
		return result;
	}
	
	
	/**
	 * 将MAP中key值全部转换成小写
	 * @param Map
	 * @return map
	 */
	public static Map mapKeyValueToLow(Map m)
	{
		if(m != null && m.size() > 0)
		{
			Map<String,Object> new_m = new HashMap<String,Object>();
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
	
	
	/**
	 * 将MAP中key值全部转换成小写并把value值转换成String
	 * @param Map
	 * @return map
	 */
	public static Map mapKeyToLowValueToString(Map m)
	{
		Map<String,String> new_m = new HashMap<String,String>();
		try{
			if(m != null && m.size() > 0)
			{
				Iterator iter = m.entrySet().iterator(); 
				while (iter.hasNext()) { 
				    Map.Entry entry = (Map.Entry) iter.next(); 
				    String key = (String)entry.getKey(); 
				    Object object = entry.getValue();		
					String value = "";
					if(object instanceof java.math.BigDecimal){
						value = object.toString();
					}else if(object instanceof oracle.sql.CLOB){
						  oracle.sql.CLOB clob = (oracle.sql.CLOB)object;
						  BufferedReader br = new BufferedReader(clob.getCharacterStream());
				    	  String str = "";
				    	  while((str=br.readLine()) != null){
				    		  value = value.concat(str);
				    	  }
					}
					else{
						//value = (String)object; 
						value = String.valueOf(object); 
					}
				    
				    new_m.put(key.toLowerCase(), value);
				} 
				return new_m;
			}
			else
				return m;
		}catch (Exception e) {
			e.printStackTrace();
			return new_m;
		}
		
	}

	
	/**
	 * 得到MAP中key对用的值
	 * @param Map
	 * @@param key  
	 * @return Object
	 */
	public static Object getValue(Map map,String key){
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
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
            Map map = new HashMap();
            map.put("BB", "222");
            map.put("AA", "111");
            map.put("aa", "111");
            List<Map> list = new ArrayList<Map>();
            list.add(map);
            //map.put("aa", "222");
//            System.out.println(map.get("aa"));
//            System.out.println(mapKeyValueToLow(map).get("aa"));
//            System.out.println(getValue(map, "aa"));
            
            
            for(Map map1 : list){
            	System.out.println(map1);
            }
            
            list = listMapKeyValueToLow(list);
            
            for(Map map1 : list){
            	System.out.println(map1);
            }
            
	}

}
