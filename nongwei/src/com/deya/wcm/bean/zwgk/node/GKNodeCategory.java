package com.deya.wcm.bean.zwgk.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GKNodeCategory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7126214808820217969L;
	private int nodcat_id = 0;
	private int parent_id = 0;
	private String nodcat_name = "";
	private String nod_position = "";
	private int sort_id = 999;
	private String app_id = "";
	private List<GKNodeCategory> class_list = new ArrayList<GKNodeCategory>();
	private List<GKNodeBean> node_list = new ArrayList<GKNodeBean>();
	public List<GKNodeBean> getNode_list() {
		return node_list;
	}
	public void setNode_list(List<GKNodeBean> nodeList) {
		node_list = nodeList;
	}
	public List<GKNodeCategory> getClass_list() {
		return class_list;
	}
	public void setClass_list(List<GKNodeCategory> classList) {
		class_list = classList;
	}
	public int getNodcat_id() {
		return nodcat_id;
	}
	public void setNodcat_id(int nodcatId) {
		nodcat_id = nodcatId;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public String getNodcat_name() {
		return nodcat_name;
	}
	public void setNodcat_name(String nodcatName) {
		nodcat_name = nodcatName;
	}
	public String getNod_position() {
		return nod_position;
	}
	public void setNod_position(String nodPosition) {
		nod_position = nodPosition;
	}
	public int getSort_id() {
		return sort_id;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	
}
