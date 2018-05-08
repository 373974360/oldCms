package com.deya.wcm.bean.appeal.sq;

import java.io.Serializable;

public class SQCustom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2840611058213274637L;
	private int cu_id = 0;
	private int model_id = 0;
	private int sq_id = 0;
	private String cu_key = "";
	private String cu_value = "";
	public int getCu_id() {
		return cu_id;
	}
	public void setCu_id(int cuId) {
		cu_id = cuId;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public int getSq_id() {
		return sq_id;
	}
	public void setSq_id(int sqId) {
		sq_id = sqId;
	}
	public String getCu_key() {
		return cu_key;
	}
	public void setCu_key(String cuKey) {
		cu_key = cuKey;
	}
	public String getCu_value() {
		return cu_value;
	}
	public void setCu_value(String cuValue) {
		cu_value = cuValue;
	}
}
