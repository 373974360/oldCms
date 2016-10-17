package com.deya.util.quartz;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CicroTaskScheduler {

	private static Scheduler scheduler = null;
	
	static{
		initCicroTaskScheduler();
	}

	public static void initCicroTaskScheduler(){
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		try {
			scheduler = schedulerFactory.getScheduler();
			//CicroTaskScheduler.addSimpleTask("init", "init", new InitJob(), new Date(), 5000, 0);
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Scheduler getSch() throws SchedulerException{
		return scheduler;
	}

	/**
	 * 向任务调度器中添加一个cron任务
	 * @param jobname
	 * @param groupname
	 * @param jobclass
	 * @param startTime
	 * @param repeatTime
	 * @throws SchedulerException
	 */
	public static void addCornTask(String jobname, String groupname, Job jobclass, 
			String cronExp) throws SchedulerException{

		JobDetail jobDetail = 
			new JobDetail(jobname, groupname, jobclass.getClass());
		CronTrigger cronTrigger = new CronTrigger(jobname, groupname);
		try {
			// setup CronExpression
			if(cronExp == null || cronExp.trim().equals("") || cronExp.trim() == ""){
				cronExp = "0 0 0 0/1 * ?";
			}
			System.out.println("cronExp----------------"+cronExp);
			CronExpression cexp = new CronExpression(cronExp);
			// Assign the CronExpression to CronTrigger
			cronTrigger.setCronExpression(cexp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		scheduler.scheduleJob(jobDetail, cronTrigger);
	}

	/**
	 * 向调度器添加一个简单任务
	 * @param jobName
	 * @param jobGroup
	 * @param job
	 * @param startTime
	 * @param repeatInterval
	 * @param repeatCount
	 * @throws SchedulerException
	 */
	public static void addSimpleTask(String jobName, String jobGroup, Job job,
			Date startTime, int repeatInterval, int repeatCount) throws SchedulerException{

		long ctime = System.currentTimeMillis(); 
		JobDetail jobDetail = 
			new JobDetail(jobName, jobGroup, job.getClass());

		SimpleTrigger simpleTrigger = 
			new SimpleTrigger(jobName, jobGroup);
		if(startTime == null){
			startTime = new Date(ctime);
		}
		simpleTrigger.setStartTime(startTime);  
		simpleTrigger.setRepeatInterval(repeatInterval);
		simpleTrigger.setRepeatCount(repeatCount);

		if(!Arrays.asList(scheduler.getJobNames(jobName)).contains(jobName)){
			scheduler.scheduleJob(jobDetail, simpleTrigger);
		}else{
			return;
		}
	}

	/**
	 * 启动任务调度器
	 * @throws SchedulerException
	 */
	public static void startTask() throws SchedulerException{
		scheduler.start();
	}

	/**
	 * 关闭任务调度器
	 * @throws SchedulerException
	 */
	public static void endTask() throws SchedulerException{
		scheduler.shutdown();
	}

	/**
	 * 移除一个任务
	 * @param jobName
	 * @param groupName
	 * @return
	 */
	public static boolean removeJob(String jobName, String groupName){
		try {
			scheduler.deleteJob(jobName, groupName);
		
			return true;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 修改一个任务的触发时间
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException 
	 * @throws ParseException
	 */
	public static void modifyJobTime(String triggerName,String triggerGroupName,
			String time) 
	throws SchedulerException, ParseException {
		Trigger trigger = scheduler.getTrigger(triggerName,triggerGroupName);
		if(trigger != null){
			CronTrigger ct = (CronTrigger)trigger;
			//修改时间
			ct.setCronExpression(time);
			//重启触发器
			scheduler.resumeTrigger(triggerName,triggerGroupName);
		}
	}
}
