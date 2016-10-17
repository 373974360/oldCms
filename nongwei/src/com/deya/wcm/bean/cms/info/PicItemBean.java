package com.deya.wcm.bean.cms.info;

public class PicItemBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4977207423763042235L;
	private int info_id = 0;
	private int pic_id = 0;	
	private int att_id = 0;	
	private String pic_path = "";
	private String pic_note = "";
	private String pic_url = "";
	private int pic_sort = 0;
	private String pic_title = "";
	private String pic_content = "";//这个字段只是用于插入信息时用，取信息时用PICBean中的pic_content
	//因为pic_content只有一个默认写到排序为第１个图片的记录中
	
	public String getPic_title() {
		return pic_title;
	}
	public void setPic_title(String picTitle) {
		pic_title = picTitle;
	}
	public String getPic_content() {
		return pic_content;
	}
	public void setPic_content(String picContent) {
		pic_content = picContent;
	}
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	public int getPic_id() {
		return pic_id;
	}
	public void setPic_id(int picId) {
		pic_id = picId;
	}
	public int getAtt_id() {
		return att_id;
	}
	public void setAtt_id(int attId) {
		att_id = attId;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String picPath) {
		pic_path = picPath;
	}
	public String getPic_note() {
		return pic_note;
	}
	public void setPic_note(String picNote) {
		pic_note = picNote;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String picUrl) {
		pic_url = picUrl;
	}
	public int getPic_sort() {
		return pic_sort;
	}
	public void setPic_sort(int picSort) {
		pic_sort = picSort;
	}	
}
