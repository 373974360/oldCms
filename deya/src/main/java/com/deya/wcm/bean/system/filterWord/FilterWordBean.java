/**
 * 关键词过滤实例
 */
package com.deya.wcm.bean.system.filterWord;

/**
 * @author 王磊
 *
 */
public class FilterWordBean{

	private int filterword_id;//
	private String pattern;//过滤词
	private String replacement;//替换词
	private String app_id;//应用ID
	private String site_id;// 站点/节点ID
	
	public int getFilterword_id() {
		return filterword_id;
	}
	public void setFilterword_id(int filterwordId) {
		filterword_id = filterwordId;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getReplacement() {
		return replacement;
	}
	public void setReplacement(String replacement) {
		this.replacement = replacement;
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
