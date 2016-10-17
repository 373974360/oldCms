package com.deya.wcm.bean.system.ware;

/**
 * 信息组件（区块）Bean
 * @author Administrator
 *	liqi
 */
public class WareBean implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 3072423803402743093L;

	private String id = "";
	private String ware_id = "";
	private String wcat_id = "";
	private String ware_name = "";//标签名称
	private String rss_url = "";
	private String ware_content = "";//标签内容
	private int ware_type = 0;//标签类型
	private int info_num = 0;
	private int ware_width = 0;
	private int ware_interval = 0;
	private String ware_url = "";
	private String ware_memo = "";//标签备注
	private String update_dtime = "";
	private String next_dtime = "";
	private int sort_id = 999;
	private String app_id = "";
	private String site_id = "";
	private int receive_recom = 0;
	public int getReceive_recom() {
		return receive_recom;
	}
	public void setReceive_recom(int receiveRecom) {
		receive_recom = receiveRecom;
	}
	public String getWare_id() {
		return ware_id;
	}
	public String getWcat_id() {
		return wcat_id;
	}
	public String getWare_name() {
		return ware_name;
	}
	public String getRss_url() {
		return rss_url;
	}
	public String getWare_content() {
		return ware_content;
	}
	public int getWare_type() {
		return ware_type;
	}
	public int getInfo_num() {
		return info_num;
	}
	public int getWare_width() {
		return ware_width;
	}
	public int getWare_interval() {
		return ware_interval;
	}
	public String getWare_url() {
		return ware_url;
	}
	public String getWare_memo() {
		return ware_memo;
	}
	public String getUpdate_dtime() {
		return update_dtime;
	}
	public String getNext_dtime() {
		return next_dtime;
	}
	public int getSort_id() {
		return sort_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setWare_id(String wareId) {
		ware_id = wareId;
	}
	public void setWcat_id(String wcatId) {
		wcat_id = wcatId;
	}
	public void setWare_name(String wareName) {
		ware_name = wareName;
	}
	public void setRss_url(String rssUrl) {
		rss_url = rssUrl;
	}
	public void setWare_content(String wareContent) {
		ware_content = wareContent;
	}
	public void setWare_type(int wareType) {
		ware_type = wareType;
	}
	public void setInfo_num(int infoNum) {
		info_num = infoNum;
	}
	public void setWare_width(int wareWidth) {
		ware_width = wareWidth;
	}
	public void setWare_interval(int wareInterval) {
		ware_interval = wareInterval;
	}
	public void setWare_url(String wareUrl) {
		ware_url = wareUrl;
	}
	public void setWare_memo(String wareMemo) {
		ware_memo = wareMemo;
	}
	public void setUpdate_dtime(String updateDtime) {
		update_dtime = updateDtime;
	}
	public void setNext_dtime(String nextDtime) {
		next_dtime = nextDtime;
	}
	public void setSort_id(int sortId) {
		sort_id = sortId;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
