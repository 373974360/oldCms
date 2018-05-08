package com.deya.wcm.bean.cms.workflow;

import java.util.ArrayList;
import java.util.List;

public class WorkFlowBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 488135611557828844L;
	private int wf_id = 0;
	private String wf_name = "";
	private String wf_memo = "";
	private int wf_steps;
	private List<WorkFlowStepBean> workFlowStep_list = new ArrayList<WorkFlowStepBean>();
	public List<WorkFlowStepBean> getWorkFlowStep_list() {
		return workFlowStep_list;
	}
	public void setWorkFlowStep_list(List<WorkFlowStepBean> workFlowStepList) {
		workFlowStep_list = workFlowStepList;
	}
	public int getWf_id() {
		return wf_id;
	}
	public void setWf_id(int wfId) {
		wf_id = wfId;
	}
	public String getWf_name() {
		return wf_name;
	}
	public void setWf_name(String wfName) {
		wf_name = wfName;
	}
	public String getWf_memo() {
		return wf_memo;
	}
	public void setWf_memo(String wfMemo) {
		wf_memo = wfMemo;
	}
	public int getWf_steps() {
		return wf_steps;
	}
	public void setWf_steps(int wfSteps) {
		wf_steps = wfSteps;
	}
}
