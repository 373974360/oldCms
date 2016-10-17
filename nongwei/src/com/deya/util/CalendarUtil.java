package com.deya.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CalendarUtil {
   
	//得到2个日期之间的所有日期
	public static GregorianCalendar[]  getBetweenDate(String d1,String d2) throws ParseException   
    {   
        Vector<GregorianCalendar> v=new Vector<GregorianCalendar>();   
        SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");   
        GregorianCalendar gc1=new GregorianCalendar(),gc2=new GregorianCalendar();   
        gc1.setTime(sdf.parse(d1));   
        gc2.setTime(sdf.parse(d2));   
        do{   
            GregorianCalendar gc3=(GregorianCalendar)gc1.clone();   
            v.add(gc3);   
            gc1.add(Calendar.DAY_OF_MONTH, 1);                
         }while(!gc1.after(gc2));   
        return v.toArray(new GregorianCalendar[v.size()]);   
    }
	
	
	// 得到他们之间周六，周天上班的天数(在周六和周天上班的时间)
	public static int getWorkDaysInWeekend(Date date_start, Date date_end,
			List<String> list) {
		try {
			int days = 0;
			for (String time : list) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dateTime = sdf.parse(time);
				// 判断该时间是不是星期六，日
				if (isWeekDay(dateTime)) {
					if (dateCompare(date_start, date_end, dateTime)) {
						days++;
					}
				}
			}
			return days;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	// 得到他们之间法定假期的天数(在周一到周五之间休息的时间)
	public static int getVacationDays(Date date_start, Date date_end,
			List<String> list) {

		try {
			int days = 0;
			for (String time : list) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dateTime = sdf.parse(time);
				// 判断该时间是不是星期六，日
				if (!isWeekDay(dateTime)) {
					if (dateCompare(date_start, date_end, dateTime)) {
						days++;
					}
				}
			}
			return days;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}

	// 判断该时间是不是星期六，日
	public static boolean isWeekDay(Date dateTime) {
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(dateTime);
			int ee = c.get(Calendar.DAY_OF_WEEK) - 1;
			//System.out.println(dateTime + "::" + ee);
			if (ee == 0 || ee == 6) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean dateCompare(Date date_start, Date date_end,
			Date dateTime) {
		boolean dateComPareFlag = false;
		if (date_start.compareTo(dateTime) == -1
				&& dateTime.compareTo(date_end) == -1) {
			dateComPareFlag = true; // 
		}
		return dateComPareFlag;
	}

	public int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {

		if (d1.after(d2)) {
			// swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * * 计算2个日期之间的相隔天数 *
	 * 
	 * @param d1 *
	 * @param d2 *
	 * @return
	 */
	public int getWorkingDay(java.util.Calendar d1, java.util.Calendar d2) {
		int result = -1;
		if (d1.after(d2)) {
			// swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int betweendays = getDaysBetween(d1, d2);
		int charge_date = 0;
		int charge_start_date = 0;
		// 开始日期的日期偏移量
		int charge_end_date = 0;
		// 结束日期的日期偏移量
		// 日期不在同一个日期内
		int stmp;
		int etmp;
		stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {
			// 开始日期为星期六和星期日时偏移量为0
			charge_start_date = stmp - 1;
		}
		if (etmp != 0 && etmp != 6) {
			// 结束日期为星期六和星期日时偏移量为0
			charge_end_date = etmp - 1;
		}
		// }
		result = (getDaysBetween(this.getNextMonday(d1), this.getNextMonday(d2)) / 7)
				* 5 + charge_start_date - charge_end_date;
		// System.out.println("charge_start_date>" + charge_start_date);
		// System.out.println("charge_end_date>" + charge_end_date);
		// System.out.println("between day is-->" + betweendays);
		return result;
	}

	public String getChineseWeek(Calendar date) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		// System.out.println(dayNames[dayOfWeek - 1]);
		return dayNames[dayOfWeek - 1];
	}

	/**
	 * * 获得日期的下一个星期一的日期 * *
	 * 
	 * @param date *
	 * @return
	 */
	public Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	/**
	 * * *
	 * 
	 * @param d1 *
	 * @param d2 *
	 * @return
	 */
	public int getHolidays(Calendar d1, Calendar d2) {
		return this.getDaysBetween(d1, d2) - this.getWorkingDay(d1, d2);
	}
	
	
	// 法定假日的日期(在周一到周五之间休息的时间)
	public static int getHolidayDays(String start_time,String now_time,String holidays_start_time,String holidays_end_time ){
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date_start = null;
			Date date_end = null;
			date_start = sdf.parse(start_time);
			date_end = sdf.parse(now_time);
			
			GregorianCalendar[] ga=getBetweenDate(holidays_start_time,holidays_end_time);
			List<String> list = new ArrayList<String>();
	        for(GregorianCalendar e:ga)   
	        {     
	              list.add(e.get(Calendar.YEAR)+"-"+(e.get(Calendar.MONTH)+1)+"-"+e.get(Calendar.DAY_OF_MONTH)+"");
	        }
	        
	        int days = getVacationDays(date_start, date_end, list);

			return days;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	// 法定假日的日期(在周六和周天上班的时间)
	public static int getWorkDaysInHolidayDays(String start_time,String now_time,String work_start_time,String work_end_time ){
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date_start = null;
			Date date_end = null;
			date_start = sdf.parse(start_time);
			date_end = sdf.parse(now_time);
			
			GregorianCalendar[] ga=getBetweenDate(work_start_time,work_end_time);
			List<String> list = new ArrayList<String>();
	        for(GregorianCalendar e:ga)   
	        {     
	              list.add(e.get(Calendar.YEAR)+"-"+(e.get(Calendar.MONTH)+1)+"-"+e.get(Calendar.DAY_OF_MONTH)+"");
	        }
	        
	        int days = getWorkDaysInWeekend(date_start, date_end, list);

			return days;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int getWorkDays(String start_time,String now_time,List<Map<String,String>> holidays_list,List<Map<String,String>> work_list){
		try{
			
			int holidays_days = 0;//在start_time和now_time之间所有的在 （法定假日的日期(在周一到周五之间休息的时间)） 之和
			for(Map map : holidays_list){
				holidays_days += getHolidayDays(start_time,now_time,(String)map.get("start_time"),(String)map.get("end_time"));
			}
			
			int work_days = 0;//在start_time和now_time之间所有的在 （法定假日的日期(在周一到周五之间休息的时间)） 之和
			for(Map map : work_list){
				work_days += getWorkDaysInHolidayDays(start_time,now_time,(String)map.get("start_time"),(String)map.get("end_time"));
			}
			
			
			int workDaysAll = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date_start = null;
			Date date_end = null;
			date_start = sdf.parse(start_time);
			date_end = sdf.parse(now_time);
			Calendar cal_start = Calendar.getInstance();
			Calendar cal_end = Calendar.getInstance();
			cal_start.setTime(date_start);
			cal_end.setTime(date_end);
			CalendarUtil calendarUtil = new CalendarUtil();
			workDaysAll = calendarUtil.getWorkingDay(cal_start, cal_end);
			
			//System.out.println(workDaysAll);
			//System.out.println(holidays_days);
			//System.out.println(work_days);
			//return  workDaysAll-holidays_days+work_days;
			if(cal_end.get(Calendar.DAY_OF_WEEK)!=1 && cal_end.get(Calendar.DAY_OF_WEEK)!=7){
				return  workDaysAll-1-holidays_days+work_days;
			}
			return  workDaysAll-holidays_days+work_days;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	} 
	
	
	
    public static void main(String[] args) throws Exception {   
        // TODO Auto-generated method stub   
        GregorianCalendar[] ga=getBetweenDate("2010-03-25", "2010-03-26");   
        for(GregorianCalendar e:ga)   
        {   
            System.out.println(e.get(Calendar.YEAR)+"-"+   
                               +(e.get(Calendar.MONTH)+1)+"-"+   
                               e.get(Calendar.DAY_OF_MONTH)+"");   
        }   
        
        
        
        String strDateStart = "2010-03-31";//信件提交时间
		String strDateEnd = "2010-05-03"; //
		int workDaysAll = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date_start = null;
		Date date_end = null;
		
		date_start = sdf.parse(strDateStart);
		date_end = sdf.parse(strDateEnd);
        
		
		Calendar cal_start = Calendar.getInstance();
		Calendar cal_end = Calendar.getInstance();
		cal_start.setTime(date_start);
		cal_end.setTime(date_end);
		CalendarUtil calendarUtil = new CalendarUtil();
		workDaysAll = calendarUtil.getWorkingDay(cal_start, cal_end);
		
        // 法定假日的日期(在周一到周五之间休息的时间)
		String time[] = { "2010-04-03", "2010-05-02" };
		List<String> list = Arrays.asList(time);
		int days = getVacationDays(date_start, date_end, list);
		System.out.println("法定假日的日期(在周一到周五之间休息的时间)-->" + days);
		// 法定假日的日期(在周六和周天上班的时间)
		String time_2[] = { "2010-04-10", "2010-05-03" };
		List<String> list_2 = Arrays.asList(time_2);
		int days_2 = getWorkDaysInWeekend(date_start, date_end, list_2);
		System.out.println("法定假日的日期(在周六和周天上班的时间)-->" + days_2);

		int workDays = workDaysAll - 1 - days + days_2;
		System.out.println(strDateStart + "到" + strDateEnd + "之间不包含（"
				+ strDateStart + "和" + strDateEnd
				+ "） 减去 法定假日的日期(在周一到周五之间休息的时间) 加上 法定假日的日期(在周六和周天上班的时间)-->" + workDays);
        
    } 
	
}

