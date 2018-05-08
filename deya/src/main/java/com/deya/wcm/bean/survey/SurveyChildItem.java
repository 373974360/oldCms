package com.deya.wcm.bean.survey;

import com.deya.wcm.services.lable.data.IBean;

/**
 * 问卷调查题目选项Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 应用于复合题型的子选项</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SurveyChildItem implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4464549401179900173L;
	private String subject_id = "";//主题ID
	private String item_id = "";//选项ID,关联item表的item_id
	private int item_num = 0;//选项值
	private String item_name = "";
	private int is_text = 0;//是否有需要填空的
	private String text_value = "";//文本内容值
	private int score = 0;//分值
	private int sort = 0;//排序 
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
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getText_value() {
		return text_value;
	}
	public void setText_value(String text_value) {
		this.text_value = text_value;
	}
	public String getSubject_id() {
		return subject_id;
	}
	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
