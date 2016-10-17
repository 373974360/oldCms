package com.deya.wcm.bean.interview;

import java.util.HashMap;
import java.util.Map;

import com.deya.wcm.services.lable.data.IBean;

/**
 * 访谈主题相关信息Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题相关信息Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubReleInfo implements IBean,java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5874123383884786932L;
	private int id;//自增序列id
	private String info_id = "";//记录id
	private String sub_id = "";//关联主题id
	private String info_name = "";//新闻名称
	private String info_type = "html";//新闻类型
	private String content = "";//内容
	private String curl = "";	//url类型 时跳转的URL地址
	private String affix_path = ""; //附件XML：<cicroxml><file>aaa.doc</file><file>bbb.doc</file></cicroxml>
	private String add_time = "";//添加时间
	private String add_user = "";//添加人id
	private String update_time = "";//修改时间
	private String update_user = "";//修改人	
	private int sort = 999;//排序
	private int is_delete = 0;//是否删除	
	private int publish_flag = 0; //发布状态  0为未发布，1为已发布  2为已撤消
	
	@SuppressWarnings("unchecked")
	public Map toMap()
	{
		Map<String,String> m = new HashMap();
		m.put("id", ""+this.id);
		m.put("info_id",this.info_id );
		m.put("sub_id",this.sub_id );
		m.put("info_name",this.info_name);
		m.put("info_type",this.info_type );
		m.put("content", this.content);
		m.put("curl",this.curl);
		m.put("affix_path",this.affix_path );
		m.put("publish_flag", ""+publish_flag);
		return m;
	}
	
	public int getPublish_flag() {
		return publish_flag;
	}
	public void setPublish_flag(int publishFlag) {
		publish_flag = publishFlag;
	}
	public String getInfo_id() {
		return info_id;
	}
	public void setInfo_id(String info_id) {
		this.info_id = info_id;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCurl() {
		return curl;
	}
	public void setCurl(String curl) {
		this.curl = curl;
	}
	public String getAffix_path() {
		return affix_path;
	}
	public void setAffix_path(String affixPath) {
		affix_path = affixPath;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInfo_name() {
		return info_name;
	}
	public void setInfo_name(String info_name) {
		this.info_name = info_name;
	}
	public String getInfo_type() {
		return info_type;
	}
	public void setInfo_type(String info_type) {
		this.info_type = info_type;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
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
	
}
