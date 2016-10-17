package com.deya.wcm.services.survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.survey.SurveyCategory;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.survey.SurveyCategoryDAO;
import com.deya.util.DateUtil;

public class SurveyCategoryService implements ISyncCatch{
	private static Map<String,SurveyCategory> cat_map = new HashMap<String,SurveyCategory>();
	
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
		List<SurveyCategory> l = SurveyCategoryDAO.getAllSurveyCategoryList();
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				cat_map.put(l.get(i).getCategory_id(), l.get(i));
			}
		}
	}
	
	public static void reloadSurveyCategory()
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.survey.SurveyCategoryService");
	}
	
	/**
     * 得到问卷调查分类列表(前台使用)
     * @param String con
     * @param int start_num
     * @param int page_size
     * @return List
     * */
	public static List getSurveyCategoryListBrowser(String con,int start_num,int page_size,String site_id)
	{
		Map m = new HashMap();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		con += " and publish_status = 1 ";
		m.put("con", con);	
		m.put("site_id", site_id);	
		return SurveyCategoryDAO.getSurveyCategoryList(m);
	}
	
	/**
     * 得到问卷调查分类总数
     * @param String con
     * @return String
     * */
	public static String getSurveyCategoryCount(String con,String site_id)
	{		
		Map m = new HashMap();		
		m.put("con", con);
		m.put("site_id", site_id);	
		return SurveyCategoryDAO.getSurveyCategoryCount(m);
	}
	
	/**
     * 得到问卷调查分类列表
     * @param String con
     * @param int start_num
     * @param int page_size
     * @return List
     * */
	public static List getSurveyCategoryList(String con,int start_num,int page_size,String site_id)
	{
		Map m = new HashMap();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("con", con);
		m.put("site_id", site_id);	
		return SurveyCategoryDAO.getSurveyCategoryList(m);
	}
	
	/**
     * 得到问卷调查分类对象
     * @param String category_id
     * @return List
     * */
	public static SurveyCategory getSurveyCategoryBean(String category_id)
	{
		if(cat_map.containsKey(category_id))
		{
			return cat_map.get(category_id);
		}else
		{
			SurveyCategory sc = SurveyCategoryDAO.getSurveyCategoryBean(category_id);
			if(sc != null)
			{
				cat_map.put(category_id, sc);
				return sc;
			}else
				return null;
		}
	}
	
	/**
     * 得到所有巳发布过的分类对象
     * @param String category_id
     * @return List
     * */
	public static List getAllSurveyCategoryName(String site_id)
	{
		List<SurveyCategory> l = new ArrayList<SurveyCategory>();
		Set<String> s =  cat_map.keySet();
		for(String i : s)
		{
			SurveyCategory sc = cat_map.get(i);
			if(site_id.equals(sc.getSite_id()) && sc.getIs_delete() == 0 && sc.getPublish_status() == 1)
				l.add(sc);
		}
		return l;
	}
	
	/**
     * 插入调查问卷分类
     * @param SurveyCategory sc
     * @return boolean
     * */
	public static boolean insertSurveyCategory(SurveyCategory sc,SettingLogsBean stl)
	{
		String c_time = DateUtil.getCurrentDateTime();
		sc.setCategory_id(UUID.randomUUID().toString());
		if(sc.getPublish_status() == 1)
			sc.setPublish_time(c_time);
		sc.setAdd_time(c_time);
		if(SurveyCategoryDAO.insertSurveyCategory(sc,stl))
		{
			reloadSurveyCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 修改问卷调查分类
     * @param SurveyCategory sc
     * @return boolean
     * */
	public static boolean updateSurveyCategory(SurveyCategory sc,SettingLogsBean stl)
	{//如果发布状态为１，判断发布时间有没有，没有的话加上发布时间，如果不为１，清空发布时间		
		String c_time = DateUtil.getCurrentDateTime();
		if(sc.getPublish_status() == 1)
		{
			if("".equals(sc.getPublish_time()))
				sc.setPublish_time(c_time);
		}			
		else
		{
			sc.setPublish_time("");
		}
		sc.setUpdate_time(c_time);
		if(SurveyCategoryDAO.updateSurveyCategory(sc,stl))
		{
			reloadSurveyCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 根据分类ID得到该分类下的主题个数,用于删除分类时判断下面是否有主题记录
     * @param int id
     * @return int　条数
     * */
	public static String getSurveyCountByCategoryID(int id)
	{
		return SurveyCategoryDAO.getSurveyCountByCategoryID(id);
	}
	

	/**
     * 删问卷调查分类
     * @param String ids
     * @param String user_name
     * @return boolean　true or false
     * */
	public static boolean deleteSurvey(String ids,String user_name,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("user_name", user_name);
		m.put("current_time", DateUtil.getCurrentDateTime());
		if(SurveyCategoryDAO.deleteSurveyCategory(m,stl))
		{
			reloadSurveyCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 发布撤消问卷分类
     * @param String ids
     * @param String publish_status 1为发布,0为撤消
     * @param String user_name
     * @return boolean　true or false
     * */
	public static boolean updateSurveyCategoryPublishStatus(String ids,String publish_status,String user_name,SettingLogsBean stl)
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("ids", ids);
		m.put("user_name", user_name);
		m.put("current_time", DateUtil.getCurrentDateTime());
		m.put("publish_status", publish_status);
		if(SurveyCategoryDAO.updateSurveyCategoryPublishStatus(m,stl))
		{
			reloadSurveyCategory();
			return true;
		}else
			return false;
	}
	
	/**
     * 查找所有分类中任意的模板ID,为应对传入的分类ID为空而找不到对应的模板
     * @param String template_type
     * @return String
     * */
	public static String getSurveyTemplate(String template_type,String site_id)
	{ 
		Set<String> s = cat_map.keySet();
		for(String i : s)
		{
			SurveyCategory sc = cat_map.get(i);
			if(site_id.equals(sc.getSite_id()))
			{
				if("list".equals(template_type))
				{
					if(!"".equals(sc.getTemplate_list_path()))
						return sc.getTemplate_list_path();
				}
				if("content".equals(template_type))
				{
					if(!"".equals(sc.getTemplate_content_path()))
						return sc.getTemplate_content_path();
				}
				if("result".equals(template_type))
				{
					if(!"".equals(sc.getTemplate_result_path()))
						return sc.getTemplate_result_path();
				}
			}
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		System.out.println(getAllSurveyCategoryName("11111ddd"));
	}
}
