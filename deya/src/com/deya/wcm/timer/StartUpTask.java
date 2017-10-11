package com.deya.wcm.timer;

import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.quartz.CicroTaskScheduler;
import org.quartz.Job;

import java.util.Iterator;
import java.util.Set;

public class StartUpTask{
	private static final String groupName = "timerTask";

    private static JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("startListener");

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
            Set<String> class_arr = bc.getPropertyNamesByCategory("timerlistenerclass");
			Iterator<String> iterator = class_arr.iterator();
			while (iterator.hasNext()){
				String next = iterator.next();
				Job cl = (Job)Class.forName(next).newInstance();
				CicroTaskScheduler.addCornTask(next, groupName, cl, bc.getProperty(next,"","timerlistenerclass"));
			}

			/*Job cl_d = (Job)Class.forName("com.deya.wcm.timer.TimerTaskJobForDay").newInstance();
			CicroTaskScheduler.addCornTask("wcm_timer_day", "timerTask_day", cl_d, "00 00 00 * * ?");

            Job cl_d_1 = (Job)Class.forName("com.deya.wcm.timer.TimerTaskJobForDayThreeTimes").newInstance();
            CicroTaskScheduler.addCornTask("wcm_timer_day_1", "wcm_timer_day_1", cl_d_1, "00 09 00 * * ?");

            Job cl_d_2 = (Job)Class.forName("com.deya.wcm.timer.TimerTaskJobForDayThreeTimes").newInstance();
            CicroTaskScheduler.addCornTask("wcm_timer_day_2", "wcm_timer_day_2", cl_d_2, "00 14 00 * * ?");

            Job cl_d_3 = (Job)Class.forName("com.deya.wcm.timer.TimerTaskJobForDayThreeTimes").newInstance();
            CicroTaskScheduler.addCornTask("wcm_timer_day_3", "wcm_timer_day_3", cl_d_3, "00 18 00 * * ?");*/

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}