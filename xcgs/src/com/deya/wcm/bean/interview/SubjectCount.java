package com.deya.wcm.bean.interview;
/**
 * 访谈主题统计Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题统计Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectCount implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3592364610328410655L;
	private String category_id = "";
	private String category_name = "";
	private String sub_id = "";
	private String sub_name = "";
	private int sub_count = 0;
	private int publish_count = 0;
	private int recommend_count = 0;
	private int user_count = 0;
	private int chat_count = 0;
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public int getChat_count() {
		return chat_count;
	}
	public void setChat_count(int chat_count) {
		this.chat_count = chat_count;
	}
	public int getPublish_count() {
		return publish_count;
	}
	public void setPublish_count(int publish_count) {
		this.publish_count = publish_count;
	}
	public int getRecommend_count() {
		return recommend_count;
	}
	public void setRecommend_count(int recommend_count) {
		this.recommend_count = recommend_count;
	}
	public int getSub_count() {
		return sub_count;
	}
	public void setSub_count(int sub_count) {
		this.sub_count = sub_count;
	}
	public String getSub_id() {
		return sub_id;
	}
	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public int getUser_count() {
		return user_count;
	}
	public void setUser_count(int user_count) {
		this.user_count = user_count;
	}
}
