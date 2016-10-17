package com.deya.wcm.bean.zwgk.index;

/**
 * 公开信息流水号生产表
 * @author liqi
 *
 */
public class IndexSequenceBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4824287728668080067L;
	
	private int id;
	private String seq_item = "";
	private int seq_cur_value;
	private String seq_ymd = "";
	private int seq_type;
	private String app_id = "";
	private String site_id = "";
	
	// get方法
	public int getId() {
		return id;
	}
	public String getSeq_item() {
		return seq_item;
	}
	public int getSeq_cur_value() {
		return seq_cur_value;
	}
	public String getSeq_ymd() {
		return seq_ymd;
	}
	public int getSeq_type() {
		return seq_type;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	
	// set方法
	public void setId(int id) {
		this.id = id;
	}
	public void setSeq_item(String seqItem) {
		seq_item = seqItem;
	}
	public void setSeq_cur_value(int seqCurValue) {
		seq_cur_value = seqCurValue;
	}
	public void setSeq_ymd(String seqYmd) {
		seq_ymd = seqYmd;
	}
	public void setSeq_type(int seqType) {
		seq_type = seqType;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	

}
