package com.deya.wcm.bean.appeal.sq;

public class ProcessBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -40768329078538639L;
	private int pro_id = 0;
	private int sq_id = 0;
	private int user_id = 0;
	private String user_realname = "";
	private int do_dept = 0;
	private String dept_name = "";
	private String pro_dtime = "";
	private int pro_type = 0;
	/*
	0：受理
	1：回复（保存/提交）
	2：转办
	3：交办
	4：呈办 
	5：重复件
	6：无效件
	7：不予受理
	8：申请延时
	9：延时通过
	10：延时打回
	11：审核通过
	12：审核打回
	13：督办
	*/

	private String pro_note = "";
	private String to_dept_name = "";
	private int old_dept_id = 0;
	private int old_sq_status = 0;
	private int old_prove_type = 0;
	
	public String getTo_dept_name() {
		return to_dept_name;
	}
	public void setTo_dept_name(String toDeptName) {
		to_dept_name = toDeptName;
	}
	public int getOld_dept_id() {
		return old_dept_id;
	}
	public void setOld_dept_id(int oldDeptId) {
		old_dept_id = oldDeptId;
	}
	public int getOld_sq_status() {
		return old_sq_status;
	}
	public void setOld_sq_status(int oldSqStatus) {
		old_sq_status = oldSqStatus;
	}
	public int getOld_prove_type() {
		return old_prove_type;
	}
	public void setOld_prove_type(int oldProveType) {
		old_prove_type = oldProveType;
	}
	public int getPro_id() {
		return pro_id;
	}
	public void setPro_id(int proId) {
		pro_id = proId;
	}
	public int getSq_id() {
		return sq_id;
	}
	public void setSq_id(int sqId) {
		sq_id = sqId;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public String getUser_realname() {
		return user_realname;
	}
	public void setUser_realname(String userRealname) {
		user_realname = userRealname;
	}
	public int getDo_dept() {
		return do_dept;
	}
	public void setDo_dept(int doDept) {
		do_dept = doDept;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String deptName) {
		dept_name = deptName;
	}
	public String getPro_dtime() {
		return pro_dtime;
	}
	public void setPro_dtime(String proDtime) {
		pro_dtime = proDtime;
	}
	public int getPro_type() {
		return pro_type;
	}
	public void setPro_type(int proType) {
		pro_type = proType;
	}
	public String getPro_note() {
		return pro_note;
	}
	public void setPro_note(String proNote) {
		pro_note = proNote;
	}
	
}
