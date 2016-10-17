package com.deya.wcm.services.search;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.deya.util.DateUtil;
import com.deya.wcm.services.search.index.IndexManager;
import com.deya.wcm.services.search.index.util.Queue;

/**
 * <p>搜索所需要的管理类 -- 内部调用类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */
public class SearchInnerManager {

	private static Set infoSetAdd = new HashSet();   //存放信息Id  来实现增量创建索引  -- 添加
	private static Set infoSetDel = new HashSet();   //存放信息Id  来实现增量创建索引  -- 删除
	
	private static Queue queue = new Queue();   //存放信息Id  来实现增量创建索引
    
	
	static{
		Timer timer = new Timer();
		Date now = new Date();
        SearchIndexTask task = new SearchIndexTask();
        Date endOfDay = DateUtil.getEndOfDay(now);
        timer.schedule(new SearchResourceIndexTask(), endOfDay, 1000 * 60 * 60 * 24); //每天晚上12点  
        timer.schedule(task, now, 1000 * 60 * 30); //每30分钟
        //timer.schedule(task, now, 1000 * 60 * 1); //每1分钟，测试用
	}
	
	//存放信息Id  来实现增量创建索引  -- 添加
	public static void infoSetAdd(String id){
		Map map = new HashMap();
		map.put("id",id);
		map.put("flag","1"); //1 表示是添加
		queue.put(map);
	}
	
	//存放信息Id  来实现增量创建索引  -- 删除
	public static void infoSetDel(String id){
		Map map = new HashMap();
		map.put("id",id);
		map.put("flag","0"); //0表示是删除
		queue.put(map);
	}
	
	
	static class SearchIndexTask extends TimerTask {
	
	    public void run() {
//	    	System.out.println("Start CreateSearchIndex Task!!!");
//    	    Set setAdd = new HashSet();
//    	    setAdd.addAll(infoSetAdd);
//    	    System.out.println("infoSetAdd=====" + infoSetAdd);
//    	    Iterator it = setAdd.iterator();
//			while(it.hasNext()){
//				String id = (String)it.next();
//				Map map = new HashMap();
//				System.out.println("id=====" + id);
//				map.put("id",id); 
//				IndexManager.indexInfoService.appendSingleDocument(map);
//				IndexManager.indexFileService.appendSingleDocument(map);
//				IndexManager.indexXxgkService.appendSingleDocument(map);
//				IndexManager.indexBsznService.appendSingleDocument(map);
//			}
//			initSetAdd(setAdd);
//			System.out.println("End CreateSearchIndex Task!!!");
//			
//			
//			System.out.println("Start DeleteSearchIndex Task!!!");
//	        Set setDelete = new HashSet();
//	        setDelete.addAll(infoSetDel);
//	        System.out.println("infoSetDel=====" + infoSetDel);
//	        Iterator itDel = setDelete.iterator();
//	        while(itDel.hasNext()){
//				String id = (String)itDel.next();
//				Map map = new HashMap();
//				map.put("id",id); 
//				System.out.println("id=====" + id);
//				IndexManager.indexInfoService.deleteSingleDocument(map);
////				IndexManager.indexFileService.deleteSingleDocument(map);
////				IndexManager.indexXxgkService.deleteSingleDocument(map);
//			}
//	        initSetDelete(setDelete);
//	        System.out.println("End DeleteSearchIndex Task!!!");
	    	
	    	System.out.println("Start SearchIndex Task!!!");
	    	while(!queue.isEmpty()) {
	    		Map map = (Map)queue.get();
	    		//String id = (String)map.get("id");
	    		String flag = (String)map.get("flag");
	    		if(flag.equals("1")){
					System.out.println("Create SearchIndex map =====" + map);
					IndexManager.appendSingleDocument(map);
	    		}
	    		if(flag.equals("0")){  
					System.out.println("Delete SearchIndex map =====" + map);
					IndexManager.deleteSingleDocument(map);
	    		}
	        } 
			System.out.println("End SearchIndex Task!!!");
	    }
	    
	    
	    public static void initSetAdd(Set set){
			Iterator it = set.iterator();
			while(it.hasNext()){
				Object o = it.next();
				if(infoSetAdd.contains(o)){
					infoSetAdd.remove(o);
				}
			}
	    }
	    public static void initSetDelete(Set set){
			Iterator it = set.iterator();
			while(it.hasNext()){
				Object o = it.next();
				if(infoSetDel.contains(o)){
					infoSetDel.remove(o);
				}
			}
	    }
	    
	}
	
	
	static class SearchResourceIndexTask extends TimerTask {
		
	    public void run() {
	    	System.out.println("Start SearchResourceIndex Task!!!");
	    	IndexManager.createResourceIndex(); 
			System.out.println("End SearchResourceIndex Task!!!");
	    }
	    
	}
	
	
	public static void main(String arr[]){
		//infoSetAdd("ss");
		//infoSetDel("ss");
	}
	
}
