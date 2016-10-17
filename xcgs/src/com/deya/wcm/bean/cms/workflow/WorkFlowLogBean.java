package com.deya.wcm.bean.cms.workflow;

public class WorkFlowLogBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -411760579193111063L;
	private int log_id = 0;
	private int content_id = 0;
	private int wf_before = 0;
	private int wf_after = 0;
	private String wf_action = "";
	private String wf_reason = "";
	private String opt_time = "";
	private String user_id = "";	
	private String ip = "";
	public int getLog_id() {
		return log_id;
	}
	public void setLog_id(int logId) {
		log_id = logId;
	}
	public int getContent_id() {
		return content_id;
	}
	public void setContent_id(int contentId) {
		content_id = contentId;
	}
	public int getWf_before() {
		return wf_before;
	}
	public void setWf_before(int wfBefore) {
		wf_before = wfBefore;
	}
	public int getWf_after() {
		return wf_after;
	}
	public void setWf_after(int wfAfter) {
		wf_after = wfAfter;
	}
	public String getWf_action() {
		return wf_action;
	}
	public void setWf_action(String wfAction) {
		wf_action = wfAction;
	}
	public String getWf_reason() {
		return wf_reason;
	}
	public void setWf_reason(String wfReason) {
		wf_reason = wfReason;
	}
	public String getOpt_time() {
		return opt_time;
	}
	public void setOpt_time(String optTime) {
		opt_time = optTime;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
