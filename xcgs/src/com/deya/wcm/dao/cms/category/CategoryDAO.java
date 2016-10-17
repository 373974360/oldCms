package com.deya.wcm.dao.cms.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 *  目录管理数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录管理数据处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class CategoryDAO {

	/**
     * 根据站点ID得到目录列表,用于克隆站点
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<CategoryBean> getCategoryListBySiteID(String site_id)
	{
		return DBManager.queryFList("getCategoryListBySiteID",site_id);
	}
	
	/**
     * 得到所有目录列表
     * @param 
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<CategoryBean> getAllCategoryList()
	{
		return DBManager.queryFList("getAllCategoryList", "");
	}
	
	/**
     * 根据ID返回目录对象
     * @param String cat_id
     * @return CategoryBean
     * */
	public static CategoryBean getCategoryBean(String cat_id)
	{
		return (CategoryBean)DBManager.queryFObj("getCategoryBean", cat_id);
	}
	
	/**
     * 根据站点或节点ID,节点类型 获得根目录节总数
     * @param String Map<String,String> m
     * @return List<CategoryBean>
     * */
	public static String getCategoryCountBySiteAndType(Map<String,String> m)
	{
		return DBManager.getString("getCategoryCountBySiteAndType", m);
	}
	
	/**
     * 根据站点或节点ID,节点类型 获得根目录节点列表
    * @param String Map<String,String> m
     * @return List<CategoryBean>
     * */
	@SuppressWarnings("unchecked")
	public static List<CategoryBean> getCategoryListBySiteAndType(Map<String,String> m)
	{
		return DBManager.queryFList("getCategoryListBySiteAndType", m);
	}
	
	/**
     * 克隆目录信息（用于克隆站点）
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean cloneCategory(CategoryBean cgb)
	{
		cgb.setId(PublicTableDAO.getIDByTableName(PublicTableDAO.INFO_CATEGORY_TABLE_NAME));
		return DBManager.insert("insert_info_category", cgb);
	}
	
	/**
     * 插入目录信息
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean insertCategory(CategoryBean cgb,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_info_category", cgb))
		{
			PublicTableDAO.insertSettingLogs("添加","目录",cgb.getCat_id()+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改目录信息
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean updateCategory(CategoryBean cgb,SettingLogsBean stl)
	{
		if(DBManager.update("update_info_category", cgb))
		{
			PublicTableDAO.insertSettingLogs("修改","目录",cgb.getCat_id()+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 批量修改目录信息
     * @param Map
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean batchUpdateCategory(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("batch_update_category", m))
		{
			PublicTableDAO.insertSettingLogs("修改","目录",m.get("cat_ids"),stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改目录首页或列表页模板信息，用于专题工具设计后保存模板关联
     * @param Map
     * @return boolean
     * */
	public static boolean updateCategoryTemplate(Map<String,String> m)
	{
		return DBManager.update("update_info_category_template", m);
	}
	
	
	/**
     * 修改目录归档状态
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean updateCategoryArchiveStatus(String ids,String is_archive,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("is_archive", is_archive);
		if(DBManager.update("update_info_category_archive", m))
		{
			PublicTableDAO.insertSettingLogs("修改","目录归档状态",ids+"",stl);
			return true;
		}
		else
			return false;
	}
	
	
	
	/**
     * 根据分类修改目录信息
     * @param CategoryBean cgb
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean updateCategoryByClass(String cat_ename,String cat_cname,String cat_id)
	{
		Map<String,String> m =new HashMap<String,String>();
		m.put("cat_ename", cat_ename);
		m.put("cat_cname", cat_cname);
		m.put("cat_id", cat_id);
		return DBManager.update("update_info_categoryByClass", m);		
	}
	
	/**
     * 保存排序
     * @param String cat_ids
     * @param SettingLogsBean stl
     * @return CategoryBean
     * */
	public static boolean sortCategory(String ids,SettingLogsBean stl)
	{
		try{
			Map<String,Object> m =new HashMap<String,Object>();
			String[] tempA = ids.split(",");
			for(int i=0;i<tempA.length;i++)
			{
				m.put("cat_sort", i+1);
				m.put("id", tempA[i]);
				DBManager.update("sort_info_category", m);
			}
			PublicTableDAO.insertSettingLogs("保存排序","目录",ids,stl);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
     * 删除模板时清空模板与栏目的关联
     * @param String template_ids
     * @param String site_id
     * @return boolean
     * */
	public static boolean clearCategoryTemplate(String template_ids,String site_id)
	{
		Map<String,String> m =new HashMap<String,String>();
		m.put("template_ids", template_ids);
		m.put("site_id", site_id);
		return DBManager.update("clear_info_category_template", m);
	}
	
	/**
     * 删除目录信息
     * @param String cat_ids
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean deleteCategory(String cat_ids,String site_id,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("cat_ids", cat_ids);
		if(site_id != null && !"".equals(site_id))
			m.put("site_id", site_id);
		if(DBManager.delete("delete_info_category", m))
		{
			PublicTableDAO.insertSettingLogs("删除","目录",cat_ids,stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 根据站点ID删除目录信息
     * @param String site_id
     * @return boolean
     * */
	public static boolean deleteCategoryBySiteiD(String site_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("site_id", site_id);
		try{
			//删除目录同步信息
			DBManager.delete("deleteSync_for_site_id", m);
			//删除目录共享信息
			DBManager.delete("delete_category_sharedBySiteID", m);
			//删除目录与模板关联信息
			DBManager.delete("delete_category_model_bySiteID", m);
			//删除目录与人员关联
			CategoryReleDAO.deleteCategoryReleUserBySiteID(site_id);
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
     * 移动目录
     * @param Map<String,String> m
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean moveCategory(Map<String,String> m,SettingLogsBean stl)
	{
		if(DBManager.update("move_category", m))
		{			
			PublicTableDAO.insertSettingLogs("移动","目录",m.get("cat_id"),stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 目录关联分类操作
     * @param String class_id
     * @param String cat_id
     * @param SettingLogsBean stl
     * @return boolean
     * */
	public static boolean releCategoryClass(String class_id,String cat_id,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("cat_class_id", class_id);
		m.put("cat_id", cat_id);
		if(DBManager.update("rele_category_class", m))
		{			
			PublicTableDAO.insertSettingLogs("修改","目录与分类方式关联",cat_id,stl);
			return true;
		}
		else
			return false;
	}
	
	
	/**
     * 根据分类方式删除目录
     * @param String class_id
     * @return boolean
     * */
	public static boolean deleteCategoryByClassID(String class_ids){
		Map<String,String> m = new HashMap<String,String>();
		m.put("class_id", class_ids);
		return DBManager.delete("delete_info_categoryByClassID", m);
	}
	
	/**
     * 根据节点ID插入默认的公开目录（公开指南，公开指引，公开年报）
     * @param String class_id
     * @return boolean
     * */
	public static boolean insertGKDefaultCategory(CategoryBean cgb)
	{
		return DBManager.insert("insert_gk_default_cate", cgb);
	}
	
	/**
     * 修改基础目录列表页模板
     * @param String template_id
     * @return boolean
     * */
	public static boolean updateBaseCategoryTemplate(String template_id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("template_id", template_id);
		return DBManager.update("update_baseCategory_template", m);
	}
	
	/**
     * 根据ID修改栏目列表页关联字段
     * @param String template_id
     * @param String id
     * @return boolean
     * */
	public static boolean updateCateTemplateListByID(String template_id,String id)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("template_id", template_id);
		m.put("id", id);
		return DBManager.update("update_baseCategory_template", m);
	}
	
	/**
     * 得到基础目录列表页模板
     * @param 
     * @return String
     * */
	public static String getBaseCategoryTemplateListID()
	{
		return DBManager.getString("getBaseCategoryTemplateListID", "");
	}
}
