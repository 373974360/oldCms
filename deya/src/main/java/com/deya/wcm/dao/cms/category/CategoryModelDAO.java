package com.deya.wcm.dao.cms.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CategoryModel;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  目录与内容模型关联数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录与内容模型关联数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class CategoryModelDAO {
	/**
     * 得到所有关联信息
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<CategoryModel> getCategoryReleModelList()
	{
		return DBManager.queryFList("getCategoryReleModelList", "");
	}
	
	/**
     * 插入关联信息
     * @param  CategoryModel
     * @param  String cat_id
     * @param  String site_id
     * @return boolean
     * */
	public static boolean insertCategoryModel(List<CategoryModel> l)
	{
		try{		
			for(int i=0;i<l.size();i++)
			{
				l.get(i).setCat_model_id(PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CATEGORY_MODEL_TABLE_NAME));					
				DBManager.insert("insert_category_model", l.get(i));
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 修改关联信息
     * @param  CategoryModel
     * @param  String cat_id
     * @param  String site_id
     * @return boolean
     * */
	public static boolean updateCategoryModel(List<CategoryModel> l,String cat_id,String site_id)
	{
		//首先删除
		if(deleteCategoryModel(cat_id,site_id))
		{
			if(l != null && l.size() > 0)
			{
				for(int i=0;i<l.size();i++)
				{
					l.get(i).setCat_model_id(PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CATEGORY_MODEL_TABLE_NAME));					
					DBManager.insert("insert_category_model", l.get(i));
				}
			}
			return true;
		}
		else
			return false;
	}
	
	/**
     * 删除关联信息
     * @param  String cat_id
     * @param  String site_id
     * @return boolean
     * */
	public static boolean deleteCategoryModel(String cat_id,String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("cat_id", cat_id);
		if(site_id != null && !"".equals(site_id))
			m.put("site_id", site_id);
		return DBManager.delete("delete_category_model", m);
	}
}
