package com.deya.wcm.bean.appeal.area;

import java.io.Serializable;

/**
 *  诉求地区分类Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求地区分类Bean类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */

public class AreaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6568341357997159923L;
   
	
	private int area_id = 0;           //地区分类ID
    private int parent_id = 0;         //父ID
    private String area_cname = "";    //地区分类名称 
    private String area_position = ""; //地区分类路径
    private int area_level = 1;        //深度/级别
    private int sort_id = 999;         
    
    
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int areaId) {
		area_id = areaId;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public String getArea_cname() {
		return area_cname;
	}
	public void setArea_cname(String areaCname) {
		area_cname = areaCname;
	}
	public String getArea_position() {
		return area_position;
	}
	public void setArea_position(String areaPosition) {
		area_position = areaPosition;
	}
	public int getArea_level() {
		return area_level;
	}
	public void setArea_level(int areaLevel) {
		area_level = areaLevel;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}
