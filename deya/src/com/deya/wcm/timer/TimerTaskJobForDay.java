package com.deya.wcm.timer;

import java.util.HashSet;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;

/**
 * 按天的定时任务，每天0时开始
 * @author zhuliang
 *
 */
public class TimerTaskJobForDay implements Job{
	@SuppressWarnings("unchecked")
	private static Set timerListeners_day=new HashSet();
	private static JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("startListener");
	public TimerTaskJobForDay(){

	}
	
	static{
		try{			
			String[] class_arr = bc.getPropertyNamesByCategory("timerlistener_day_class");
			if(class_arr != null && class_arr.length > 0)
        	{
				 
				for(int i=class_arr.length;i > 0;i--)
        		{
					//System.out.println("***********TimerTaskJobForDay listener classes "+bc.getProperty(class_arr[i-1], "", "timerlistener_day_class"));
					Class.forName(bc.getProperty(class_arr[i-1], "", "timerlistener_day_class"));
        		}
        	}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		
		try{
			for(Object o : timerListeners_day)
			{				
				TimerListener timerListener=(TimerListener)o;
				timerListener.timingTask();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void registerPublishListener(TimerListener timerListener){
        if(timerListener==null){
            return;
        }
        timerListeners_day.add(timerListener);
    }
	
	public static void removePublishListener(TimerListener timerListener){
        if(timerListener==null){
            return;
        }
        timerListeners_day.remove(timerListener);
    }
}
