package com.deya.wcm.bean.zwgk.index;

/**
 * 公开信息索引码规则
 * @author liqi
 *
 */
public class IndexRoleBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -39355223051427063L;
	
	private int id;
	private String ir_id = "";
	private String ir_item = "";
	private String ir_value = "";
	private String ir_space = "";
	private int is_valid;
	private int sort_id;
	private int ir_type;
	private String site_id="";
	
	// get方法
	public int getId() {
		return id;
	}
	public String getIr_id() {
		return ir_id;
	}
	public String getIr_item() {
		return ir_item;
	}
	public String getIr_value() {
		return ir_value;
	}
	public String getIr_space() {
		return ir_space;
	}
	public int getIs_valid() {
		return is_valid;
	}
	public int getSort_id() {
		return sort_id;
	}
	public int getIr_type() {
		return ir_type;
	}
	
	// set方法
	public void setId(int id) {
		this.id = id;
	}
	public void setIr_id(String irId) {
		ir_id = irId;
	}
	public void setIr_item(String irItem) {
		ir_item = irItem;
	}
	public void setIr_value(String irValue) {
		ir_value = irValue;
	}
	public void setIr_space(String irSpace) {
		ir_space = irSpace;
	}
	public void setIs_valid(int isValid) {
		is_valid = isValid;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public void setIr_type(int irType) {
		ir_type = irType;
	}


	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
}
