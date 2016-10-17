package com.deya.wcm.timer;

import org.quartz.Job;

import com.deya.util.quartz.CicroTaskScheduler;
import com.deya.util.quartz.FormatRssCronExpression;
public class StartUpTask{
	private static final String groupName = "timerTask";
	
	static{
		startupHandl();
	}
	
	public static String getGroupName()
	{
		return groupName;
	}
	
	public static void startupHandl()
	{
		try
		{
			Job cl = (Job)Class.forName("com.deya.wcm.timer.TimerTaskJob").newInstance();			
			CicroTaskScheduler.addCornTask("wcm_timer", groupName, cl, FormatRssCronExpression.formatCronExp("1m"));
			
			Job cl_d = (Job)Class.forName("com.deya.wcm.timer.TimerTaskJobForDay").newInstance();
			CicroTaskScheduler.addCornTask("wcm_timer_day", "timerTask_day", cl_d, "00 00 00 * * ?");

            Job cl_d_1 = (Job)Class.forName("com.deya.wcm.timer.TimerTaskJobForDayThreeTimes").newInstance();
            CicroTaskScheduler.addCornTask("wcm_timer_day_1", "wcm_timer_day_1", cl_d_1, "00 09 00 * * ?");

            Job cl_d_2 = (Job)Class.forName("com.deya.wcm.timer.TimerTaskJobForDayThreeTimes").newInstance();
            CicroTaskScheduler.addCornTask("wcm_timer_day_2", "wcm_timer_day_2", cl_d_2, "00 14 00 * * ?");

            Job cl_d_3 = (Job)Class.forName("com.deya.wcm.timer.TimerTaskJobForDayThreeTimes").newInstance();
            CicroTaskScheduler.addCornTask("wcm_timer_day_3", "wcm_timer_day_3", cl_d_3, "00 18 00 * * ?");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}