package com.deya.wcm.bean.appeal.model;

import java.io.Serializable;

public class ModelReleDept implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4493098208357772087L;
	private int model_id;
	private int dept_id;
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int deptId) {
		dept_id = deptId;
	}
}
