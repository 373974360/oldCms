package com.deya.wcm.bean.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>搜索索引信息扩展类</p>
 * <p>搜索索引信息类的属性</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */
public class IndexBeanInfo extends IndexBean {

	private String categoryId; //栏目
    private String publish_time; //发布时间
    private String typed; //小分类
    private List<InfoPic> infoPics = new ArrayList<InfoPic>();
    private String doc_no;
	
	public IndexBeanInfo(){
		super("info"); 
		setTyped("info");
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publishTime) {
		publish_time = publishTime;
	}

	public String getTyped() {
		return typed;
	}

	public void setTyped(String typed) {
		this.typed = typed;
	}

	public List<InfoPic> getInfoPics() {
		return infoPics;
	}

	public String getDoc_no() {
		return doc_no;
	}

	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}

	public void setInfoPics(List<InfoPic> infoPics) {
		this.infoPics = infoPics;
	}

}
