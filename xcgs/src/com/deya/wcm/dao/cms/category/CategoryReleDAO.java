package com.deya.wcm.dao.cms.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CategoryReleBean;
import com.deya.wcm.db.DBManager;

/**
 *  目录与人员关联数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录与人员关联数据处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */

public class CategoryReleDAO {
	/**
	 * 得到所有的"分类方式"信息列表
	 * @return 
	 * 		"分类方式"信息列表
	 */
	@SuppressWarnings("unchecked")
	public static List<CategoryReleBean> getCategoryReleUserList()
	{
		return DBManager.queryFList("getCategoryReleUserList", "");
	}
	
	/**
	 * 插入目录与人员的关联
	 * @param CategoryReleBean crb
	 * @return boolean
	 */
	public static boolean insertCategoryReleUser(CategoryReleBean crb)
	{
		return DBManager.insert("insert_category_releUser", crb);
	}
	
	/**
	 * 根据目录ID删除关联
	 * @param int cat_id
	 * @return boolean		
	 */
	public static boolean deleteCategoryReleUserByCatID(String cat_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("cat_id",cat_id);
		m.put("site_id",site_id);
		return DBManager.delete("delete_category_releByCatID", m);
	}
	
	/**
	 * 根据人员,类型删除关联
	 * @param int priv_type
	 * @param String prv_id
	 * @param String site_id
	 * @return boolean		
	 */
	public static boolean deleteCategoryReleUserByCatID(int priv_type,String prv_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("priv_type",priv_type+"");
		m.put("prv_id",prv_id);
		m.put("site_id",site_id);
		return DBManager.delete("delete_category_releByCatID", m);
	}
	
	/**
	 * 根据站点ID删除关联
	 * @param Sting site_id
	 * @return boolean		
	 */
	public static boolean deleteCategoryReleUserBySiteID(String site_id)
	{
		return DBManager.delete("delete_category_releBySiteID", site_id);
	}
}
