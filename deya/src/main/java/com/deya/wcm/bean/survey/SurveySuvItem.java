package com.deya.wcm.bean.survey;

import java.util.*;
/**
 * 问卷调查题目选项Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 问卷调查题目选项Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SurveySuvItem implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1577994211193164622L;
	private String survey_id = "";//所属问卷ID
	private String subject_id = "";//主题ID
	private String item_id = "";//选项ID,对应表单中控件的ID名称,如item_1
	private String item_name = "";
	private int is_text = 0;//是否有需要填空的
	private String text_value = "";//文本内容值
	private int sort = 0;//排序 
	private List<SurveyChildItem> childList = new ArrayList<SurveyChildItem>();
	public int getIs_text() {
		return is_text;
	}
	public void setIs_text(int is_text) {
		this.is_text = is_text;
	}
	
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}
	public String getSurvey_id() {
		return survey_id;
	}
	public void setSurvey_id(String survey_id) {
		this.survey_id = survey_id;
	}
	public String getText_value() {
		return text_value;
	}
	public void setText_value(String text_value) {
		this.text_value = text_value;
	}
	public List<SurveyChildItem> getChildList() {
		return childList;
	}
	public void setChildList(List<SurveyChildItem> childList) {
		this.childList = childList;
	}	
}
