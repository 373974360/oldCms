package com.deya.wcm.services.model;

import com.deya.wcm.services.model.services.FieldsUtil;

public class Fields {
   
	private int id;
	
	private int from_id; //内容模型对应字段表 用到
	private int model_id; //内容模型对应字段表 用到
	
	private String field_enname;
	private String field_cnname;
	private String field_type;
	private String is_sys;
	private String is_null;
	private String is_display;
	private String field_text;
	private String field_info;
	private String add_time;
	
	private int field_sort; //内容模型对应字段表 用到
	
	//特殊属性 
	private String field_maxnum;
	private String field_maxlength;
	private String validator;
	private String width;
	private String height;
	private String htmledit_type;
	private String default_value;
	private String select_item;
	private String select_view;
	private String max_num;
	private String min_num;
	private String data_type;
	private String data_type_id; 
	
	
	//辅助字段
	private String from_field_cnname;
	private String field_flag = "";
	private String field_flag2 = "";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getField_enname() {
		return FieldsUtil.formatQuote(field_enname);
	}
	public void setField_enname(String fieldEnname) {
		field_enname = fieldEnname;
	}
	public String getField_cnname() {
		return FieldsUtil.formatQuote(field_cnname);
	}
	public void setField_cnname(String fieldCnname) {
		field_cnname = fieldCnname;
	}
	public String getField_type() {
		return FieldsUtil.formatQuote(field_type);
	}
	public void setField_type(String fieldType) {
		field_type = FieldsUtil.formatQuote(fieldType);
	}
	public String getIs_sys() {
		return is_sys;
	}
	public void setIs_sys(String isSys) {
		is_sys = FieldsUtil.formatQuote(isSys);
	}
	public String getIs_null() {
		return is_null;
	}
	public void setIs_null(String isNull) {
		is_null = FieldsUtil.formatQuote(isNull);
	}
	public String getIs_display() {
		return FieldsUtil.formatQuote(is_display);
	}
	public void setIs_display(String isDisplay) {
		is_display = isDisplay;
	}
	public String getField_text() {
		return FieldsUtil.formatQuote(field_text);
	}
	public void setField_text(String fieldText) {
		field_text = fieldText;
	}
	public String getField_info() {
		return FieldsUtil.formatQuote(field_info);
	}
	public void setField_info(String fieldInfo) {
		field_info = fieldInfo;
	}
	public String getAdd_time() {
		return FieldsUtil.formatQuote(add_time);
	}
	public void setAdd_time(String addTime) {
		add_time = addTime;
	}
	public String getField_maxnum() {
		return FieldsUtil.formatQuote(field_maxnum);
	}
	public void setField_maxnum(String fieldMaxnum) {
		field_maxnum = fieldMaxnum;
	}
	public String getField_maxlength() {
		return FieldsUtil.formatQuote(field_maxlength);
	}
	public void setField_maxlength(String fieldMaxlength) {
		field_maxlength = fieldMaxlength;
	}
	public String getValidator() {
		return FieldsUtil.formatQuote(validator);
	}
	public void setValidator(String validator) {
		this.validator = validator;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return FieldsUtil.formatQuote(height);
	}
	public String getField_flag() {
		return field_flag;
	}
	public void setField_flag(String fieldFlag) {
		field_flag = fieldFlag;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getHtmledit_type() {
		return FieldsUtil.formatQuote(htmledit_type);
	}
	public void setHtmledit_type(String htmleditType) {
		htmledit_type = htmleditType;
	}
	public String getDefault_value() {
		return FieldsUtil.formatQuote(default_value);
	}
	public void setDefault_value(String defaultValue) {
		default_value = defaultValue;
	}
	public String getSelect_item() {
		return FieldsUtil.formatQuote(select_item);
	}
	public void setSelect_item(String selectItem) {
		select_item = selectItem;
	}
	public String getSelect_view() {
		return FieldsUtil.formatQuote(select_view);
	}
	public void setSelect_view(String selectView) {
		select_view = selectView;
	}
	public String getMax_num() {
		return FieldsUtil.formatQuote(max_num);
	}
	public void setMax_num(String maxNum) {
		max_num = maxNum;
	}
	public String getMin_num() {
		return FieldsUtil.formatQuote(min_num);
	}
	public void setMin_num(String minNum) {
		min_num = minNum;
	}
	public String getData_type() {
		return FieldsUtil.formatQuote(data_type);
	}
	public void setData_type(String dataType) {
		data_type = dataType;
	}
	public String getData_type_id() {
		return FieldsUtil.formatQuote(data_type_id);
	}
	public void setData_type_id(String dataTypeId) {
		data_type_id = dataTypeId;
	}
	
	
	@Override
	public String toString() {
		return "Fields [add_time=" + add_time + ", data_type=" + data_type
				+ ", data_type_id=" + data_type_id + ", default_value="
				+ default_value + ", field_cnname=" + field_cnname
				+ ", field_enname=" + field_enname + ", field_info="
				+ field_info + ", field_maxlength=" + field_maxlength
				+ ", field_maxnum=" + field_maxnum + ", field_text="
				+ field_text + ", field_type=" + field_type + ", height="
				+ height + ", htmledit_type=" + htmledit_type + ", id=" + id
				+ ", is_display=" + is_display + ", is_null=" + is_null
				+ ", is_sys=" + is_sys + ", max_num=" + max_num + ", min_num="
				+ min_num + ", select_item=" + select_item + ", select_view="
				+ select_view + ", validator=" + validator + ", width=" + width
				+ "]";
	}
	public int getFrom_id() {
		return from_id;
	}
	public void setFrom_id(int fromId) {
		from_id = fromId;
	}
	public int getField_sort() {
		return field_sort;
	}
	public void setField_sort(int fieldSort) {
		field_sort = fieldSort;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public String getFrom_field_cnname() {
		return from_field_cnname;
	}
	public void setFrom_field_cnname(String fromFieldCnname) {
		from_field_cnname = fromFieldCnname;
	}
	public String getField_flag2() {
		return field_flag2;
	}
	public void setField_flag2(String fieldFlag2) {
		field_flag2 = fieldFlag2;
	}
	
	
}
