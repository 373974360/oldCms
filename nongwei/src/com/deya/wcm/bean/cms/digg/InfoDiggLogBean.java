package com.deya.wcm.bean.cms.digg;
/**
 * digg日志bean
 * @author 王磊
 *
 */
public class InfoDiggLogBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -758741655889930244L;
	private int id;
	private int info_id ;
	private int flag ;  //0支持；1反对
	private int user_id ;//会员id
	private String username = "";//会员名字
	private String ip = "";//访问IP
	private String digg_dtime = "";  //操作时间
	private String app_id = "";  //应用
	private String site_id = "";  //站点
	
	
	public int getInfo_id() {
		return info_id;
	}
	
	public void setInfo_id(int infoId) {
		info_id = infoId;
	}
	
	public int getFlag() {
		return flag;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int userId) {
		user_id = userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getDigg_dtime() {
		return digg_dtime;
	}
	
	public void setDigg_dtime(String diggDtime) {
		digg_dtime = diggDtime;
	}
	
	public String getApp_id() {
		return app_id;
	}
	
	public void setApp_id(String appId) {
		app_id = appId;
	}
	
	public String getSite_id() {
		return site_id;
	}
	
	public void setSite_id(String siteId) {
		site_id = siteId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
