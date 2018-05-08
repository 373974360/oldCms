package com.deya.wcm.bean.appCom.guestbook;

import java.io.Serializable;

public class GuestBookBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7672051674429369870L;
	private int id = 0;	
	private int gbs_id = 0;					
	private String nickname = "";						
	private String title = "";
	private String address = "";
	private String content = "";
	private int member_id = 0;	
	private String ip = "";
	private String class_id = "";
	private int publish_status = 0;	
	private int reply_status = 0;	
	private String add_time = "";
	private String reply_time = "";
	private String reply_content = "";			
	private String realname = "";
	private String phone = "";
	private String tel = "";
	private String post_code = "";
	private String idcard = "";
	private String vocation = "";
	private String age = "";
	private int sex = 0;	
	private int hits = 0;					
	private String site_id = "";
		
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String postCode) {
		post_code = postCode;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getVocation() {
		return vocation;
	}
	public void setVocation(String vocation) {
		this.vocation = vocation;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getHits() {
		return hits;
	}
	public void setHit(int hits) {
		this.hits = hits;
	}
	public int getGbs_id() {
		return gbs_id;
	}
	public void setGbs_id(int gbsId) {
		gbs_id = gbsId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int memberId) {
		member_id = memberId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String classId) {
		class_id = classId;
	}
	public int getPublish_status() {
		return publish_status;
	}
	public void setPublish_status(int publishStatus) {
		publish_status = publishStatus;
	}
	public int getReply_status() {
		return reply_status;
	}
	public void setReply_status(int replyStatus) {
		reply_status = replyStatus;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String addTime) {
		add_time = addTime;
	}
	public String getReply_time() {
		return reply_time;
	}
	public void setReply_time(String replyTime) {
		reply_time = replyTime;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String replyContent) {
		reply_content = replyContent;
	}
	
}
