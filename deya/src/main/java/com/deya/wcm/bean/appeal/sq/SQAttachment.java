package com.deya.wcm.bean.appeal.sq;

public class SQAttachment implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8108138689620847745L;
	private int sq_id = 0;
	private String att_name = "";
	private String att_path = "";
	private int relevance_type = 0;
	public int getSq_id() {
		return sq_id;
	}
	public void setSq_id(int sqId) {
		sq_id = sqId;
	}
	public String getAtt_name() {
		return att_name;
	}
	public void setAtt_name(String attName) {
		att_name = attName;
	}
	public String getAtt_path() {
		return att_path;
	}
	public void setAtt_path(String attPath) {
		att_path = attPath;
	}
	public int getRelevance_type() {
		return relevance_type;
	}
	public void setRelevance_type(int relevanceType) {
		relevance_type = relevanceType;
	}
}
