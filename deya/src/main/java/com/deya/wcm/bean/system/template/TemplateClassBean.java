package com.deya.wcm.bean.system.template;

/**
 * @author 符江波
 * @version 1.0
 * @date   2011-3-24 上午11:46:50
 */
public class TemplateClassBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331183539900698192L;
	private int id;
	private int tclass_id;
	private String tclass_ename;
	private String tclass_cname;
	private String tclass_memo;
	private String app_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTclass_id() {
		return tclass_id;
	}
	public String getTclass_ename() {
		return tclass_ename;
	}
	public String getTclass_cname() {
		return tclass_cname;
	}
	public String getTclass_memo() {
		return tclass_memo;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setTclass_id(int tclassId) {
		tclass_id = tclassId;
	}
	public void setTclass_ename(String tclassEname) {
		if(tclassEname == null){
			tclassEname = " ";
		}
		tclass_ename = tclassEname;
	}
	public void setTclass_cname(String tclassCname) {
		if(tclassCname == null){
			tclassCname = " ";
		}
		tclass_cname = tclassCname;
	}
	public void setTclass_memo(String tclassMemo) {
		if(tclassMemo == null){
			tclassMemo = " ";
		}
		tclass_memo = tclassMemo;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}

}
