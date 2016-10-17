package com.deya.wcm.bean.zwgk.appcatalog;
//公开应用目录
public class AppCatalogBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 57811879513553903L;
	private int id = 0;
	private int cata_id = 0;
	private String cata_cname = "";
	private int parent_id  = 0;
	private String tree_position  = "";
	private int template_index  = 0;
	private int template_list  = 0;
	private int is_mutilpage  = 0;
	private String jump_url  = "";
	private String cat_keywords  = "";
	private String cat_description  = "";
	private String cat_memo  = "";
	private int cat_sort = 999;
	private String c_sql  = "";
	public int getCata_id() {
		return cata_id;
	}
	public void setCata_id(int cataId) {
		cata_id = cataId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCata_cname() {
		return cata_cname;
	}
	public void setCata_cname(String cataCname) {
		cata_cname = cataCname;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parentId) {
		parent_id = parentId;
	}
	public String getTree_position() {
		return tree_position;
	}
	public void setTree_position(String treePosition) {
		tree_position = treePosition;
	}
	public int getTemplate_index() {
		return template_index;
	}
	public void setTemplate_index(int templateIndex) {
		template_index = templateIndex;
	}
	public int getTemplate_list() {
		return template_list;
	}
	public void setTemplate_list(int templateList) {
		template_list = templateList;
	}
	public int getIs_mutilpage() {
		return is_mutilpage;
	}
	public void setIs_mutilpage(int isMutilpage) {
		is_mutilpage = isMutilpage;
	}
	public String getJump_url() {
		return jump_url;
	}
	public void setJump_url(String jumpUrl) {
		jump_url = jumpUrl;
	}
	public String getCat_keywords() {
		return cat_keywords;
	}
	public void setCat_keywords(String catKeywords) {
		cat_keywords = catKeywords;
	}
	public String getCat_description() {
		return cat_description;
	}
	public void setCat_description(String catDescription) {
		cat_description = catDescription;
	}
	public String getCat_memo() {
		return cat_memo;
	}
	public void setCat_memo(String catMemo) {
		cat_memo = catMemo;
	}
	public int getCat_sort() {
		return cat_sort;
	}
	public void setCat_sort(int catSort) {
		cat_sort = catSort;
	}
	public String getC_sql() {
		return c_sql;
	}
	public void setC_sql(String cSql) {
		c_sql = cSql;
	}
}
