package com.deya.wcm.dao.survey;
import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.survey.*;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
public class SurveyCategoryDAO {
	/**
     * 得到问卷调查分类总数
     * @param String con
     * @return String
     * */
	public static String getSurveyCategoryCount(Map m)
	{
		return  DBManager.getString("getSurveyCategoryCount", m);
	}
	
	/**
     * 得到所有问卷调查分类列表
     * @param Map m
     * @return List
     * */
	@SuppressWarnings("unchecked")
	public static List<SurveyCategory> getAllSurveyCategoryList()
	{		
		return DBManager.queryFList("getAllSurveyCategoryList", "");
	}
	
	/**
     * 得到问卷调查分类列表
     * @param Map m
     * @return List
     * */
	public static List getSurveyCategoryList(Map m)
	{		
		return DBManager.queryFList("getSurveyCategoryList", m);
	}
	
	/**
     * 得到问卷调查分类对象
     * @param String category_id
     * @return List
     * */
	public static SurveyCategory getSurveyCategoryBean(String category_id)
	{
		return (SurveyCategory)DBManager.queryFObj("getSurveyCategoryBean", category_id);
	}
	
	/**
     * 得到所有巳发布过的分类对象
     * @param String category_id
     * @return List
     * */
	public static List getAllSurveyCategoryName()
	{
		return DBManager.queryFList("getAllSurveyCategoryName","");
	}
	
	/**
     * 插入调查问卷分类
     * @param SurveyCategory sc
     * @return boolean
     * */
	public static boolean insertSurveyCategory(SurveyCategory sc,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.SURVEY_CATEGORY_TABLE_NAME);
		sc.setId(id);
		if(DBManager.insert("insertSurveyCategory", sc))
		{
			PublicTableDAO.insertSettingLogs("添加","调查问卷分类",id+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改调查问卷分类
     * @param SurveyCategory sc
     * @return boolean
     * */
	public static boolean updateSurveyCategory(SurveyCategory sc,SettingLogsBean stl)
	{		
		if(DBManager.update("updateSurveyCategory", sc))
		{
			PublicTableDAO.insertSettingLogs("修改","调查问卷分类",sc.getId()+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 根据分类ID得到该分类下的主题个数,用于删除分类时判断下面是否有主题记录
     * @param int id
     * @return int　条数
     * */
	public static String getSurveyCountByCategoryID(int id)
	{
		return DBManager.getString("getSurveyCountByCategoryID", id);
	}
	
	/**
     * 删除调查问卷分类
     * @param Map m
     * @return boolean
     * */
	public static boolean deleteSurveyCategory(Map m,SettingLogsBean stl)
	{		
		if(DBManager.update("deleteSurveyCategory", m))
		{
			PublicTableDAO.insertSettingLogs("删除","调查问卷分类",m.get("ids")+"",stl);
			return true;
		}
		else
			return false;
	}
	
	/**
     * 修改调查问卷分类发布状态
     * @param String ids
     * @return boolean
     * */
	public static boolean updateSurveyCategoryPublishStatus(Map m,SettingLogsBean stl)
	{		
		if(DBManager.update("updateSurveyCategoryPublishStatus", m))
		{
			PublicTableDAO.insertSettingLogs("修改","调查问卷分类发布状态",m.get("ids")+"",stl);
			return true;
		}
		else
			return false;
	}
}
