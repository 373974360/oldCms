package com.deya.wcm.services.appeal.sq;

import com.deya.util.jconfig.JconfigFactory;
import com.deya.util.jconfig.JconfigUtil;
import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.wcm.bean.appeal.sq.SQBean;

/**
 *  诉求短信处理类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求短信处理类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author liqi
 * @version 1.0
 * * 
 */
public class SQSMSFactory {
	
	private static ISQSMS sqSMS;
	
	static{
		JconfigUtil bc = JconfigFactory.getJconfigUtilInstance("project_config"); 
		String sq_sms_class = bc.getProperty("sq_sms_class", "", "sqsms");
	
		try{
			if(sq_sms_class != null && !"".equals(sq_sms_class))
				sqSMS = (ISQSMS) Class.forName(sq_sms_class).newInstance();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 新加消息时发送短信(用于客户提交信息后发送查询码和信件编码,再给管理员发送短信)
	 * @param SQBean sb
	 * @return void
	 */
	public static void sendSMSForAdd(SQBean sb,ModelBean mb)
	{
		try{
			
			SendSMSThread sst = new SendSMSThread(sqSMS,sb,mb,"sendSMSForAdd");
			sst.start();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 办结后发送短信给(用于将信件回复结果发送给客户,和管理员)
	 * @param SQBean sb
	 * @return void
	 */
	public static void sendSMSForResult(SQBean sb,ModelBean mb)
	{
		try{			
			SendSMSThread sst = new SendSMSThread(sqSMS,sb,mb,"sendSMSForResult");
			sst.start();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 督办,发短信给部门管理 员
	 * @param SQBean sb
	 * @return void
	 */
	public static void sendSMSForSupervise(SQBean sb,ModelBean mb)
	{
		try{	
			SendSMSThread sst = new SendSMSThread(sqSMS,sb,mb,"sendSMSForSupervise");
			sst.start();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 转交呈办理发送给部门管理 员
	 * @param SQBean sb
	 * @return void
	 */
	 public static void sendSMSForTrans(SQBean sb,ModelBean mb)
	{
		 try{	
			 SendSMSThread sst = new SendSMSThread(sqSMS,sb,mb,"sendSMSForTrans");
			sst.start();
		 }catch(Exception e)
			{
				e.printStackTrace();
			}
	} 
	 
	 /**
	 * 发布操作,给信件管理员
	 * @param SQBean sb
	 * @return void
	 */
	 public static void sendSMSForPublish(SQBean sb,ModelBean mb)
	{
		 try{	
			 SendSMSThread sst = new SendSMSThread(sqSMS,sb,mb,"sendSMSForTrans");
			sst.start();
		 }catch(Exception e)
			{
				e.printStackTrace();
			}
	} 
}
