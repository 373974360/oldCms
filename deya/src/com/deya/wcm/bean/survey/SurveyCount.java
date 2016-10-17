package com.deya.wcm.bean.survey;

public class SurveyCount implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2648767611181549872L;
	private String category_id = "";
	private String c_name = "";
	private String s_id = "";
	private String s_name = "";
	private int sur_count = 0;//问卷总数	
	private int publish_count = 0;//发部问卷数
	private int subject_count = 0;//问题总数
	private int answer_count = 0;//答卷数
	public int getAnswer_count() {
		return answer_count;
	}
	public void setAnswer_count(int answer_count) {
		this.answer_count = answer_count;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public int getPublish_count() {
		return publish_count;
	}
	public void setPublish_count(int publish_count) {
		this.publish_count = publish_count;
	}
	public String getS_id() {
		return s_id;
	}
	public void setS_id(String s_id) {
		this.s_id = s_id;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
	}
	public int getSubject_count() {
		return subject_count;
	}
	public void setSubject_count(int subject_count) {
		this.subject_count = subject_count;
	}
	public int getSur_count() {
		return sur_count;
	}
	public void setSur_count(int sur_count) {
		this.sur_count = sur_count;
	}
}
