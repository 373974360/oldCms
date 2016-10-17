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

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}