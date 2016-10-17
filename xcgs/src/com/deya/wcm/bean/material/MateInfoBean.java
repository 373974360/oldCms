package com.deya.wcm.bean.material;

public class MateInfoBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4732045569676799535L;
	
	private int att_id;
	private int f_id;
	private String att_ename;
	private String att_cname;
	private String att_path;
	private String alias_name;
	private String thumb_url;
	private String hd_url;
	private String fileext;
	private long filesize;
	private int att_type;
	private String att_description;
	private  int user_id;
	private String upload_dtime;
	private String app_id;
	private String site_id;
	
	

	public int getAtt_id() {
		return att_id;
	}


	public int getF_id() {
		return f_id;
	}


	public String getAtt_ename() {
		return att_ename;
	}


	public String getAtt_cname() {
		return att_cname;
	}


	public String getAtt_path() {
		return att_path;
	}


	public String getAlias_name() {
		return alias_name;
	}


	public String getThumb_url() {
		return thumb_url;
	}


	public String getHd_url() {
		return hd_url;
	}


	public String getFileext() {
		return fileext;
	}

	public int getAtt_type() {
		return att_type;
	}


	public String getAtt_description() {
		return att_description;
	}


	public int getUser_id() {
		return user_id;
	}


	public String getUpload_dtime() {
		return upload_dtime;
	}


	public String getApp_id() {
		return app_id;
	}


	public String getSite_id() {
		return site_id;
	}


	public void setAtt_id(int attId) {
		att_id = attId;
	}


	public void setF_id(int fId) {
		f_id = fId;
	}


	public void setAtt_ename(String attEname) {
		att_ename = attEname;
	}


	public void setAtt_cname(String attCname) {
		att_cname = attCname;
	}


	public void setAtt_path(String attPath) {
		att_path = attPath;
	}


	public void setAlias_name(String aliasName) {
		alias_name = aliasName;
	}


	public void setThumb_url(String thumbUrl) {
		thumb_url = thumbUrl;
	}


	public void setHd_url(String hdUrl) {
		hd_url = hdUrl;
	}


	public void setFileext(String fileext) {
		this.fileext = fileext;
	}


	
	public Long getFilesize() {
		return filesize;
	}


	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}


	public void setAtt_type(int attType) {
		att_type = attType;
	}


	public void setAtt_description(String attDescription) {
		att_description = attDescription;
	}


	public void setUser_id(int userId) {
		user_id = userId;
	}


	public void setUpload_dtime(String uploadDtime) {
		upload_dtime = uploadDtime;
	}


	public void setApp_id(String appId) {
		app_id = appId;
	}


	public void setSite_id(String siteId) {
		site_id = siteId;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
