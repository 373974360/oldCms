package com.deya.wcm.dao.cms.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.SyncBean;
import com.deya.wcm.db.DBManager;

/**
 *  目录同步数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录同步数据处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */
public class SyncDAO {
		
	/**
	 * 得到所有的目录同步信息列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<SyncBean> getAllSyncBeanList()
	{
		return DBManager.queryFList("getAllSyncList", "");
	}
	
	/**
	 * 插入目录同步信息
	 * @param sb	目录同步信息对象
	 * @param stl
	 * @return	
	 * 		true 成功| false 失败
	 */
	public static boolean insertSync(SyncBean sb)
	{		
		return DBManager.insert("insertSync", sb);
	}
	
	
	
	/**
	 * 删除目录同步信息
	 * @param String s_site_id 目标站点ID	
	 * @return		true 成功| false 失败
	 */
	public static boolean deleteSync(String s_site_id,String cat_ids,String orientation)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("site_id", s_site_id);
		m.put("cat_ids", cat_ids);
		m.put("orientation", orientation);
		return DBManager.delete("deleteSync", m);
	}
	
	/**
	 * 删除目录时删除同步信息
	 * @param String site_id
	 * @param String cat_ids
	 * @return		true 成功| false 失败	 
	 */
	public static boolean deleteSyncForCatID(String site_id,String cat_ids)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("site_id", site_id);
		m.put("cat_ids", cat_ids);
		return DBManager.delete("deleteSync_forCat_id", m);
	}
}
