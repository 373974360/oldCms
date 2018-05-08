package com.deya.wcm.timer;

import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.quartz.CicroTaskScheduler;
import org.quartz.Job;
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
            String[] class_arr = bc.getPropertyNamesByCategory("timerlistenerclass");
            if(class_arr != null && class_arr.length > 0)
            {
                for (String classStr : class_arr) {
                    Job cl = (Job)Class.forName(classStr).newInstance();
                    CicroTaskScheduler.addCornTask(classStr, groupName, cl, bc.getProperty(classStr,"","timerlistenerclass"));
                }
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