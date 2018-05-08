package com.deya.wcm.bean.cms.info;

import java.util.ArrayList;
import java.util.List;

public class PicBean extends GKInfoBean implements java.io.Serializable{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 6636466978126090163L;
	private List<PicItemBean> item_list = new ArrayList<PicItemBean>();
	private String pic_content = "";     
	public String getPic_content() {
		return pic_content;   
	}     
	public void setPic_content(String picContent) {
		pic_content = picContent;
	}
	public List<PicItemBean> getItem_list() {
		return item_list;
	}
	public void setItem_list(List<PicItemBean> itemList) {
		item_list = itemList;
	}
}
