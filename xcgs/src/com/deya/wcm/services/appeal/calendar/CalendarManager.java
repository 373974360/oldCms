package com.deya.wcm.services.appeal.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.deya.util.CalendarUtil;
import com.deya.wcm.bean.appeal.calendar.CalendarBean;
import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.catchs.ISyncCatch;
import com.deya.wcm.catchs.SyncCatchHandl;
import com.deya.wcm.dao.PublicTableDAO;
import com.deya.wcm.dao.appeal.calendar.CalendarDAO;


/**
 * 节假日管理逻辑处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 节假日管理逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class CalendarManager implements ISyncCatch{
	
	private static TreeMap<Integer,CalendarBean> cal_map = new TreeMap<Integer, CalendarBean>();	//分类缓存
	
	static{
		reloadCatchHandl();
	}
	
	public void reloadCatch()
	{
		reloadCatchHandl();
	}
	
	public static void reloadCatchHandl()
	{
		cal_map.clear();
		List<CalendarBean> lt = CalendarDAO.getAllCalendarList();
		if (lt != null && lt.size() > 0) 
		{
			for (int i = 0; i < lt.size(); i++) 
			{				
				cal_map.put(lt.get(i).getCa_id(), lt.get(i));  
			}  
		}
	}
	/**
	 * 初始化
	 * @param
	 * @return
	 */
	public static void reloadCalendar() 
	{
		SyncCatchHandl.reladCatchForRMI("com.deya.wcm.services.appeal.calendar.CalendarManager");
	}
	/**
	 * 得到节假日列表
	 * @return	list节假日列表
	 */
	public static List<CalendarBean> getAllCalendarList()
	{
		List<CalendarBean> lt = new ArrayList<CalendarBean>();
		Iterator<CalendarBean> it = cal_map.values().iterator();
			while(it.hasNext())
			{
				lt.add(it.next());
			}
			return lt;
	}
	/**
     * 得到节假日总数
     * @param
     * @return String
     * */
	public static String getCalendarCount()
	{
		return CalendarDAO.getCalendarCount();
	}
	/**
	 * 根据节假日id得到对象
	 * @param ca_id
	 * @return CalendarBean <节假日Bean>
	 */
	public static CalendarBean getCalendarBean(int ca_id)
	{
		if(cal_map.containsKey(ca_id))
		{
			return cal_map.get(ca_id);
		}else
		{
			CalendarBean ob = CalendarDAO.getCalendarBean(ca_id);  
			cal_map.put(ca_id, ob);
			return ob; 
		}
	}
	/**
     * 得到ID,用于添加记录
     * @param
     * @return String
     * */
	public static int getInsertID()
	{
		return PublicTableDAO.getIDByTableName(PublicTableDAO.APPEAL_CALENDAR_TABLE_NAME);
	}
	/**
	 * 添加节假日
	 * @return	true 成功| false 失败
	 */
	public static boolean insertCalendar(CalendarBean bean, SettingLogsBean stl)
	{
		if(CalendarDAO.insertCalendar(bean, stl))
		{ 
			reloadCalendar();
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 修改节假日信息
	 * @param bean	节假日对象
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean updateCalendar(CalendarBean bean, SettingLogsBean stl)
	{
		if(CalendarDAO.updateCalendar(bean, stl))
		{
			reloadCalendar();
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 删除节假日信息
	 * @param mp	key=mcat_ids,values=节假日IDS
	 * @param stl
	 * @return	true 成功| false 失败
	 */
	public static boolean deleteCalendar(Map<String, String> mp, SettingLogsBean stl)
	{
		if(CalendarDAO.deleteCalendar(mp, stl))
		{
			reloadCalendar();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
     * 得到信件已经提交的天数（工作日）
     * @param start_time 提交时间
     * @param now_time 现在时间
     * @return int 工作日数
     */
	public static int getWorkDays(String start_date,String current_date){
		try{
			int work_days = 0;	
			if(!start_date.equals(current_date))
			{
				List<CalendarBean> CalendarBeanList = getAllCalendarList();
								
				List<Map<String,String>> holidays_list = new ArrayList<Map<String,String>>();
				List<Map<String,String>> work_list = new ArrayList<Map<String,String>>();
				for(CalendarBean calendarBean : CalendarBeanList){
					Map<String,String> map = new HashMap<String,String>();
					map.put("start_time", calendarBean.getStart_dtime());
					map.put("end_time", calendarBean.getEnd_dtime());					
					if(calendarBean.getCa_flag() == 0){//法定假日的日期(在周一到周五之间休息的时间)						
						holidays_list.add(map);
					}else if(calendarBean.getCa_flag() == 1){
						work_list.add(map);
					}
				}
			
				work_days = CalendarUtil.getWorkDays(start_date, current_date, holidays_list, work_list);
			}
			return work_days;//
			
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	         
	public static void main(String[] args)
	{
		System.out.println(getWorkDays("2011-04-01","2011-04-25"));		
	}
}
