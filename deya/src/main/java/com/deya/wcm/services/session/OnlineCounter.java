package com.deya.wcm.services.session;

import java.util.HashMap;
import java.util.Map;

public class OnlineCounter {    
    private static Map<String,Integer>  mapAll = new HashMap<String,Integer>();
    public static long getOnline(String site_id) { 
    	//System.out.println("getOnline site_id-----" + site_id);
    	Integer count = 0;
    	try{
    		count = mapAll.get(site_id);
    		//System.out.println("getOnline count-----" + count);
        	if(count==null){
        		count = 0;
        	}else if(count<0){
        		count = 0;
        		mapAll.put("site_id",1);
        	}
        }catch (Exception e) {
			e.printStackTrace();
		}finally{
			return count;
		}
    }     
    public static void raise(String site_id){ 
    	Integer count = 0;
    	try{
    		count = mapAll.get(site_id);
    		//System.out.println("raise count-----" + count);
        	if(count==null){
        		count = 0;
        	}
        	//count = 9;
        	mapAll.put(site_id, ++count);
        	//System.out.println("raise mapAll-----" + mapAll);
        }catch (Exception e) {
			e.printStackTrace();
		}
    }  
    
    public static void reduce( String site_id){ 
    	Integer count = 0;
    	try{
    		count = mapAll.get(site_id);
        	if(count==null){
        		count = 0;
        		mapAll.put(site_id, count);
        	}else{
        		mapAll.put(site_id, --count);
        	}
        }catch (Exception e) {
			e.printStackTrace();
		}
   } 
}