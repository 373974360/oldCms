package com.deya.wcm.bean.system.formodel;

public class ModelFieldBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2938967510508763845L;
	private int field_id;
	private int model_id;
	private int meta_id;
	
	private String field_ename = "";
	private String field_cname = "";
	private String table_name = "";
	private int is_show = 0;//是否在做模板时进行展示
	public int getMeta_id() {
		return meta_id;
	}

	public void setMeta_id(int metaId) {
		meta_id = metaId;
	}

	public String getField_ename() {
		return field_ename;
	}

	public void setField_ename(String fieldEname) {
		field_ename = fieldEname;
	}

	public String getField_cname() {
		return field_cname;
	}

	public void setField_cname(String fieldCname) {
		field_cname = fieldCname;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String tableName) {
		table_name = tableName;
	}

	public int getIs_show() {
		return is_show;
	}

	public void setIs_show(int isShow) {
		is_show = isShow;
	}

	public String getField_memo() {
		return field_memo;
	}

	public void setField_memo(String fieldMemo) {
		field_memo = fieldMemo;
	}

	private String field_memo = "";
	public int getModel_id() {
		return model_id;
	}

	public void setModel_id(int modelId) {
		model_id = modelId;
	}

	public int getField_id() {
		return field_id;
	}

	public void setField_id(int fieldId) {
		field_id = fieldId;
	}
}
