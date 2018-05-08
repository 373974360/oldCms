package com.deya.wcm.dao.search.impl;

/**
 * <p>搜索索引创建实现类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.dao.search.IIndexInfoDao;
import com.deya.wcm.db.DBManager;

public class IndexFileDaoImpl implements IIndexInfoDao{

	/**
     * 得一般信息列表有翻页
     * @param Map map sql所需要的参数 
     * @return List
     * */
	public List getInfoListBySiteIdPages(Map map) {
		//System.out.println("map====" + map);
		return DBManager.queryFList("search.getFileListBySiteIdPages", map);
	} 
	
	/**
     * 得一般信息总数
     * @param Map map sql所需要的参数 
     * @return int
     * */
	public int getInfoListBySiteIdCount(Map map) {
		return Integer.valueOf((Integer)DBManager.queryFObj("search.getFileListBySiteIdCount", map));
	}
	
	/**
     * 通过id信息
     * @param int id 
     * @return Map
     * */
	public Map getInfoById(int id){
		Map map = new HashMap();
		map.put("id",id);
		return (Map)DBManager.queryFObj("search.getFileById", map);
	}

	@Override
	public Map getInfoById(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

}
