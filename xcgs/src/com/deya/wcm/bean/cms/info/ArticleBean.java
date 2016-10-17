package com.deya.wcm.bean.cms.info;

/**
 * @author 符江波
 * @version 1.0
 * @date 2011-6-14 下午02:07:45
 */
public class ArticleBean extends GKInfoBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6598237580496572399L;
	private String info_content = "";
	private String info_status = "";
	/*
	private int page_count;
	private int prepage_wcount;
	private int word_count;
	*/
	
	public String getInfo_content() {
		return info_content;
	}
	/*
	public int getPage_count() {
		return page_count;
	}
	public int getPrepage_wcount() {
		return prepage_wcount;
	}
	public int getWord_count() {
		return word_count;
	}
	*/
	public void setInfo_content(String info_content) {
		this.info_content = info_content;
	}
	/*
	public void setPage_count(int page_count) {
		this.page_count = page_count;
	}
	public void setPrepage_wcount(int prepage_wcount) {
		this.prepage_wcount = prepage_wcount;
	}
	public void setWord_count(int word_count) {
		this.word_count = word_count;
	}
	*/
}
