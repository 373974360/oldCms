package com.deya.wcm.services.appeal.category;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.appeal.category.CategoryBean;
import com.deya.wcm.services.Log.LogManager;

public class CategoryRPC {
	/**
     * 得到所有菜单,并组织为json数据
     * @param
     * @return String
     * */
	public static String getCategoryTreeJsonStr()
	{
		return CategoryManager.getAppCateTreeJsonStr();
	}
	
	/**
     * 得到菜单ID,用于添加页面
     * @param
     * @return String
     * */
	public static int getCategoryID()
	{
		return CategoryManager.getAppealCategoryID();
	}
	
	/**
     * 根据菜单ID得到对象
     * @param String Category_id
     * @return CategoryBean
     * */
	public static CategoryBean getCategoryBean(int Category_id)
	{
		return CategoryManager.getApp_categoryBean(Category_id);
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点个数,用于页面管理
	 * String Category_id
	 * @param
	 * @return String
	 */
	public static String getChildCategoryCount(String Category_id)
	{
		return CategoryManager.getChildAppCateCount(Category_id);
	}
	
	/**
	 * 根据菜单节点ID得到此节点下的子节点列表deep+1,
	 * String Category_id
	 * @param
	 * @return List
	 */
	public static List<CategoryBean> getChildCategoryList(String Category_id)
	{
		return CategoryManager.getChildAppCateList(Category_id);
	}
	
	/**
     * 插入菜单信息
     * @param CategoryBean mb
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean insertCategory(CategoryBean mb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.insertAppCate(mb,stl);
		}else
			return false;
	}
	
	/**
     * 修改菜单信息
     * @param CategoryBean mb
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean updateCategory(CategoryBean mb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.updateAppCate(mb,stl);
		}else
			return false;
	}
	
	/**
     * 保存菜单排序
     * @param String Category_id
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean saveCategorySort(String Category_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.saveAppCateSort(Category_id,stl);
		}else
			return false;
	}
	
	/**
     * 删除菜单信息
     * @param String Category_id
     * @param HttpServletRequest request
     * @return boolean
     * */
	public static boolean deleteCategory(String Category_id,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.deleteAppCate(Category_id,stl);
		}else
			return false;
	}
	
	/**
     * 移动菜单
     * @param String parent_id
     * @param String Category_ids
     * @return boolean
     * */
	public static boolean moveCategory(String parent_id,String Category_ids,HttpServletRequest request) {
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CategoryManager.moveAppCate(parent_id,Category_ids,stl);
		}else
			return false;
	}
	
	public static void main(String arg[])
	{
		System.out.println(getCategoryTreeJsonStr());
		System.out.println(getCategoryBean(1));
	}

}
