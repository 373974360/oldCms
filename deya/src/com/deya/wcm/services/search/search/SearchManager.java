package com.deya.wcm.services.search.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.search.PageControl;
import com.deya.wcm.bean.search.ResultBean;
import com.deya.wcm.bean.search.SearchResult;
import com.deya.wcm.services.search.search.util.SearchUtil;

public class SearchManager {
    
	/**
	 *简单搜索
	 *@param HttpServletRequest request
	 *@return SearchResult 
	* */
	public static SearchResult search(HttpServletRequest request){  
	   String query = SearchUtil.getXmlParam(request);
	   return search(query);
	}
	
	
	/**
	 *简单搜索
	 *@param String query
	 *@return SearchResult 
	* */
	public static SearchResult search(String query){  
	   Map map = SearchUtil.initPraToMap(query);
	   return SearchInfoManager.search(map);
	}
	
	/**
	 *高级搜索
	 *@param HttpServletRequest request
	 *@return SearchResult 
	* */
	public static SearchResult searchGJ(HttpServletRequest request){  
	   String query = SearchUtil.getXmlParam(request);
	   return searchGJ(query);
	}
	
	/**
	 *高级搜索
	 *@param String query
	 *@return SearchResult 
	* */
	public static SearchResult searchGJ(String query){  
	   Map map = SearchUtil.initPraToMap(query);
	   return SearchInfoManager.searchGJ(map);
	}
	
	public static void main (String arr[]){
		Map map = new HashMap();
		map.put("q", "信息");
		map.put("p", "1");
		SearchResult result = SearchInfoManager.search(map);
		if(result==null){
			 System.out.println("没有符合条件的记录");
			 return ;
		}
		String key = result.getKey(); //关键字
	    String time = result.getTime();//得到搜索所用时间
	    PageControl pageControl = result.getPageControl(); //分页信息
	    List<ResultBean> items = result.getItems();//结果条目列表
	    
	    System.out.println("关键字:"+key);
	    System.out.println("搜索所用时间:"+time);
	    System.out.println("总条数:"+pageControl.getMaxRowCount());
	    System.out.println("总页数:"+pageControl.getMaxPage());
	    System.out.println("当前页数:"+pageControl.getCurPage());
	    for(ResultBean bean : items){
	    	System.out.println();
	    	System.out.println(bean.getId());
	    	System.out.println(bean.getTitle());
	    	System.out.println(bean.getContent());
	    	System.out.println(bean.getUrl());
	    	System.out.println(bean.getTime());
	    	System.out.println();
	    }
	}
}
