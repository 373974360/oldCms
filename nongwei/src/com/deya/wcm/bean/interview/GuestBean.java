package com.deya.wcm.bean.interview;
/**
 * 聊天室游客Bean.
 * <p>Title: CicroDate</p>
 * <p>Description: 聊天室游客Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class GuestBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3026305367824566220L;
	private String user_num = ""; //游客登录用户序号，如果是会员，使用会员的登录名
	private String user_name = ""; //登录用户名(会员用)
	private String nick_name = "";//昵称
	private String ip = ""; //ip
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getUser_num() {
		return user_num;
	}
	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
