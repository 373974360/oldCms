package com.deya.wcm.bean.appeal.model;

import java.io.Serializable;

public class ModelReleUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7402961441938300532L;
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	private int model_id;
	private int user_id;
}
