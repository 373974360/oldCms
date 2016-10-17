package com.deya.wcm.bean.appeal.satisfaction;

import java.util.ArrayList;
import java.util.List;

import com.deya.wcm.bean.cms.workflow.WorkFlowStepBean;

public class SatisfactionBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6521099637603667064L;
	
	private int sat_id;
	private String sat_item;
	private int sat_score;
	
	private List<SatisfactionBean> SatisfactionOptions_list = new ArrayList<SatisfactionBean>();
	
	public List<SatisfactionBean> getSatisfactionOptions_list() {
		return SatisfactionOptions_list;
	}
	public void setSatisfactionOptions_list(
			List<SatisfactionBean> satisfactionOptionsList) {
		SatisfactionOptions_list = satisfactionOptionsList;
	}
	public int getSat_id() {
		return sat_id;
	}
	public String getSat_item() {
		return sat_item;
	}
	public int getSat_score() {
		return sat_score;
	}
	public void setSat_id(int satId) {
		sat_id = satId;
	}
	public void setSat_item(String satItem) {
		sat_item = satItem;
	}
	public void setSat_score(int satScore) {
		sat_score = satScore;
	}
}