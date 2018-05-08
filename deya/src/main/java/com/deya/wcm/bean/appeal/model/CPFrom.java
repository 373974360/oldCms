package com.deya.wcm.bean.appeal.model;

import java.io.Serializable;

public class CPFrom implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8556711491953809992L;
	private int field_id = 1;
	private int model_id = 0;
	private String field_ename = "";
	private String field_cname = "";
	public int getField_id() {
		return field_id;
	}
	public void setField_id(int fieldId) {
		field_id = fieldId;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public String getField_ename() {
		return field_ename;
	}
	public void setField_ename(String fieldEname) {
		field_ename = fieldEname;
	}
	public String getField_cname() {
		return field_cname;
	}
	public void setField_cname(String fieldCname) {
		field_cname = fieldCname;
	}
}
