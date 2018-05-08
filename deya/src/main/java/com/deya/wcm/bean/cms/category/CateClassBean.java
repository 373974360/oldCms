package com.deya.wcm.bean.cms.category;

public class CateClassBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -411032022574587570L;
	private int class_id;					
	private String class_ename = "";//英文名称
	private String class_cname = "";//中文名称
	private String class_codo = "";//域代码
	private String class_define  = "";//定义
	private int class_type = 1;//分类方式类型 0：基础分类法	1：共享目录
	private String app_ids  = "";//所属应用
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int classId) {
		class_id = classId;
	}
	public String getClass_ename() {
		return class_ename;
	}
	public void setClass_ename(String classEname) {
		class_ename = classEname;
	}
	public String getClass_cname() {
		return class_cname;
	}
	public void setClass_cname(String classCname) {
		class_cname = classCname;
	}
	public String getClass_codo() {
		return class_codo;
	}
	public void setClass_codo(String classCodo) {
		class_codo = classCodo;
	}
	public String getClass_define() {
		return class_define;
	}
	public void setClass_define(String classDefine) {
		class_define = classDefine;
	}
	public int getClass_type() {
		return class_type;
	}
	public void setClass_type(int classType) {
		class_type = classType;
	}
	public String getApp_ids() {
		return app_ids;
	}
	public void setApp_ids(String appIds) {
		app_ids = appIds;
	}
}
