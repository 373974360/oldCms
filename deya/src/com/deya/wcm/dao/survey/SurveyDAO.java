package com.deya.wcm.dao.survey;
import java.util.*;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.survey.*;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
/**
 * 问卷调查数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 问卷调查数据处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SurveyDAO {
	/**
     * 得到推荐列表(前台使用)
     * @param 
     * @return List
     * */
	public static List getSurveyRecommendList(Map m)
	{
		return DBManager.queryFList("getSurveyRecommendList",m);
	}
	
	/**
     * 得到推荐列表总数(前台使用)
     * @param 
     * @return List
     * */
	public static String getSurveyRecommendListCount(Map m)
	{
		return DBManager.getString("getSurveyRecommendListCount",m);
	}
	
	/**
     * 得到问卷调查列表总数
     * @param Map m　组织好的查询，翻页条数等参数
     * @return String　列表
     * */
	public static String getSurveyListCountBrowser(Map m)
	{	
		return DBManager.getString("getSurveyListCountBrowser", m);
	}
	
	/**
     * 设置推荐状态
     * @param Map m
     * @return boolean
     * */
	public static boolean updateSurveyRecommend(Map m)
	{
		return DBManager.update("updateSurveyRecommend", m);
	}
	
	/**
     * 得到问卷调查列表总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSurveyCount(Map m)
	{
		return DBManager.getString("getSurveyCount", m);
	}
	
	/**
     * 得到问卷调查列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	public static List getSurveyList(Map m)
	{		
		List l = DBManager.queryFList("getSurveyList", m);
		return l;
	}
	
	/**
     * 得到问卷调查主信息
     * @param String s_id
     * @return SurveyBean
     * */
	public static SurveyBean getSurveyBean(String s_id)
	{
		return (SurveyBean)DBManager.queryFObj("getSurveyBean", s_id);
	}
	
	/**
     * 得到问卷调查主信息
     * @param String s_id
     * @return SurveyBean
     * */
	public static List getAllSurveyObjBYPublish()
	{
		return DBManager.queryFList("getAllSurveyObjBYPublish", "");
	}
	
	/**
     * 得到问卷调查题目及选项信息
     * @param String s_id
     * @return List　
     * */
	public static List getSurveySubjectBean(String s_id)
	{
		return DBManager.queryFList("getSurveySubjectBean", s_id);
	}
	
	/**
     * 得到问卷调查题目信息
     * @param String s_id
     * @return List　
     * */
	public static List getSurveySubjectSingle(String s_id)
	{
		return DBManager.queryFList("getSurveySubjectSingle", s_id);
	}
	
	/**
     * 插入问卷调查
     * @param SurveyBean sb　问卷对象
     * @return boolean　true or false
     * */
	public static boolean insertSurvey(SurveyBean sb,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.SURVEY_TABLE_NAME);
		sb.setId(id);
		if(DBManager.insert("insertSurvey", sb))
		{
			PublicTableDAO.insertSettingLogs("添加","问卷调查",id+"",stl);
			return true;
		}else
			return false;	
	}
	
	/**
     * 修改问卷调查
     * @param SurveyBean sb　问卷对象
     * @return boolean　true or false
     * */
	public static boolean updateSurvey(SurveyBean sb,SettingLogsBean stl)
	{
		if(DBManager.update("updateSurvey", sb))
		{
			PublicTableDAO.insertSettingLogs("修改","问卷调查",sb.getS_id(),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 问卷调查属性设置
     * @param SurveyBean sb　问卷对象
     * @return boolean　true or false
     * */
	public static boolean setSurveyAttr(SurveyBean sb,SettingLogsBean stl)
	{
		if(DBManager.update("setSurveyAttr", sb))
		{
			PublicTableDAO.insertSettingLogs("修改","问卷调查属性",sb.getS_id(),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除问卷调查题目
     * @param String survey_id
     * @return boolean　true or false
     * */
	public static boolean deleteSubjectItem(String survey_id,SettingLogsBean stl)
	{
		try{ 
			DBManager.delete("survey.deleteSubject", survey_id);
			DBManager.delete("deleteSubjectChild", survey_id);
			DBManager.delete("deleteSubjectItem", survey_id);
			
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * 插入问卷题目
     * @param SurveySub sub　问卷对象
     * @return boolean　true or false
     * */
	public static boolean insertSurveySub(SurveySub sub,SettingLogsBean stl)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.SURVEY_SUB_TABLE_NAME);
		sub.setId(id);
		return DBManager.insert("insertSurveySub", sub);
	}
	
	/**
     * 插入问卷题目选项
     * @param SurveySuvItem item　问卷对象
     * @return boolean　true or false
     * */
	public static boolean insertSurveySubItem(SurveySuvItem item)
	{
		return DBManager.insert("insertSurveySubItem", item);
	}
	

	/**
     * 插入子选项
     * @param SurveyChildItem ci　问卷对象
     * @return boolean　true or false
     * */
	public static boolean insertSurveyChildItem(SurveyChildItem ci)
	{
		return DBManager.insert("insertSurveyChildItem", ci);
	}
	
	/**
     * 发布撤消问卷
     * @param M
     * @return boolean　true or false
     * */
	public static boolean publishSurvey(Map m,SettingLogsBean stl)
	{
		if(DBManager.update("publishSurvey",m))
		{
			PublicTableDAO.insertSettingLogs("修改","问卷调查发布状态",(String)m.get("ids"),stl);
			return true;
		}else
			return false;
	}
	
	/**
     * 删除问卷
     * @param M
     * @return boolean　true or false
     * */
	public static boolean deleteSurvey(Map m,SettingLogsBean stl)
	{		
		if(DBManager.update("deleteSurvey",m))
		{
			PublicTableDAO.insertSettingLogs("删除","问卷调查",(String)m.get("ids"),stl);
			return true;
		}else
			return false;
	}
}
