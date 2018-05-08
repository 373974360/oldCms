package com.deya.wcm.bean.survey;
/**
 * 答卷选项Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 答卷选项Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SurveyAnswerItem implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1771792087511284215L;
	private String answer_id = "";//答卷ID
	private String s_id = "";//问卷ID
	private String item_id = "";
	private String item_value = "";
	private String item_text = "";
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_text() {
		return item_text;
	}
	public void setItem_text(String item_text) {
		this.item_text = item_text;
	}
	public String getItem_value() {
		return item_value;
	}
	public void setItem_value(String item_value) {
		this.item_value = item_value;
	}
	public String getS_id() {
		return s_id;
	}
	public void setS_id(String s_id) {
		this.s_id = s_id;
	}
	public String getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(String answer_id) {
		this.answer_id = answer_id;
	}
	
}
