package com.deya.wcm.bean.system.design;
/**
 * @Description:设计工具模块
 * @version 1.0
 * @date 2012-01-06 下午01:24:34
 */
public class DesignModuleBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2113322120951832705L;
	private int id = 0;
	private int module_id = 0;
	private int datasouce_type = 0;//数据源类型　0列表　1文本
	private String module_name = "";
	private String module_content = "";
	private String v_code  = "";
	private String thumb_url  = "";
	private String style_ids  = "";	
	private String attr_ids  = "";//所需要的属性设置项	
	private int weight = 0;
	private String app_id  = "";
	private String site_id  = "";
	public String getAttr_ids() {
		return attr_ids;
	}
	public void setAttr_ids(String attrIds) {
		attr_ids = attrIds;
	}
	public int getDatasouce_type() {
		return datasouce_type;
	}
	public void setDatasouce_type(int datasouceType) {
		datasouce_type = datasouceType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getModule_id() {
		return module_id;
	}
	public void setModule_id(int moduleId) {
		module_id = moduleId;
	}
	public String getModule_name() {
		return module_name;
	}
	public void setModule_name(String moduleName) {
		module_name = moduleName;
	}
	public String getModule_content() {
		return module_content;
	}
	public void setModule_content(String moduleContent) {
		module_content = moduleContent;
	}
	public String getV_code() {
		return v_code;
	}
	public void setV_code(String vCode) {
		v_code = vCode;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumbUrl) {
		thumb_url = thumbUrl;
	}
	public String getStyle_ids() {
		return style_ids;
	}
	public void setStyle_ids(String styleIds) {
		style_ids = styleIds;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
}
