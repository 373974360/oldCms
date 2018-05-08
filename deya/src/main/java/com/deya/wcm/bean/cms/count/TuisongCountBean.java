package com.deya.wcm.bean.cms.count;

public class TuisongCountBean implements java.io.Serializable{

	/**
	 * 站点推送信息统计
	 * 
	 */
	private static final long serialVersionUID = -4357978566686491115L;
	
	private int info_id = 0;  //信息id
	private int is_host =0;   //主信息条数
	private int isNot_host =0;   //推送信息条数
	private String site_id = "";
	private String site_name = "";
	private int cat_id = 0;      //栏目id
    private String cat_name =""; //栏目名称
	private int from_id  =0;     //引用id
	private int user_id = 0;     //
	private String app_id = "";  //所属应用
	
	private int icount =0;   //临时变量

	
	

	public int getInfo_id() {
		return info_id;
	}
	public int getIcount() {
		return icount;
	}
	public void setIcount(int icount) {
		this.icount = icount;
	}
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String catName) {
		cat_name = catName;
	}
	public int getIs_host() {
		return is_host;
	}
	public int getIsNot_host() {
		return isNot_host;
	}
	public String getSite_id() {
		return site_id;
	}
	public String getSite_name() {
		return site_name;
	}
	public int getCat_id() {
		return cat_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public int getFrom_id() {
		return from_id;
	}
	public void setIs_host(int isHost) {
		is_host = isHost;
	}
	public void setIsNot_host(int isNotHost) {
		isNot_host = isNotHost;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public void setSite_name(String siteName) {
		site_name = siteName;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setFrom_id(int fromId) {
		from_id = fromId;
	}	
}