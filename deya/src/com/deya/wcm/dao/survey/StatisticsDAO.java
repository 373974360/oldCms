package com.deya.wcm.dao.survey;
import java.util.*;

import com.deya.wcm.bean.survey.*;
import com.deya.wcm.db.DBManager;
/**
 * 答卷统计数据处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 答卷统计数据处理</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class StatisticsDAO {
	/**
     * 得到问卷调查列表总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getStatisticsCount(String con)
	{
		return DBManager.getString("getStatisticsCount", con);
	}
	
	/**
     * 得到问卷调查答卷列表
     * @param Map m 查询条件
     * @return List
     * */
	public static List getStatisticsList(Map m)
	{
		return DBManager.queryFList("getStatisticsList", m);
	}
	
	/**
     * 得到所有答卷的统计数据
     * @param String s_id
     * @return String
     * */
	public static String getStatisticsCountBySurvey(String s_id)
	{
		return DBManager.getString("getStatisticsCountBySurvey", s_id);
	}
	
	/**
     * 得到所有答卷的统计数据
     * @param String s_id
     * @return List
     * */
	public static List getStatisticsDataBySurvey(Map m)
	{
		return DBManager.queryFList("getStatisticsDataBySurvey", m);
	}
	
	/**
     * 得到某个选项的答卷文本总数
     * @param Map
     * @return String count
     * */
	public static String getItemTextCount(Map m)
	{
		return DBManager.getString("getItemTextCount", m);
	}
	
	/**
     * 得到某个投票选项的答卷总数
     * @param m
     * @return String count
     * */
	public static String getVoteCountBySurveyItem(Map m)
	{
		return DBManager.getString("getVoteCountBySurveyItem", m);
	}
	
	/**
     * 得到某个投票选项的答卷计数汇总
     * @param m
     * @return String count
     * */
	public static List getVoteTotalBySurveyItem(Map m)
	{
		return DBManager.queryFList("getVoteTotalBySurveyItem", m);
	}
	
	/**
     * 得到某个选项的答卷文本列表
     * @param Map
     * @return List
     * */
	public static List getItemTextList(Map m)
	{
		return DBManager.queryFList("getItemTextList", m);
	}
	
	public static List getItemTextList2(Map m)
	{
		return DBManager.queryFList("getItemTextList2", m);
	}
	
	/**
     * 得到某个答卷详细信息
     * @param String answer_id
     * @return List
     * */
	public static List getAnswerItemDetail(String answer_id)
	{
		return DBManager.queryFList("getAnswerItemDetail", answer_id);
	}
	
	/**
     * 得到某个主题的答卷列表总数
     * @param Map m 查询条件
     * @return String　条数
     * */
	public static String getAnswerListCount(Map m)
	{
		return DBManager.getString("getAnswerListCount", m);
	}
	

	/**
     * 得到某个主题的答卷列表
     * @param Map m 查询条件
     * @return List
     * */
	public static List getAnswerList(Map m)
	{
		return DBManager.queryFList("getAnswerList", m);
	}
	
	public static List getAnswerList2(Map m)
	{
		return DBManager.queryFList("getAnswerList2", m);
	}
}
