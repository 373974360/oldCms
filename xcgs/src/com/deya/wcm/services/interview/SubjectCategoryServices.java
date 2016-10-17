package com.deya.wcm.services.interview;

import java.util.*;

import com.deya.wcm.bean.interview.SubjectCategory;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.interview.*;
import com.deya.util.DateUtil;

/**
 * 访谈主题分类逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题分类的逻辑处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */

public class SubjectCategoryServices implements ISyncCatch{
	private static Map<Integer,SubjectCategory> cat_map = new HashMap<Integer,SubjectCategory>();
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		cat_map.clear();
		List<SubjectCategory> l = SubjectCategoryDAO.getAllSubjectCategoryList();
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				cat_map.put(l.get(i).getId(), l.get(i));
			}
		}
	}
	
	public static void reloadSubjectCategory()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.interview.SubjectCategoryServices");
	}
	
	/**
     * 根据分类ID得到该分类下的主题个数,用于删除分类时判断下面是否有主题记录
     * @param int id
     * @return int　条数
     * */
	public static String getSubjectCountByCategoryID(int id)
	{
		return SubjectCategoryDAO.getSubjectCountByCategoryID(id);
	}
	
	/**
     * 得到主题分类总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSubCategoryCount(String con,String site_id){
		
		return SubjectCategoryDAO.getSubCategoryCount(con,site_id);
	} 
	
	/**
     * 得到主题分类对象信息
     * @param int　id 查询条件
     * @return SubjectCategory　题分类对象
     * */
	public static SubjectCategory getSubjectCategoryBean(int id)
	{
		if(cat_map.containsKey(id))
		{
			return cat_map.get(id);
		}else
		{
			SubjectCategory sc = SubjectCategoryDAO.getSubjectCategoryBean(id);
			if(sc != null)
			{
				cat_map.put(sc.getId(), sc);
				return sc;
			}
			else
				return null;
		}
	}
	
	/**
     * 得到主题分类对象信息
     * @param String category_id 查询条件
     * @return SubjectCategory　题分类对象
     * */
	public static SubjectCategory getSubjectCategoryBean(String category_id)
	{
		Set<Integer> s = cat_map.keySet();
		for(int i : s)
		{
			SubjectCategory sc = cat_map.get(i);
			if(category_id.equals(sc.getCategory_id()))
				return sc;
		}
		return SubjectCategoryDAO.getSubjectCategoryBean(category_id);
	}
	
	/**
     * 得到所有主题分类信息　id,category_id,name
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubCategoryAllName(String site_id)
	{			
		List<SubjectCategory> l = new ArrayList<SubjectCategory>();
		Set<Integer> s = cat_map.keySet();
		for(Integer i : s)
		{
			SubjectCategory sc = cat_map.get(i);
			if(site_id.equals(sc.getSite_id()) && sc.getIs_delete() == 0 && sc.getPublish_status() == 1)
				l.add(sc);
		}
		return l;
	}
	
	/**
     * 得到主题分类列表
     * @param String　con 查询条件
     * @param int　start_num 起始条数
     * @param int　page_size 每页显示条数
     * @return List　列表
     * */
	@SuppressWarnings("unchecked")
	public static List getSubCategoryList(String con,int start_num,int page_size,String site_id){
		Map m = new HashMap();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("con", con);
		m.put("site_id", site_id);
		return SubjectCategoryDAO.getSubCategoryList(m);
	}
	
	/**
     * 插入主题分类
     * @param SubjectCategory sc　分类对象
     * @return boolean　true or false
     * */
	public static boolean insertSubCategory(SubjectCategory sc,SettingLogsBean stl)
	{		
		sc.setCategory_id(UUID.randomUUID().toString());
		sc.setAdd_time(DateUtil.getCurrentDateTime());	
		
		if(sc.getPublish_status() == 1)
			sc.setPublish_time(DateUtil.getCurrentDateTime());
		if(SubjectCategoryDAO.insertSubCategory(sc,stl))
		{
			reloadSubjectCategory();
			return true;
		}else
			return false;
	}
	/**
     * 修改主题分类
     * @param SubjectCategory sc　分类对象
     * @return boolean　true or false
     * */
	public static boolean updateSubCategory(SubjectCategory sc,SettingLogsBean stl)
	{		
		sc.setUpdate_time(DateUtil.getCurrentDateTime());
		if(sc.getPublish_status() == 1)
			sc.setPublish_time(DateUtil.getCurrentDateTime());
		if(SubjectCategoryDAO.updateSubCategory(sc,stl))
		{
			reloadSubjectCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 删除主题分类
     * @param String ids 要删除的ID
     * @param String user_id　当前操作用户
     * @return boolean　true or false
     * */
	public static boolean deleteSubCategory(String ids,String user_name,SettingLogsBean stl)
	{		
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("user_name", user_name);
		m.put("current_time", DateUtil.getCurrentDateTime());	
		if(SubjectCategoryDAO.deleteSubCategory(m,stl))
		{
			reloadSubjectCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改主题分类发布状态
     * @param String ids　要发布的信息ID
     * @param int status_flag　要发布的状态　1为发布，0为撤消
     * @param String user_id　当前操作用户
     * @return boolean　true or false
     * */
	public static boolean updateSubCategoryState(String ids,int status_flag,String user_id,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("status", ""+status_flag);
		m.put("user_name", user_id);		
		m.put("current_time", DateUtil.getCurrentDateTime());
		if(SubjectCategoryDAO.updateSubCategoryState(m,stl))
		{
			reloadSubjectCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 保存分类排序
     * @param String ids　要排序的信息ID
     * @return boolean　true or false
     * */
	public static boolean saveSubCategorySort(String ids,SettingLogsBean stl)
	{		
		return SubjectCategoryDAO.saveSubCategorySort(ids,stl);		
	}
	
	/**
     * 查找所有分类中任意的模板ID,为应对传入的分类ID为空而找不到对应的模板
     * @param String template_type
     * @return String
     * */
	@SuppressWarnings("unchecked")
	public static String getInterViewTemplate(String template_type,String site_id)
	{		
		Set<Integer> s = cat_map.keySet();
		for(int i : s)
		{
			SubjectCategory sc = cat_map.get(i);
			if(site_id.equals(sc.getSite_id()))
			{
				if("list".equals(template_type))
				{
					if(!"".equals(sc.getM_hlist_path()))
						return sc.getM_hlist_path();
				}
				if("live".equals(template_type))
				{
					if(!"".equals(sc.getM_on_path()))
						return sc.getM_on_path();
				}
				if("forecastList".equals(template_type))
				{
					if(!"".equals(sc.getM_forecast_path()))
						return sc.getM_forecast_path();
				}
				if("historyList".equals(template_type))
				{
					if(!"".equals(sc.getM_h_path()))
						return sc.getM_h_path();
				}
				if("infoList".equals(template_type))
				{
					if(!"".equals(sc.getM_rlist_path()))
						return sc.getM_rlist_path();
				}
				if("infoContent".equals(template_type))
				{
					if(!"".equals(sc.getM_rcontent_list()))
						return sc.getM_rcontent_list();
				}
				System.out.println(sc.getCategory_name()+"  "+sc.getM_rcontent_list());
			}
		}
		return "";
	}
	
	public static void main(String[] args)
	{		
		//System.out.println(getInterViewTemplate("infoContent"));
	}
}
