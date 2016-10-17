package com.deya.wcm.services.appeal.sq;

import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.wcm.bean.appeal.sq.SQBean;

public class SendSMSThread extends Thread{
     private ISQSMS sqSMS;
	 private SQBean sb;
	 private ModelBean mb;
	 private String handl_name = "";
	 public void run(){
		if("sendSMSForAdd".equals(handl_name))
		{
			//发送给管理员
			if(sqSMS != null)
				sqSMS.sendSMSToAdminForAdd(sb,mb);
			//发送给客户
			if(sqSMS != null)
				sqSMS.sendSMSToClientForAdd(sb,mb);
		}
		if("sendSMSForResult".equals(handl_name))
		{
			//发送给管理员
			if(sqSMS != null)
				sqSMS.sendSMSToAdminForResult(sb,mb);
			//发送给客户
			if(sqSMS != null)
				sqSMS.sendSMSToClientForResult(sb,mb);
		}
		if("sendSMSForSupervise".equals(handl_name))
		{
			//发送给部门管理 员
			if(sqSMS != null)
				sqSMS.sendSMSForSupervise(sb,mb);
		}
		if("sendSMSForTrans".equals(handl_name))
		{
			//发送给部门管理 员
			 if(sqSMS != null)
				 sqSMS.sendSMSForTrans(sb,mb);
		}
		if("sendSMSForPublish".equals(handl_name))
		{
			//发送给信件管理员
			 if(sqSMS != null)
				 sqSMS.sendSMSForPublish(sb,mb);
		}
	 }
	 
	 public SendSMSThread(ISQSMS sqSMS,SQBean sbean, ModelBean mBean,String handlName)
	 {
		 this.sb = sbean;
		 this.mb = mBean;
		 this.handl_name = handlName;
	 }
}
