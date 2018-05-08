package com.deya.wcm.services.search;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.search.SiteInfo;

/**
 * <p>搜索所需要的管理类 js 调用</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */
public class SearchRPC {

	/**
     * 从search.xml得到所有站点信息列表
     * @param Map map  start_num,page_size
     * @return List
     * */
	public static List<SiteInfo> getSiteListByMap(Map mapParam){
		return SearchForManager.getSiteListByMap(mapParam);
	}
	
	/**
     * 从search.xml得到所有站点信息总数
     * @return int
     * */
	public static int getSiteListByMapCount(){
		return SearchForManager.getSiteListByMapCount();
	}

}
