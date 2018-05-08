package com.deya.wcm.services.cms.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.cms.category.CategoryBean;
import com.deya.wcm.bean.cms.category.CategoryModel;
import com.deya.wcm.bean.system.formodel.ModelBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.cms.category.CategoryModelDAO;
import com.deya.wcm.services.system.formodel.ModelManager;

/**
 *  目录与内容模型关联逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 目录与内容模型关联逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class CategoryModelManager implements ISyncCatch{
	private static Map<String,List<CategoryModel>> cmm = new HashMap<String,List<CategoryModel>>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		cmm.clear();
		try{
			List<CategoryModel> l = CategoryModelDAO.getCategoryReleModelList();		
			if(l != null && l.size() > 0)
			{
				String temp_key = "";
				for(int i=0;i<l.size();i++)
				{
					temp_key = l.get(i).getCat_id()+"_"+l.get(i).getSite_id();
					
					if(cmm.containsKey(temp_key))
					{
						cmm.get(temp_key).add(l.get(i));
					}else
					{
						List<CategoryModel> map_list = new ArrayList<CategoryModel>();
						map_list.add(l.get(i));
						cmm.put(temp_key, map_list);
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void reloadCategoryModel()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.cms.category.CategoryModelManager");
	}
	
	/**
     * 根据栏目ID,站点ID,内容模型ID 得到模板ID
     * @param 
     * @return 
     * */
	public static String getTemplateID(String cat_id,String site_id,int model_id)
	{//公开指南等特殊的栏目用公开系统里的配置
		String tid = "";
		if("10".equals(cat_id) || "11".equals(cat_id) || "12".equals(cat_id))
		{
			site_id = "GK";
			tid = getTemplateIDHandl(cat_id,site_id,model_id);
		}else
		{
			tid = getTemplateIDHandl(cat_id,site_id,model_id);
		}
		if("0".equals(tid))
		{
			//得专题的栏目
			CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id), site_id);
			String root_tree_id = cb.getCat_position().split("\\$")[2];
			CategoryBean root_cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(root_tree_id), site_id);
			//如果是专题栏目，查找根节点对象						
			if(root_cb.getCat_type() == 1)
			{							
				tid = getTemplateIDHandl(root_cb.getCat_id()+"",site_id,model_id);
			}			
		}
		/*
		String tid = getTemplateIDHandl(cat_id,site_id,model_id);
		
		if("0".equals(tid))
		{
			//这是信息公开3个特殊的栏目，公开指南，年报，指引		
			if("10".equals(cat_id) || "11".equals(cat_id) || "12".equals(cat_id))
			{
				site_id = "GK";
				tid = getTemplateIDHandl(cat_id,site_id,model_id);
			}else
			{
				//得专题的栏目
				CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id), site_id);
				String root_tree_id = cb.getCat_position().split("\\$")[2];
				CategoryBean root_cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(root_tree_id), site_id);
				//如果是专题栏目，查找根节点对象						
				if(root_cb.getCat_type() == 1)
				{							
					tid = getTemplateIDHandl(root_cb.getCat_id()+"",site_id,model_id);
				}
			}
		}*/	
		return tid;
	}
	
	public static String getTemplateIDHandl(String cat_id,String site_id,int model_id)
	{
		List<CategoryModel> l = getCategoryReleModelList(cat_id,site_id);
		if(l != null && l.size() > 0)
		{
			for(CategoryModel c : l)
			{
				if(c.getModel_id() == model_id)
					return c.getTemplate_content()+"";
			}
		}
		return "0";
	}
	
	
	/**
     * 根据栏目ID和站点ID得到目录与内容模型对象列表
     * @param 
     * @return List
     * */
	public static List<ModelBean> getCateReleModelBeanList(String cat_id,String site_id)
	{
		List<ModelBean> mb_list = new ArrayList<ModelBean>();
		List<CategoryModel> l = getCategoryReleModelList(cat_id,site_id);
		if(l != null && l.size() > 0)
		{
			for(CategoryModel cm : l)
			{
				ModelBean mb = ModelManager.getModelBean(cm.getModel_id());
				//新建目录中 如果没有选中是否添加 则此处处理为 只关联模板用来生成内容也  不添加此模板类型的内容
				if(mb.getDisabled() == 0 && cm.getIsAdd() == 1)
					mb_list.add(mb);
			}
		}
		ModelManager.sortModelList(mb_list);
		return mb_list;
	}
	
	/**
     * 根据栏目ID和站点ID得到目录与内容模型关联列表
     * @param 
     * @return List
     * 这里弄的比较复杂了，纯目录新建的话　id和cat_id　是一致的，所以这里用ID或cat_id来取是没有问题的，但是克隆的站点就不一样了
     * 克隆的站点在这个表中存的是cat_id，所以这里需要判断一下，首先根据传过来的ID和站点ID进行匹配，如果没有，有可能传过来的是cat_id,
     * 这样就要判断是传过来的是cat_id还是ID
     * */
	public static List<CategoryModel> getCategoryReleModelList(String cat_id,String site_id)
	{
		String temp_key = "";
		CategoryBean cb = CategoryManager.getCategoryBeanCatID(Integer.parseInt(cat_id), site_id);
		if(cb != null)
		{
			temp_key = cb.getId()+"_"+site_id;//根据cat_ID得到 id
			
			if(cmm.containsKey(temp_key))
				return cmm.get(temp_key);
			else
			{
				temp_key = cb.getCat_id()+"_"+site_id;
				if(cmm.containsKey(temp_key))
					return cmm.get(temp_key);
				else
					return null;
			}
		}else
		{
			cb = CategoryManager.getCategoryBean(Integer.parseInt(cat_id));				
			if(cb != null)
			{
				temp_key = cat_id+"_"+site_id;
				
				if(cmm.containsKey(temp_key))
					return cmm.get(temp_key);
				else
					return cmm.get(cb.getCat_id()+"_"+site_id);
			}else
				return null;
		}		
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
		if(l != null && l.size() > 0)
		{
			if(CategoryModelDAO.insertCategoryModel(l))
			{
				reloadCategoryModel();
				return true;
			}
			else
				return false;
		}
		return true;
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
		if(CategoryModelDAO.updateCategoryModel(l,cat_id,site_id))
		{
			reloadCategoryModel();
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
		if(CategoryModelDAO.deleteCategoryModel(cat_id, site_id))
		{
			reloadCategoryModel();
			return true;
		}
		else
			return false;
	}
	
	public static void main(String args[])
	{
		System.out.println(getCategoryReleModelList("759",""));		
	}
}
