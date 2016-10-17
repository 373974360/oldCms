package com.deya.wcm.bean.appeal.category;


public class CategoryBean implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6320202942203932698L;
	private int cat_id;
	private int parent_id=0;
    private String cat_cname;
    private String cat_position;
    private int cat_level;
    private int sort_id=0;
    
    
	public int getCat_id() {
		return cat_id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public String getCat_cname() {
		return cat_cname;
	}
	public String getCat_position() {
		return cat_position;
	}
	public int getCat_level() {
		return cat_level;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public void setCat_cname(String catCname) {
		cat_cname = catCname;
	}
	public void setCat_position(String catPosition) {
		cat_position = catPosition;
	}
	public void setCat_level(int catLevel) {
		cat_level = catLevel;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
 
}
