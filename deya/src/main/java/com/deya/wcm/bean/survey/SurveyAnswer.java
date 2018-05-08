package com.deya.wcm.bean.survey;

import java.util.*;
/**
 * 问卷调查答卷Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 问卷调查答卷Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SurveyAnswer implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8517048204317594208L;
	private int id;
	private String answer_id = "";//答卷ID
	private String s_id = "";//问卷ID
	private String ip = "";//
	private String answer_time = "";//回答时间
	private int operate_time = 0;//完成答题所需时间
	private String user_name = "";//登录会员名，预留
	private String item_text = "";//对象使用，无表字段
	private List<SurveyAnswerItem> item_list = new ArrayList<SurveyAnswerItem>();
	public String getAnswer_time() {
		return answer_time;
	}
	public void setAnswer_time(String answer_time) {
		this.answer_time = answer_time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(int operate_time) {
		this.operate_time = operate_time;
	}
	public String getS_id() {
		return s_id;
	}
	public void setS_id(String s_id) {
		this.s_id = s_id;
	}	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public List<SurveyAnswerItem> getItem_list() {
		return item_list;
	}
	public void setItem_list(List<SurveyAnswerItem> item_list) {
		this.item_list = item_list;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(String answer_id) {
		this.answer_id = answer_id;
	}
	public String getItem_text() {
		return item_text;
	}
	public void setItem_text(String item_text) {
		this.item_text = item_text;
	}
}
