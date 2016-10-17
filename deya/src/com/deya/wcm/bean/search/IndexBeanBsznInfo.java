package com.deya.wcm.bean.search;

/**
 * <p>搜索索引办事指南扩展类</p>
 * <p>搜索索引办事指南类的属性</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */
public class IndexBeanBsznInfo extends IndexBean {

	private String bszn_type; //服务类别  --- gk_fwlb 和数据字典关联
    private String bszn_org; //办事机构  ---- gk_bsjg
    private String bszn_object; //服务对象及范围  --- gk_bldx
    private String publish_time; //时间 
    private String categoryId; //栏目
    
    private String typed ; //政务公开中的小分类
    
	public IndexBeanBsznInfo() {
		super("zwgk");  
		setTyped("bszn"); 
	}
	public String getBszn_type() {
		return bszn_type;
	}
	public void setBszn_type(String bsznType) {
		bszn_type = bsznType;
	}
	public String getBszn_org() {
		return bszn_org;
	}
	public void setBszn_org(String bsznOrg) {
		bszn_org = bsznOrg;
	}
	public String getBszn_object() {
		return bszn_object;
	}
	public void setBszn_object(String bsznObject) {
		bszn_object = bsznObject;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publishTime) {
		publish_time = publishTime;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getTyped() {
		return typed;
	}
	public void setTyped(String typed) {
		this.typed = typed;
	}
	   
    
    
}
