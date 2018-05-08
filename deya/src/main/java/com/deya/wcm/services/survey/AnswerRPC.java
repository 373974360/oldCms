package com.deya.wcm.services.survey;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.util.FormatUtil;
import com.deya.wcm.bean.survey.SurveyAnswer;
import com.deya.wcm.bean.survey.SurveyBean;

public class AnswerRPC {
	//得到服务器时间
	public static String getCurrentDate()
	{
		return com.deya.util.DateUtil.getCurrentDate();
	}
	
	/**
     * 判断该IP上次提交的时间
     * @param String s_id 问卷ID
     * @param String s_time 间隔时间
     * @param String ip
     * @return boolean 返回true可以提交，返回false时间未到，不能提交
     * */
	public static boolean isSubmitTimeout(String s_id,String s_time,HttpServletRequest request)
	{
		try{
			return AnswerServer.isSubmitTimeout(s_id,s_time,request.getRemoteAddr());
		}catch(Exception e){
			e.printStackTrace();
			return true;
		}
		//return AnswerServer.isSubmitTimeout(s_id,s_time,request.getRemoteAddr());
	}
	
	/**
     * 得到该IP的提交的次数
     * @param String s_id 问卷ID
     * @return String　条数
     * */
	public static String getAnswerCountByIP(String s_id,HttpServletRequest request)
	{
		return AnswerServer.getAnswerCountByIP(s_id,FormatUtil.getIpAddr(request));
	}
	
	/**
     * 得到问卷调查列表总条数
     * @param String　con 查询条件
     * @return String　条数
     * */
	public static String getSurveyCount_browser(String con)
	{
		return AnswerServer.getSurveyCount_browser(con);
	}
	
	/**
     * 得到问卷调查列表
     * @param SurveyAnswer sa
     * @return List
     * */
	public static List getSurveyList_browser(String con,int start_num,int page_size)
	{
		return AnswerServer.getSurveyList_browser(con, start_num, page_size);
	}
	
	/**
     * 插入答卷
     * @param SurveyAnswer sa
     * @return boolean
     * */
	public static String insertSurveyAnswer(SurveyAnswer sa,String auth_code,HttpServletRequest request)
	{
		SurveyBean sb = SurveyRPC.getSurveyBean(sa.getS_id());
		if(sb.getIs_checkcode() == 1)
		{
			String codeSession = (String)request.getSession().getAttribute("valiCode");
			if(!codeSession.equals(auth_code))
			{
				return "coderror";
			}
		}
		//判断是否有IP限制		
		if(sb.getIp_fre() != 0)
		{
			if(Integer.parseInt(getAnswerCountByIP(sa.getS_id(),request)) > sb.getIp_fre())
			{
				return "ipfull";
			}
		}
		if(sb.getSpacing_interval() != null && !"".equals(sb.getSpacing_interval()))
		{
			if(!isSubmitTimeout(sa.getS_id(),sb.getSpacing_interval(),request))
			{
				return "timeout";
			}
		}
		sa.setIp(FormatUtil.getIpAddr(request));
		boolean b = AnswerServer.insertSurveyAnswer(sa);
		if(b)
		{
			request.getSession().setAttribute("valiCode", "");
			return "true";
		}else
			return "false";
	}

	/**
     * 插入答卷
     * @param SurveyAnswer sa
     * @return boolean
     * */
	public static boolean insertSurveyAnswerBefore(SurveyAnswer sa,HttpServletRequest request)
	{
		sa.setIp(request.getRemoteAddr());
		return AnswerServer.insertSurveyAnswer(sa);
	}
	
	/**
     * 得到某个投票选项的答卷计数汇总 用于在答卷上显示投票数据
     * @param String s_id  问卷ID
     * @param String item_id 选项ID
     * @return String count
     * */
	public static Map getVoteTotalBySurveyItem(String s_id,String item_id)
	{
		return StatisticsService.getVoteTotalBySurveyItem(s_id, item_id);
	}
}
