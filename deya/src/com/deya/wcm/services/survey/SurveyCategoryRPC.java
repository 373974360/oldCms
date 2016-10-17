package com.deya.wcm.services.survey;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.survey.SurveyCategory;
import com.deya.wcm.services.Log.LogManager;

public class SurveyCategoryRPC {
	/**
     * 得到问卷调查分类总数
     * @param String con
     * @return String
     * */
	public static String getSurveyCategoryCount(String con,String site_id)
	{
		return SurveyCategoryService.getSurveyCategoryCount(con,site_id);
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
		return SurveyCategoryService.getSurveyCategoryList(con, start_num, page_size,site_id);
	}
	
	/**
     * 得到问卷调查分类对象
     * @param String category_id
     * @return List
     * */
	public static SurveyCategory getSurveyCategoryBean(String category_id)
	{
		return SurveyCategoryService.getSurveyCategoryBean(category_id);
	}
	
	/**
     * 得到所有巳发布过的分类对象
     * @param String category_id
     * @return List
     * */
	public static List getAllSurveyCategoryName(String site_id)
	{
		return SurveyCategoryService.getAllSurveyCategoryName(site_id);
	}
	
	/**
     * 插入调查问卷分类
     * @param SurveyCategory sc
     * @return boolean
     * */
	public static boolean insertSurveyCategory(SurveyCategory sc,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SurveyCategoryService.insertSurveyCategory(sc,stl);
		}else
			return false;
	}
	
	/**
     * 修改问卷调查分类
     * @param SurveyCategory sc
     * @return boolean
     * */
	public static boolean updateSurveyCategory(SurveyCategory sc,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SurveyCategoryService.updateSurveyCategory(sc,stl);
		}else
			return false;
	}
	
	/**
     * 删问卷调查分类
     * @param String ids
     * @param String user_name
     * @return boolean　true or false
     * */
	public static boolean deleteSurveyCategory(String ids,String user_name,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SurveyCategoryService.deleteSurvey(ids, user_name,stl);
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
	public static boolean updateSurveyCategoryPublishStatus(String ids,String publish_status,String user_name,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SurveyCategoryService.updateSurveyCategoryPublishStatus(ids, publish_status, user_name,stl);
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
		return SurveyCategoryService.getSurveyCountByCategoryID(id);
	}
}
