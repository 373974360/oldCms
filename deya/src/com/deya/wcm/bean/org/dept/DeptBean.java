package com.deya.wcm.bean.org.dept;

public class DeptBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4585630871271236668L;
	private int dept_id;//部门ID
	private int parent_id;//父ID
	private String deptlevel_value;//部门级别
	private String dept_fullname = "";//部门全名称
	private String dept_name = "";//简名/别名
	private String area_code = "";//地区代码
	private String dept_code = "";//机构代码
	private String functions = "";//部门职能
	private String tel = "";//电话
	private String fax = "";//传真
	private String email = "";//Email
	private String address = "";//地址
	private int dept_sort = 999;//排序
	private String dept_memo = "";//备注
	private String tree_position = "";
	private String manager_ids = "";//部门管理员，","+用户ID+","号分隔，可多个　如　,12,13,
	private int is_delete = 0;//是否已删除
	
	private int is_publish = 0;//发布到公务员名录
	private String postcode = "";
		
	public int getIs_publish() {
		return is_publish;
	}
	public void setIs_publish(int isPublish) {
		is_publish = isPublish;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getArea_code() {
		return area_code;
	}
	public String getManager_ids() {
		return manager_ids;
	}
	public void setManager_ids(String manager_ids) {
		this.manager_ids = manager_ids;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getDept_fullname() {
		return dept_fullname;
	}
	public void setDept_fullname(String dept_fullname) {
		this.dept_fullname = dept_fullname;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_memo() {
		return dept_memo;
	}
	public void setDept_memo(String dept_memo) {
		this.dept_memo = dept_memo;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	
	public String getDeptlevel_value() {
		return deptlevel_value;
	}
	public void setDeptlevel_value(String deptlevelValue) {
		deptlevel_value = deptlevelValue;
	}
	public int getDept_sort() {
		return dept_sort;
	}
	public void setDept_sort(int dept_sort) {
		this.dept_sort = dept_sort;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getFunctions() {
		return functions;
	}
	public void setFunctions(String functions) {
		this.functions = functions;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
