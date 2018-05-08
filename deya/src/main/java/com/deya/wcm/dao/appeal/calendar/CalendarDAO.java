package com.deya.wcm.dao.appeal.calendar;

import java.util.List;
import java.util.Map;

import com.deya.wcm.bean.appeal.calendar.CalendarBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.db.DBManager;

/**
 * 节假日管理处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 节假日管理处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class CalendarDAO {

	/**
	 * 得到节假日列表
	 * @param
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<CalendarBean> getAllCalendarList()
	{
		return DBManager.queryFList("getAllCalendar", "");
	}
	
	/**
	 * 根据节假日id得到对象
	 * @param ca_id
	 * @return CalendarBean
	 */
	public static CalendarBean getCalendarBean(int ca_id)
	{
		return (CalendarBean)DBManager.queryFObj("getCalendarBean", ca_id);
 	}
	
	public static String  getCalendarCount()
	{
		return DBManager.getString("getCalendarCount","");
	}
	/**
	 * 新增节假日
	 * @param calebean
	 * @param stl
	 * @return  boolean
	 */
	public static boolean insertCalendar(CalendarBean calebean,SettingLogsBean stl)
	{
		if(DBManager.insert("insert_Calendar", calebean)){
			PublicTableDAO.insertSettingLogs("添加","节假日",calebean.getCa_id()+"",stl);
		    return true;
		}else{
			return false;
		}
	}
	/**
	 * 修改节假日信息
	 * @param calebean
	 * @param stl
	 * @return boolean 
	 */
	public static boolean updateCalendar(CalendarBean calebean,SettingLogsBean stl)
	{
		if(DBManager.update("update_Calendar", calebean)){
			PublicTableDAO.insertSettingLogs("修改","节假日",calebean.getCa_id()+"",stl);
		    return true;
		}else{
		    return false;	
		}
	}
	/**
	 * 删除节假日信息
	 * @param ca_id:节假日ID
	 * @param stl
	 * @return boolean
	 */
	public static boolean deleteCalendar(Map<String, String> mp,SettingLogsBean stl){
		 
		 
		if(DBManager.delete("delete_Calendar_ids", mp)){
			PublicTableDAO.insertSettingLogs("删除","节假日",mp.get("ca_id"),stl);
			return true;
		}else
			return false;
	}
	
	
	
	
	
}
