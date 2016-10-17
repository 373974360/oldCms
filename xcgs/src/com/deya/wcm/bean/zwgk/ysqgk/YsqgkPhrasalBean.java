package com.deya.wcm.bean.zwgk.ysqgk;

/**
 * 依申请 公开常用语 Bean
 * <p>Title: CicroDate</p>
 * <p>Description: 依申请 公开常用语Bean</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author zhangqiang
 * @version 1.0
 * @date 2011-08-05 14:37:34
 */

public class YsqgkPhrasalBean implements java.io.Serializable{
	
	private static final long serialVersionUID = 830170940056984419L;
	
	private int gph_id;
	private String gph_title ="";
	private String gph_content="";
	private int gph_type=0;
	private int sort_id=0;
	
	
	public int getGph_id() {
		return gph_id;
	}
	public String getGph_title() {
		return gph_title;
	}
	public String getGph_content() {
		return gph_content;
	}
	public int getGph_type() {
		return gph_type;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setGph_id(int gphId) {
		gph_id = gphId;
	}
	public void setGph_title(String gphTitle) {
		gph_title = gphTitle;
	}
	public void setGph_content(String gphContent) {
		gph_content = gphContent;
	}
	public void setGph_type(int gphType) {
		gph_type = gphType;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
}