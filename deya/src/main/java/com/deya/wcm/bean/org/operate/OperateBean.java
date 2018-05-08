package com.deya.wcm.bean.org.operate;

public class OperateBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2025294211575431893L;
	private int opt_id;
	private int parent_id;
	private String tree_position = "";
	private String opt_name = "";
	private String app_id = "";
	private String controller = "";
	private String action = "";
	private String opt_flag = "";
	private String opt_memo = "";
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	
	public String getOpt_memo() {
		return opt_memo;
	}
	public void setOpt_memo(String optMemo) {
		opt_memo = optMemo;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getOpt_flag() {
		return opt_flag;
	}
	public void setOpt_flag(String opt_flag) {
		this.opt_flag = opt_flag;
	}
	public int getOpt_id() {
		return opt_id;
	}
	public void setOpt_id(int opt_id) {
		this.opt_id = opt_id;
	}
	public String getOpt_name() {
		return opt_name;
	}
	public void setOpt_name(String opt_name) {
		this.opt_name = opt_name;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getTree_position() {
		return tree_position;
	}
	public void setTree_position(String tree_position) {
		this.tree_position = tree_position;
	}
}
