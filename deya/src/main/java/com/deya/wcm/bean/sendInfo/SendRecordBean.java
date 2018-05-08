package com.deya.wcm.bean.sendInfo;


public class SendRecordBean implements java.io.Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -43736106645308592L;
	private int id = 0;
	private int send_info_id	= 0;
	private int send_cat_id	= 0;
	private int send_model_id = 0;//表中无此字段
	private String title = "";//表中无此字段
	private String send_site_id	= "";
	private String send_app_id	= "";
	private String send_time	= "";
	private int send_user_id	= 0;
	private String t_site_id	= "";
	private String t_site_name	= "";
	private int t_cat_id	= 0;
	private String t_cat_cname	= "";
	private int adopt_status	= 0;
	private String adopt_dtime	= "";
	private String adopt_desc ="";
	
	public SendRecordBean clone(){
		SendRecordBean o = null;
        try{
            o = (SendRecordBean)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSend_model_id() {
		return send_model_id;
	}
	public void setSend_model_id(int sendModelId) {
		send_model_id = sendModelId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSend_info_id() {
		return send_info_id;
	}
	public void setSend_info_id(int sendInfoId) {
		send_info_id = sendInfoId;
	}
	public int getSend_cat_id() {
		return send_cat_id;
	}
	public void setSend_cat_id(int sendCatId) {
		send_cat_id = sendCatId;
	}
	public String getSend_site_id() {
		return send_site_id;
	}
	public void setSend_site_id(String sendSiteId) {
		send_site_id = sendSiteId;
	}
	public String getSend_app_id() {
		return send_app_id;
	}
	public void setSend_app_id(String sendAppId) {
		send_app_id = sendAppId;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String sendTime) {
		send_time = sendTime;
	}
	public int getSend_user_id() {
		return send_user_id;
	}
	public void setSend_user_id(int sendUserId) {
		send_user_id = sendUserId;
	}
	public String getT_site_id() {
		return t_site_id;
	}
	public void setT_site_id(String tSiteId) {
		t_site_id = tSiteId;
	}
	public String getT_site_name() {
		return t_site_name;
	}
	public void setT_site_name(String tSiteName) {
		t_site_name = tSiteName;
	}
	public int getT_cat_id() {
		return t_cat_id;
	}
	public void setT_cat_id(int tCatId) {
		t_cat_id = tCatId;
	}
	public String getT_cat_cname() {
		return t_cat_cname;
	}
	public void setT_cat_cname(String tCatCname) {
		t_cat_cname = tCatCname;
	}
	public int getAdopt_status() {
		return adopt_status;
	}
	public void setAdopt_status(int adoptStatus) {
		adopt_status = adoptStatus;
	}
	public String getAdopt_dtime() {
		return adopt_dtime;
	}
	public void setAdopt_dtime(String adoptDtime) {
		adopt_dtime = adoptDtime;
	}
	public String getAdopt_desc() {
		return adopt_desc;
	}
	public void setAdopt_desc(String adoptDesc) {
		adopt_desc = adoptDesc;
	}
}
