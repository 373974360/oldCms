package com.deya.wcm.bean.zwgk.export;

public class ExportInfo {

	private String indexCode = ""; //索引号
	private String title = "";     //标题
	private String time = "";      //时间
	private String depName = "";   //发布机构
	
	private String doc_no = "";      //文号
	private String description = ""; //概述
	
	private int id = 0;//信息ID
	
	public String getIndexCode() {
		return indexCode;
	}
	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String docNo) {
		doc_no = docNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
