package com.deya.wcm.dao.cms.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CategoryGetRegu;
import com.deya.wcm.db.DBManager;

/**
 *  目录获取规则数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录获取规则数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class CategoryGetReguDAO {
	/**
	 * 根据目录节点ID得到所有规则
	 * @param 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<CategoryGetRegu> getCategoryReguList(Map<String,String> m)
	{		
		return DBManager.queryFList("getCategoryReguList", m);
	}
	
	/**
	 * 插入目录节点获取规则
	 * @param CategoryGetReguManager cgr
	 * @return boolean
	 */
	public static boolean insertCategoryRegu(CategoryGetRegu cgr)
	{
		return DBManager.insert("insert_info_category_regu", cgr);
	}
	
	/**
	 * 根据目录节点ID删除获取规则
	 * @param RegulationBean rub
	 * @return boolean
	 */
	public static boolean deleteCategoryRegu(String cat_id,String site_id)	
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("cat_ids", cat_id+"");
		m.put("site_id", site_id+"");		
		return DBManager.insert("delete_info_category_regu", m);
	}
}
