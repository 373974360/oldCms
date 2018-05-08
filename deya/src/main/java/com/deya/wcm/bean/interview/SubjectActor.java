package com.deya.wcm.bean.interview;

import java.util.HashMap;
import java.util.Map;

import com.deya.wcm.services.lable.data.IBean;

/**
 * 访谈主题参与者Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题参与者Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectActor implements IBean,java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7034610093652182437L;
	private int id;//自增序列id
	private String actor_id = "";//记录id
	private String sub_id = "";//关联主题id
	private String actor_name = "";//姓名
	private String age = "";//年龄
	private String sex = "";//性别
	private String email = "";//邮箱
	private String company = "";//单位
	private String a_post = "";//职务
	private String address = "";//地址
	private String description = "";//嘉宾介绍
	private String pic_path = "";//嘉宾图片地址
	private String add_time = "";//添加时间
	private String add_user = "";//添加人id
	private String update_time = "";//修改时间
	private String update_user = "";//修改人	
	private int sort = 999;//
	private int is_delete = 0;//是否删除
	private int is_db = 0;//数据是否巳入库，在bean 对象中存在，表中无此字段
	private String user_name = "";
	
	@SuppressWarnings("unchecked")
	public Map toMap()
	{
		Map m = new HashMap();
		m.put("id", this.id);
		m.put("actor_id",this.actor_id );
		m.put("actor_name",this.actor_name );
		m.put("age",this.age);
		m.put("sex",this.sex );
		m.put("email", this.email);
		m.put("company",this.company);
		m.put("a_post",this.a_post );
		m.put("address", address);
		m.put("description",description );
		m.put("pic_path",pic_path );
		return m;
	}
	
	public String getActor_id() {
		return actor_id;
	}
	public void setActor_id(String actor_id) {
		this.actor_id = actor_id;
	}
	public String getActor_name() {
		return actor_name;
	}
	public void setActor_name(String actor_name) {
		this.actor_name = actor_name;
	}
	public String getAdd_time() {
		return add_time;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIs_db() {
		return is_db;
	}
	public void setIs_db(int is_db) {
		this.is_db = is_db;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
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
	public String getA_post() {
		return a_post;
	}
	public void setA_post(String a_post) {
		this.a_post = a_post;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
