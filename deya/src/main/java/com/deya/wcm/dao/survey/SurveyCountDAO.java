package com.deya.wcm.dao.survey;

import java.util.List;
import java.util.Map;

import com.deya.wcm.db.DBManager;

public class SurveyCountDAO {
	/**
     * 得到所有巳发布过的分类对象
     * @param 
     * @return String
     * */
	public static List getAllSurveyCategory(String site_id)
	{
		return  DBManager.queryFList("getAllSurveyCategory", site_id);
	}
	
	/**
     * 按分类下的问卷数统计
     * @param 
     * @return String
     * */
	public static List getSurveyCategoryCount(Map m)
	{
		return  DBManager.queryFList("getSurveyCategoryCount2", m);
	}
	
	/**
     * 按分类下的问卷所有主题数统计
     * @param 
     * @return String
     * */
	public static List getSurveySubjectCount(Map m)
	{
		return  DBManager.queryFList("getSurveySubjectCount", m);
	}
	
	/**
     * 按分类下的问卷所有答卷数统计
     * @param 
     * @return String
     * */
	public static List getSurveyAnswerCount(Map m)
	{
		return  DBManager.queryFList("getSurveyAnswerCount", m);
	}
	
	/**
     * 得到最热问卷答卷统计
     * @param 
     * @return String
     * */
	public static List getHotCount_answer(Map m)
	{
		return  DBManager.queryFList("getHotCount_answer", m);
	}
	
	/**
     * 得到最热问卷问卷统计
     * @param 
     * @return String
     * */
	public static List getSurveySubjectCountBySub(Map m)
	{
		return  DBManager.queryFList("getSurveySubjectCountBySub", m);
	}
}
