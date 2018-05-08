package com.deya.wcm.bean.interview;
/**
 * 聊天室功能Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 聊天室功能Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class ChatBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3653264713070431625L;
	private int id;//自增序列id
	private String sub_id = ""; //关联主题ID
	private String chat_id = "";//记录id
	private String chat_user = "";//发言人
	private String content = "";//内容
	private String put_time = "";//提交时间
	private String ip = "";//ip
	private int audit_status = 0;//审核状态
	private String chat_type = "";//发言人类型
	private String chat_area = "";//发言人所属区域
	private int is_show = 0;//是否前台展示
	private int index_num = 0;//在集合中存在的下标
	private String user_num = "";//直播时游客默认的序号
	public String getUser_num() {
		return user_num;
	}
	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}
	public int getIndex_num() {
		return index_num;
	}
	public void setIndex_num(int index_num) {
		this.index_num = index_num;
	}
	public int getAudit_status() {
		return audit_status;
	}
	public void setAudit_status(int audit_status) {
		this.audit_status = audit_status;
	}
	public String getChat_id() {
		return chat_id;
	}
	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}
	public String getChat_type() {
		return chat_type;
	}
	public void setChat_type(String chat_type) {
		this.chat_type = chat_type;
	}
	public String getChat_user() {
		return chat_user;
	}
	public void setChat_user(String chat_user) {
		this.chat_user = chat_user;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getIs_show() {
		return is_show;
	}
	public void setIs_show(int is_show) {
		this.is_show = is_show;
	}
	public String getPut_time() {
		return put_time;
	}
	public void setPut_time(String put_time) {
		this.put_time = put_time;
	}
	public String getChat_area() {
		return chat_area;
	}
	public void setChat_area(String chat_area) {
		this.chat_area = chat_area;
	}
	public String getSub_id() {
		return sub_id;
	}
	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}
}
