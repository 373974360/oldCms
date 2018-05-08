package com.deya.wcm.bean.sendInfo;

public class AdoptRecordBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4692923841901090149L;
	private int id = 0;
	private int receive_info_id = 0;
	private int cat_id = 0;
	private String adopt_dtime	= "";
	private int adopt_user = 0;
	private int new_info_id = 0;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReceive_info_id() {
		return receive_info_id;
	}
	public void setReceive_info_id(int receiveInfoId) {
		receive_info_id = receiveInfoId;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public String getAdopt_dtime() {
		return adopt_dtime;
	}
	public void setAdopt_dtime(String adoptDtime) {
		adopt_dtime = adoptDtime;
	}
	public int getAdopt_user() {
		return adopt_user;
	}
	public void setAdopt_user(int adoptUser) {
		adopt_user = adoptUser;
	}
	public int getNew_info_id() {
		return new_info_id;
	}
	public void setNew_info_id(int newInfoId) {
		new_info_id = newInfoId;
	}
	
}
