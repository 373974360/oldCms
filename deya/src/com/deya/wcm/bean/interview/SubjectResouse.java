package com.deya.wcm.bean.interview;
/**
 * 访谈主题资源附件信息类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题资源附件信息类Bean　如下载信息，图片及视频</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectResouse implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3045801488671957872L;
	private int id;
	private String sub_id  = "";//关联主题ID
	private String affix_type = "";//附件类型
	private String affix_path = "";//附件地址
	private String affix_name = "";//附件名称
	private String description = "";
	private String affix_status = "";//附件所属状态
	private String add_time = "";//添加时间
	private String add_user = "";//添加人id
	private String update_time = "";//修改时间
	private String update_user = "";//修改人
	private int sotr = 0; 
	private int is_delete = 0;//是否删除
	private String user_name = "";//
	/*
	public SubjectResouse(String sub_id,String affix_type,String affix_path,String affix_name,String affix_status,
			String add_time,String add_user,String update_time,String update_user,String user_name)
	{
		this.sub_id = sub_id;
		this.affix_type = affix_type;
		this.affix_path = affix_path;
		this.affix_name = affix_name;
		this.affix_status = affix_status;
		this.add_time = add_time;
		this.add_user = add_user;
		this.user_name = user_name;
		this.update_time = update_time;
		this.update_user = update_user;
	}*/
	
	public String getAdd_time() {
		return add_time;
	}
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getAdd_user() {
		return add_user;
	}
	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}
	public String getAffix_name() {
		return affix_name;
	}
	public void setAffix_name(String affix_name) {
		this.affix_name = affix_name;
	}
	public String getAffix_path() {
		return affix_path;
	}
	public void setAffix_path(String affix_path) {
		this.affix_path = affix_path;
	}
	public String getAffix_status() {
		return affix_status;
	}
	public void setAffix_status(String affix_status) {
		this.affix_status = affix_status;
	}
	public String getAffix_type() {
		return affix_type;
	}
	public void setAffix_type(String affix_type) {
		this.affix_type = affix_type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public int getSotr() {
		return sotr;
	}
	public void setSotr(int sotr) {
		this.sotr = sotr;
	}
	public String getSub_id() {
		return sub_id;
	}
	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
