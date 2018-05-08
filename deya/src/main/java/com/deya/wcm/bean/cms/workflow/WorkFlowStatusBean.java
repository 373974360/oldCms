package com.deya.wcm.bean.cms.workflow;

public class WorkFlowStatusBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2165979798772144484L;
	private int status_id = 0;
	private String status_name = "";
	private String status_memo = "";
	/*-2：回收站
	-1： 归档
	0：草稿
	1：退稿
	2：待审
	3：撤稿
	4：待发
	5：已发*/

	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int statusId) {
		status_id = statusId;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String statusName) {
		status_name = statusName;
	}
	public String getStatus_memo() {
		return status_memo;
	}
	public void setStatus_memo(String statusMemo) {
		status_memo = statusMemo;
	}
}
