package com.webService;

import com.deya.wcm.timer.TimerListener;
import com.deya.wcm.timer.TimerTaskJob;

public class OaGongGaoTimerImpl implements TimerListener{
	static{
		TimerTaskJob.registerPublishListener(new OaGongGaoTimerImpl());
	}
	
	public void timingTask()
	{
		OaGongGaoManager.getOaData();
	}
	
}
