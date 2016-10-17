package com.deya.wcm.timer;

import java.util.HashSet;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.util.DebugLog;

public class TimerTaskJob implements Job{
	@SuppressWarnings("unchecked")
	private static Set timerListeners=new HashSet();
	private static JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("startListener"); 	
	public TimerTaskJob(){

	}
	
	static{
		try{			
			String[] class_arr = bc.getPropertyNamesByCategory("timerlistenerclass");
			if(class_arr != null && class_arr.length > 0)
        	{
				 
				for(int i=class_arr.length;i > 0;i--)
        		{
					//System.out.println("***********TimerTaskJob listener classes "+bc.getProperty(class_arr[i-1], "", "timerlistenerclass"));
					Class.forName(bc.getProperty(class_arr[i-1], "", "timerlistenerclass"));
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
			for(Object o : timerListeners)
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
        timerListeners.add(timerListener);
    }
	
	public static void removePublishListener(TimerListener timerListener){
        if(timerListener==null){
            return;
        }
        timerListeners.remove(timerListener);
    }
	
	
}
