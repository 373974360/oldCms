package com.deya.wcm.bean.cms.workflow;

public class WorkFlowStepBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7922683879295449708L;
	private int step_id = 1;
	private int wf_id = 0;	
	private String step_name = "";
	private String role_id = "";
    private int required = 0;
    public int getStep_id() {
		return step_id;
	}
	public void setStep_id(int stepId) {
		step_id = stepId;
	}
	public int getWf_id() {
		return wf_id;
	}
	public void setWf_id(int wfId) {
		wf_id = wfId;
	}
	public String getStep_name() {
		return step_name;
	}
	public void setStep_name(String stepName) {
		step_name = stepName;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String roleId) {
		role_id = roleId;
	}

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }
}
