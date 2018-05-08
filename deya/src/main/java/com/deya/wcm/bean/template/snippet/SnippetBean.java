package com.deya.wcm.bean.template.snippet;

/**
 *  代码片断Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description:  代码片断Bean类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 * * 
 */
public class SnippetBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6244627846606999601L;

	
	private int id;              //ID
	private int sni_id;          //片断ID
	private String sni_name;     //片断名称
	private String sni_content;  //片断内容
	private String app_id;       //应用ID
	private String site_id;      //站点ID、公开节点ID
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSni_id() {
		return sni_id;
	}
	public void setSni_id(int sniId) {
		sni_id = sniId;
	}
	public String getSni_name() {
		return sni_name;
	}
	public void setSni_name(String sniName) {
		sni_name = sniName;
	}
	public String getSni_content() {
		return sni_content;
	}
	public void setSni_content(String sniContent) {
		sni_content = sniContent;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
