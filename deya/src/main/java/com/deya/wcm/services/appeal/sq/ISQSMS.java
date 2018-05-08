package com.deya.wcm.services.appeal.sq;

import com.deya.wcm.bean.appeal.model.ModelBean;
import com.deya.wcm.bean.appeal.sq.SQBean;

public interface ISQSMS {
	/**
	 * 新加消息时发送短信(用于客户提交信息后发送查询码和信件编码)
	 * @param SQBean sb
	 * @return void
	 */
	public void sendSMSToClientForAdd(SQBean sb,ModelBean mb);
	
	/**
	 * 新加消息时发送短信(给管理员发送短信)
	 * @param SQBean sb
	 * @return void
	 */
	public void sendSMSToAdminForAdd(SQBean sb,ModelBean mb);
	
	/**
	 * 办结后发送短信给(用于将信件回复结果发送给客户)
	 * @param SQBean sb
	 * @return void
	 */
	public void sendSMSToClientForResult(SQBean sb,ModelBean mb);
	
	/**
	 * 办结后发送短信给(用于将信件回复结果发送给管理员)
	 * @param SQBean sb
	 * @return void
	 */
	public void sendSMSToAdminForResult(SQBean sb,ModelBean mb);
		
	/**
	 * 督办,发短信给部门管理 员
	 * @param SQBean sb
	 * @return void
	 */
	public void sendSMSForSupervise(SQBean sb,ModelBean mb);
		
	/**
	 * 转交呈办理发送给部门管理 员
	 * @param SQBean sb
	 * @return void
	 */
	 public void sendSMSForTrans(SQBean sb,ModelBean mb);
	
	 /**
	 * 发布操作,给信件管理员
	 * @param SQBean sb
	 * @return void
	 */
	 public void sendSMSForPublish(SQBean sb,ModelBean mb);
		
}
