package com.deya.wcm.bean.survey;

import java.util.*;
/**
 * 问卷调查题目Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 问卷调查题目Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SurveySub implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3437139468940986426L;
	private int id;//主键id
	private String survey_id = "";//所属问卷ID
	private String subject_id = "";//主题ID
	private String sub_name = "";
	private String subject_type = "";//题目类型
	private String explains = "";//提示说明
	private String item_value = "";//多项选择中，记录该题中的选项值
	private int is_required = 0;//该题是否必答
	private int sort = 0;//排序
	private List<SurveySuvItem> itemList = new ArrayList<SurveySuvItem>();
	
	public String getExplains() {
		return explains;
	}
	public void setExplains(String explains) {
		this.explains = explains;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIs_required() {
		return is_required;
	}
	public void setIs_required(int is_required) {
		this.is_required = is_required;
	}
	public List<SurveySuvItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SurveySuvItem> itemList) {
		this.itemList = itemList;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public String getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}
	public String getSubject_type() {
		return subject_type;
	}
	public void setSubject_type(String subject_type) {
		this.subject_type = subject_type;
	}
	public String getSurvey_id() {
		return survey_id;
	}
	public void setSurvey_id(String survey_id) {
		this.survey_id = survey_id;
	}
	public String getItem_value() {
		return item_value;
	}
	public void setItem_value(String item_value) {
		this.item_value = item_value;
	}
}
