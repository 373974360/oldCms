package com.deya.wcm.dao.search;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.search.IndexBeanInfo;

/**
 * <p>搜索索引创建接口类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */
public interface IIndexInfoDao {
    
	/**
     * 得一般信息列表
     * @param Map map sql所需要的参数
     * @return List
     * */
	public List getInfoListBySiteIdPages(Map map);
	
	
	/**
     * 得一般信息总数
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public int getInfoListBySiteIdCount(Map map);
	
	/**
     * 通过id信息
     * @param int id 
     * @return Map
     * */
	public Map getInfoById(int id);
	
	/**
     * 通过id信息
     * @param Map map 
     * @return Map
     * */
	public Map getInfoById(Map map);

	
}
