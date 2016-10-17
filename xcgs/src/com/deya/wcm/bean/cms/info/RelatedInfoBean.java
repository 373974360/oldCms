package com.deya.wcm.bean.cms.info;

/**
 * @author 符江波
 * @version 1.0
 * @date 2011-6-22 下午02:41:14
 */
public class RelatedInfoBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2864646229263835438L;
	protected int info_id;
	protected int related_info_id;
	protected String title = "";
	protected String thumb_url = "";
	protected String content_url = "";
	protected int sort_id;
	protected int model_id;
	
	public int getInfo_id() {
		return info_id;
	}
	public int getRelated_info_id() {
		return related_info_id;
	}
	public String getTitle() {
		return title;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public String getContent_url() {
		return content_url;
	}
	public int getSort_id() {
		return sort_id;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}
	public void setRelated_info_id(int related_info_id) {
		this.related_info_id = related_info_id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}
	public void setContent_url(String content_url) {
		this.content_url = content_url;
	}
	public void setSort_id(int sort_id) {
		this.sort_id = sort_id;
	}
	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}
	
}
