package com.deya.wcm.dao.cms.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CategorySharedBean;
import com.deya.wcm.db.DBManager;

/**
 *  目录共享管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录共享管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class CategorySharedDAO {
	/**
     * 得到所有目录共享列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<CategorySharedBean> getAllCategorySharedList()
	{
		return DBManager.queryFList("getAllCategorySharedList", "");
	}
	
	/**
     * 插入目录共享信息
     * @param CategorySharedBean csb
     * @return boolean
     * */
	public static boolean insertCategoryShared(CategorySharedBean csb)
	{
		return DBManager.insert("insert_category_shared", csb);
	}
	
	
	/**
     * 根据站点ID，栏目ID，共享类型删除信息
     * @param String site_id
     * @param int cat_id
     * @param int shared_type
     * @return boolean
     * */
	public static boolean deleteCategoryShared(String site_id,int cat_id,int shared_type)
	{
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("s_site_id", site_id);
		m.put("cat_id", cat_id);
		m.put("shared_type", shared_type);
		return DBManager.delete("delete_category_shared", m);
	}
	
	/**
     * 根据栏目ID删除信息      
     * @param String cat_ids
     * @return boolean
     * */
	public static boolean deleteCategorySharedByCatID(String cat_ids,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("cat_ids", cat_ids);
		m.put("s_site_id", site_id);		
		return DBManager.delete("delete_category_sharedByCatID", m);
	}
}
