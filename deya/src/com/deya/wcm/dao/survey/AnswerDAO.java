package com.deya.wcm.dao.survey;
import java.util.*;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.survey.*;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;
/**
 * 问卷前台数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 问卷前台数据处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class AnswerDAO {
	/**
     * 得到该IP最后的提交时间
     * @param Map m
     * @return String　条数
     * */
	public static String getLastAnswerTimeByIP(Map m)
	{
		try{
			return  DBManager.getString("getLastAnswerTimeByIP", m);
		}catch(Exception e)
		{
			//e.printStackTrace();
			return "";
		}
	}
	
	/**
     * 得到该IP的提交的次数
     * @param Map m
     * @return String　条数
     * */
	public static String getAnswerCountByIP(Map m)
	{
		return  DBManager.getString("getAnswerCountByIP", m);
	}
	
	/**
     * 得到问卷调查列表总数
     * @param 
     * @return String
     * */
	public static String getSurveyCount_browser()
	{
		return  DBManager.getString("getSurveyCount_browser", "");
	}
	
	/**
     * 得到问卷调查列表
     * @param Map m
     * @return List
     * */
	public static List getSurveyList_browser(Map m)
	{		
		List l = DBManager.queryFList("getSurveyList_browser", m);
		return l;
	}
	
	/**
     * 插入答卷主表
     * @param SurveyAnswer sa
     * @return boolean
     * */
	public static boolean insertSurveyAnswer(SurveyAnswer sa)
	{
		int id = PublicTableDAO.getIDByTableName(PublicTableDAO.SURVEY_ANSWER_TABLE_NAME);
		sa.setId(id);
		return DBManager.insert("insertAnswer", sa);
	}
	
	/**
     * 插入答卷主表
     * @param SurveyAnswer sa
     * @return boolean
     * */
	public static boolean insertSurveyAnswerItem(SurveyAnswerItem item)
	{
		return DBManager.insert("insertAnswerItem", item);		
	}
}
