package com.deya.wcm.bean.zwgk.node;

import java.io.Serializable;

public class GKNodeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6222724034207244504L;
	private int id = 0;
	private String node_id = "";//
	private int nodcat_id = 0;//
	private int dept_id = 0;//所属部门\机构
	private String node_name = "";//节点简称
	private String node_fullname = "";//节点全称	
	private int node_status = 0;//0：正常 1：暂停	-1：为删除
	private int sort_id = 999;		
	private String dept_code = "";//公开机构编码
	private int is_apply = 0;//是否参与依申请公开	
	private String apply_name = "";//依申请公开：显示名称
	private String node_demo = "";//备注
	private String rela_site_id = "";//关联站点
	private String address = "";
	private String postcode = "";
	private String office_dtime = "";
	private String tel = "";
	private String fax = "";
	private String email = "";
	String index_template_id = "";
	public String getIndex_template_id() {
		return index_template_id;
	}
	public void setIndex_template_id(String indexTemplateId) {
		index_template_id = indexTemplateId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getOffice_dtime() {
		return office_dtime;
	}
	public void setOffice_dtime(String officeDtime) {
		office_dtime = officeDtime;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getRela_site_id() {
		return rela_site_id;
	}
	public void setRela_site_id(String relaSiteId) {
		rela_site_id = relaSiteId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String nodeId) {
		node_id = nodeId;
	}
	public int getNodcat_id() {
		return nodcat_id;
	}
	public void setNodcat_id(int nodcatId) {
		nodcat_id = nodcatId;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int deptId) {
		dept_id = deptId;
	}
	public String getNode_name() {
		return node_name;
	}
	public void setNode_name(String nodeName) {
		node_name = nodeName;
	}
	public String getNode_fullname() {
		return node_fullname;
	}
	public void setNode_fullname(String nodeFullname) {
		node_fullname = nodeFullname;
	}
	public int getNode_status() {
		return node_status;
	}
	public void setNode_status(int nodeStatus) {
		node_status = nodeStatus;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String deptCode) {
		dept_code = deptCode;
	}
	public int getIs_apply() {
		return is_apply;
	}
	public void setIs_apply(int isApply) {
		is_apply = isApply;
	}
	public String getApply_name() {
		return apply_name;
	}
	public void setApply_name(String applyName) {
		apply_name = applyName;
	}
	public String getNode_demo() {
		return node_demo;
	}
	public void setNode_demo(String nodeDemo) {
		node_demo = nodeDemo;
	}
	
}
