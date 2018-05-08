/**
 * 
 */
package com.deya.wcm.bean.appeal.cpDept;

/**
 * @author 王磊
 *
 */
public class CpDeptBean {
	
	private int dept_id; //机构ID
	private int parent_id; //父ID
	private String dept_name; //机构名称
	private String dept_position; //机构路径
	private int dept_level; //深度/级别
	private String dept_memo; //机构描述
	private int sort_id=999; //排序
	
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int deptId) {
		dept_id = deptId;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String deptName) {
		dept_name = deptName;
	}
	public String getDept_position() {
		return dept_position;
	}
	public void setDept_position(String deptPosition) {
		dept_position = deptPosition;
	}
	public int getDept_level() {
		return dept_level;
	}
	public void setDept_level(int deptLevel) {
		dept_level = deptLevel;
	}
	public String getDept_memo() {
		return dept_memo;
	}
	public void setDept_memo(String deptMemo) {
		dept_memo = deptMemo;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}

}
