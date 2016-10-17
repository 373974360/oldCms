package com.deya.wcm.bean.search;

/**
 * <p>搜索索引基础类</p>
 * <p>搜索索引类的属性</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author lisupei
 * @version 1.0
 */
public class IndexBean {

	private static String type ;//搜索标示
	private String id;
	private String title;
	private String content;
	private String url;
	private String site_id;
	private String app_id;
	private String node_id;//公开节点id
	private String model_id;//内容模型id
	
	private String is_host = "master";//信息类型      主信息：master:0       引用：refer:1
	
	public IndexBean(String type){
		 this.setType(type);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		IndexBean.type = type;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String siteId) {
		site_id = siteId;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String appId) {
		app_id = appId;
	}

	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String nodeId) {
		node_id = nodeId;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String modelId) {
		model_id = modelId;
	}

	public String getIs_host() {
		return is_host;
	}

	public void setIs_host(String is_host) {
		if(is_host==null || is_host==""){
			is_host = "0";
		}
		if(is_host.equals("0")){
			is_host = "master";
		}else if(is_host.equals("1")){
			is_host = "refer";
		}
		this.is_host = is_host;
	}
	
	
}
