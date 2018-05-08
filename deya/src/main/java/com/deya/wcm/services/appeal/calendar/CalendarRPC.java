package com.deya.wcm.services.appeal.calendar;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.deya.wcm.bean.appeal.calendar.CalendarBean;
import com.deya.wcm.bean.appeal.purpose.PurposeBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.services.Log.LogManager;
import com.deya.wcm.services.appeal.purpose.PurposeManager;
 

 

/**
 * 节假日管理接口类.
 * <p>Title: CicroDate</p>
 * <p>Description: 节假日管理接口类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class CalendarRPC {
	
	/**
	 * 得到节假日List
	 * @return list
	 */
	public static List<CalendarBean> getCalendarList()
	{
		return  CalendarManager.getAllCalendarList();
	}
	
	/**
     * 得到节假日总数
     * @param
     * @return String
     * */
	public static String getCalendarCount()
	{
		return CalendarManager.getCalendarCount();
	}
	/**
	 * 根据id得到对象
	 * @param ca_id
	 * @return CalendarBean
	 */
	public static CalendarBean getCalendarBean(int ca_id)
	{
		return CalendarManager.getCalendarBean(ca_id);
 	}
	/**
     * 得到新增ID,用于添加记录
     * @param
     * @return String
     * */
	public static int getInsertID()
	{
		return CalendarManager.getInsertID();
	}
	
	/**
	 * 插入节假日信息
	 * @param mcb	节假日信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean insertCalendar(CalendarBean bean, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CalendarManager.insertCalendar(bean, stl);
		}
		else
		{
			return false;
		}
	}
	/**
	 * 修改节假日信息
	 * @param mcb	节假日信息
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean updateCalendar(CalendarBean bean, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CalendarManager.updateCalendar(bean, stl);
		}
		else
		{
			return false;
		}
	}
	/**
	 * 删除节假日信息
	 * @param mp	删除条件
	 * @param request
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteCalendar(Map<String, String> mp, HttpServletRequest request)
	{
		SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
		if(stl != null)
		{
			return CalendarManager.deleteCalendar(mp, stl);
		}
		else
		{
			return false;
		}
	}
	
}
