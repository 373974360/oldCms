/**
 * 
 */
package com.deya.wcm.bean.appeal.cpLead;

/**
 * @author Administrator
 *
 */
public class CpLeadBean {

	private int lead_id;       //领导人ID
	private String lead_name;  //领导人姓名
	private String lead_job;   //职务
	private String lead_img;   //领导照片URL
	private String lead_url;   //简介URL
	private String lead_memo;  //领导人描述
	private int sort_id=9999;       //排序
	private int dept_id;  //机构ID
	private String dept_name;  //机构名称（为方便展示添加的属性）
	
	
	public int getLead_id() {
		return lead_id;
	}
	public void setLead_id(int leadId) {
		lead_id = leadId;
	}
	public String getLead_name() {
		return lead_name;
	}
	public void setLead_name(String leadName) {
		lead_name = leadName;
	}
	public String getLead_job() {
		return lead_job;
	}
	public void setLead_job(String leadJob) {
		lead_job = leadJob;
	}
	public String getLead_img() {
		return lead_img;
	}
	public void setLead_img(String leadImg) {
		lead_img = leadImg;
	}
	public String getLead_url() {
		return lead_url;
	}
	public void setLead_url(String leadUrl) {
		lead_url = leadUrl;
	}
	public String getLead_memo() {
		return lead_memo;
	}
	public void setLead_memo(String leadMemo) {
		lead_memo = leadMemo;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int deptId) {
		dept_id = deptId;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String deptName) {
		dept_name = deptName;
	}
}
