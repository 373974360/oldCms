package com.deya.wcm.services.survey;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.bean.survey.*;
import com.deya.wcm.dao.survey.*;
import com.deya.wcm.db.DBManager;
import com.deya.util.DateUtil;
import java.util.*;
/**
 * 答卷逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 答卷的逻辑处理
 * </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class AnswerServer {
	
	/**
     * 判断该IP上次提交的时间
     * @param String s_id 问卷ID
     * @param String s_time 间隔时间
     * @param String ip
     * @return boolean 返回true可以提交，返回false时间未到，不能提交
     * */
	public static boolean isSubmitTimeout(String s_id,String s_time,String ip)
	{
		if(s_time != null && !"".equals(s_time))
		{
			Map m = new HashMap();
			m.put("ip", ip);
			m.put("s_id", s_id);
			String last_time = AnswerDAO.getLastAnswerTimeByIP(m);
			//System.out.println(s_time);
			//System.out.println(last_time);
			//System.out.println(DateUtil.timestampToDate(DateUtil.dateToTimestamp(last_time)+getSpacingIntervalTimes(s_time),"yyyy-MM-dd hh:mm:ss"));
			//System.out.println(DateUtil.timestampToDate(DateUtil.dateToTimestamp(),"yyyy-MM-dd hh:mm:ss"));
			if(last_time != null && !"".equals(last_time))
			{	
				try{
					return DateUtil.dateToTimestamp() > DateUtil.dateToTimestamp(last_time)+getSpacingIntervalTimes(s_time);
				}catch(Exception e)
				{
					return true;
				}
			}
		}
		return true;
	}
	
	public static Long getSpacingIntervalTimes(String s_time)
	{
		Long tL = 0l;
		String type = s_time.substring(0,1);
		int ti = Integer.parseInt(s_time.substring(1));
		if("d".equals(type))
			tL = ti * 24 * 60 * 60 * 1000L;
		if("h".equals(type))
			tL = ti * 60 * 60 * 1000L;
		if("m".equals(type))
			tL = ti * 60 * 1000L;
		
		return tL;
	}
	
	/**
     * 得到该IP的提交的次数
     * @param String ip
     * @return String　条数
     * */
	public static String getAnswerCountByIP(String s_id,String ip)
	{
		Map m = new HashMap();
		m.put("ip", ip);
		m.put("s_id", s_id);
		return AnswerDAO.getAnswerCountByIP(m);
	}
	
	/**
     * 得到问卷调查列表总数
     * @param 
     * @return String
     * */
	public static String getSurveyCount_browser(String con)
	{
		return AnswerDAO.getSurveyCount_browser();
	}
	
	/**
     * 得到问卷调查列表
     * @param SurveyAnswer sa
     * @return List
     * */
	public static List getSurveyList_browser(String con,int start_num,int page_size)
	{
		Map m = new HashMap();
		m.put("start_num", start_num);//设置启始条数
		m.put("page_size", page_size);//设置结束条数
		m.put("con", con);
		return AnswerDAO.getSurveyList_browser(m);
	}
	
	/**
     * 插入答卷
     * @param SurveyAnswer sa
     * @return boolean
     * */
	public static synchronized boolean insertSurveyAnswer(SurveyAnswer sa)
	{		
		try{
			String answer_id = UUID.randomUUID().toString();
			sa.setAnswer_id(answer_id);
			sa.setAnswer_time(DateUtil.getCurrentDateTime());
			
			AnswerDAO.insertSurveyAnswer(sa);
			List <SurveyAnswerItem> item_list = sa.getItem_list();
			
			if(item_list != null && item_list.size() > 0)
			{
				for(int i=0;i<item_list.size();i++)
				{
					SurveyAnswerItem item = item_list.get(i);
					item.setS_id(sa.getS_id());
					item.setAnswer_id(answer_id);
					AnswerDAO.insertSurveyAnswerItem(item);
				}
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String args[])
	{
		System.out.println(isSubmitTimeout("87095242-86f3-4fcb-8d1c-254d955d0f05","m2","192.168.12.80"));
	}
}
