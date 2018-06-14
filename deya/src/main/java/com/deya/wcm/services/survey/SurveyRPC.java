package com.deya.wcm.services.survey;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.survey.SurveyBean;
import com.deya.wcm.services.Log.LogManager;
import com.yinhai.pdf.SurveyToPdf;
import com.yinhai.webservice.client.PushSurveyServiceClient;
import com.deya.wcm.bean.survey.SurveyAnswer;
import com.deya.util.DateUtil;
import com.deya.wcm.dao.survey.AnswerDAO;
import com.deya.wcm.bean.survey.SurveyAnswerItem;
import org.apache.commons.lang3.StringUtils;

/**
 * 访谈主题前台访问交互类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题前台访问交互类</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SurveyRPC {
//	得到服务器时间
	public static String getCurrentDate()
	{
		return com.deya.util.DateUtil.getCurrentDate();
	}
	
	/**
     * 设置推荐状态
     * @param String ids
     * @param String recommend_flag
     * @return boolean
     * */
	public static boolean updateSurveyRecommend(String ids,String recommend_flag)
	{
		return SurveyService.updateSurveyRecommend(ids,recommend_flag);
	}
	
	/**
     * 得到主题总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSurveyCount(String con,String site_id)
	{
		return SurveyService.getSurveyCount(con,site_id);
	}
	
	/**
     * 得到问卷调查列表
     * @param Map m　组织好的查询，翻页条数等参数
     * @return List　列表
     * */
	public static List getSurveyList(String con,int start_num,int page_size,String site_id)
	{
		return SurveyService.getSurveyList(con, start_num, page_size,site_id);
	}
	
	/**
     * 得到问卷调查主信息
     * @param String s_id
     * @return List　列表
     * */
	public static SurveyBean getSurveyBean(String s_id)
	{
		return SurveyService.getSurveyBean(s_id);
	}
	
	/**
     * 得到问卷调查题目及选项信息
     * @param String s_id
     * @return List　
     * */
	public static List getSurveySubjectBean(String s_id)
	{
		return SurveyService.getSurveySubjectBean(s_id);
	}
	
	/**
     * 得到问卷调查题目信息
     * @param String s_id
     * @return List　
     * */
	public static List getSurveySubjectSingle(String s_id)
	{
		return SurveyService.getSurveySubjectSingle(s_id);
	}
	
	/**
     * 修改问卷调查
     * @param SurveyBean sb　问卷对象
     * @return boolean　true or false
     * */
	public static boolean updateSurvey(SurveyBean sb,List l,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SurveyService.updateSurvey(sb,l,stl);
		}else
			return false;
	}
	
	/**
     * 问卷调查属性设置
     * @param SurveyBean sb　问卷对象
     * @return boolean　true or false
     * */
	public static boolean setSurveyAttr(SurveyBean sb,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SurveyService.setSurveyAttr(sb,stl);
		}else
			return false;
	}
	
	/**
     * 插入问卷调查
     * @param SurveyBean sb　问卷对象
     * @return boolean　true or false
     * */
	public static boolean insertSurvey(SurveyBean sb,List l,HttpServletRequest request)
	{		
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SurveyService.insertSurvey(sb,l,stl);
		}else
			return false;
	}
	/**
     * 插入问卷调查题目
     * @param List l
     * @return boolean　true or false
     * */
	public static boolean insertSurveySub(List l)
	{
		return SurveyService.insertSurveySub(l);
	}
	
	/**
     * 发布撤消问卷
     * @param String ids
     * @param String publish_status 1为发布,0为撤消
     * @param String user_name
     * @return boolean　true or false
     * */
	public static boolean publishSurvey(String ids,String publish_status,String user_name,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SurveyService.publishSurvey(ids, publish_status, user_name,stl);
		}else
			return false;
	}
	
	/**
     * 删除问卷
     * @param M
     * @return boolean　true or false
     * */
	public static boolean deleteSurvey(String ids,String user_name,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return SurveyService.deleteSurvey(ids, user_name,stl);
		}else
			return false;
	}

	/**
	 * 删除问卷
	 * @param M
	 * @return boolean　true or false
	 * */
	public static boolean backSurvey(String s_ids,String ids,HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			if(SurveyToPdf.toPdf(s_ids,request)){
				return SurveyService.backSurvey(ids, stl);
			}else{
				return false;
			}
		}else
			return false;
	}
	
	/********************* 数据统计接口　开始 ********************************/
	/**
     * 得到问卷调查列表总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getStatisticsCount(String con)
	{
		return StatisticsService.getStatisticsCount(con);
	}
	
	/**
     * 得到问卷调查列表
     * @param String con　组织好的查询，翻页条数等参数
     * @param int start_num
     * @param int page_size
     * @return List
     * */
	public static List getStatisticsList(String con,int start_num,int page_size)
	{
		return StatisticsService.getStatisticsList(con,start_num,page_size);
	}
	
	/**
     * 得到所有答卷的统计数据
     * @param String s_id
     * @return List
     * */
	public static Map getStatisticsDataBySurvey(String s_id)
	{
		return StatisticsService.getStatisticsDataBySurvey(s_id,"");
	}
	
	/**
     * 得到所有答卷的统计数据
     * @param String s_id
     * @param String source
     * @return List
     * */
	public static Map getStatisticsDataBySurvey(String s_id,String source)
	{
		return StatisticsService.getStatisticsDataBySurvey(s_id,source);
	}
	
	/**
     * 得到某个选项的答卷文本总数
     * @param Map
     * @return String count
     * */
	public static String getItemTextCount(String s_id,String item_id,String item_value,String is_text,String search_con)
	{
		return StatisticsService.getItemTextCount(s_id, item_id, item_value, is_text,search_con);
	}
	
	/**
     * 得到某个选项的答卷文本总数
     * @param Map
     * @return String count
     * */
	public static List getItemTextList(String s_id,String item_id,String item_value,String is_text,int start_num,int page_size,String search_con)
	{
		return StatisticsService.getItemTextList(s_id, item_id, item_value, is_text,start_num,page_size,search_con);
	}
	
	/**
     * 得到某个答卷详细信息
     * @param String answer_id
     * @return List
     * */
	public static List getAnswerItemDetail(String answer_id)
	{
		return StatisticsService.getAnswerItemDetail(answer_id);
	}
	
	/**
     * 得到某个主题的答卷列表总数
     * @param String s_id 主题ID
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getAnswerListCount(String s_id,String source,String con)
	{
		return StatisticsService.getAnswerListCount(s_id, source,con);
	}
	
	/**
     * 得到某个主题的答卷列表
     * @param String s_id 主题ID
     * @param String con　组织好的查询，翻页条数等参数
     * @param int start_num
     * @param int page_size
     * @return List
     * */
	public static List getAnswerList(String s_id,String source,String con,int start_num,int page_size)
	{
		return StatisticsService.getAnswerList(s_id, source, con, start_num, page_size);
	}
	/********************* 数据统计接口　结束 ********************************/
	
	/********************* 问卷统计接口　开始 ********************************/
	/**
     * 得到分类统计数据
     * @param String start_time
     * @param String end_time
     * @return Map
     * */
	public static Map getSurveyCategoryCount(String start_time,String end_time,String time_type,String site_id)
	{ 
		return SurveyCountServices.getSurveyCategoryCount(start_time,end_time,time_type,site_id);
	}
	
	/**
     * 得到分类统计数据
     * @param String start_time 开始时间
     * @param String end_time 结束时间
     * @param String category_ids 栏目ID
     * @param String count_num 排序条数
     * @return Map
     * */
	public static Map getHotCount(String start_time,String end_time,String category_ids,int count_num,String time_type,String site_id)
	{ 
		return SurveyCountServices.getHotCount(start_time,end_time,category_ids,count_num,time_type,site_id);
	}
	/********************* 问卷统计接口　结束 ********************************/


	public static boolean pushAnswer(String ywlsh,String ids){
		boolean b = true;
		int i = PushSurveyServiceClient.doService(ywlsh,"/survey/view.jsp?s_id="+ids);
		if(i==0){
			b = false;
		}
		return b;
	}

	public static boolean updateAnswer(String sid,String itemId,String itemValue,int num){
		boolean b = false;
		String answerId = AnswerServer.getAnswerByIP(sid,"127.0.0.1");
		if(StringUtils.isEmpty(answerId)){
			answerId = UUID.randomUUID().toString();
			SurveyAnswer answer = new SurveyAnswer();
			answer.setAnswer_id(answerId);
			answer.setS_id(sid);
			answer.setAnswer_time(DateUtil.getCurrentDateTime());
			answer.setIp("127.0.0.1");
			answer.setOperate_time(0);
			answer.setSource("xxfb");
			answer.setUser_name("管理员");
			b = AnswerDAO.insertSurveyAnswer(answer);
		}else{
			b = true;
		}
		if(b){
			for(int i=0;i<num;i++){
				SurveyAnswerItem item = new SurveyAnswerItem();
				item.setAnswer_id(answerId);
				item.setS_id(sid);
				item.setItem_id(itemId);
				item.setItem_value(itemValue);
				item.setItem_text("");
				b = AnswerDAO.insertSurveyAnswerItem(item);
			}
		}else{
			b =false;
		}
		return b;
	}
}
