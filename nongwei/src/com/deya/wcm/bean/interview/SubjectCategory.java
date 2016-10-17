package com.deya.wcm.bean.interview;

import java.util.HashMap;
import java.util.Map;

import com.deya.wcm.services.lable.data.IBean;

/**
 * 访谈主题分类Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题分类Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectCategory implements IBean,java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 961774871963305808L;
	private int id;
	private String category_id = "";//分类ID
	private String category_name = "";//分类名称
	private String description = "";//分类描述
	private int publish_status = 0;//发布状态
	private String publish_time = "";//	发布时间
	private int is_grade = 0;//是否允许评分
	private int is_comment = 0;//是否允许评论
	private int is_com_audit = 0;//评论是否要审核
	private int is_com_filter = 0;//评论是否要过滤
	private int is_p_audit = 0;//(图文直播区)发言是否需要审核
	private int is_p_filter = 0;//(图文直播区)发言是否需要过滤
	private int is_show_text = 1;//是否启用文字互动区
	private int is_register = 0;//是否只允许注册会员发言
	private int is_t_flink = 0;//是否开启昵称过滤
	private int is_t_keyw = 0;//是否开启关键词过滤
	private int is_t_audit = 1;//文字互动区发言是否审核
	private String m_forecast_path = "";//访谈预告新闻稿模板地址
	private String m_hlist_path = "";//历史访谈列表模板地址
	private String m_on_path = "";//访谈中模板地址
	private String m_h_path = "";//历史访谈回顾模板地址
	private String m_rlist_path = "";//相关信息列表模板地址
	private String m_rcontent_list = "";//相关信息内容模板地址
	private String add_time = "";//添加时间
	private String add_user = "";//添加人ID
	private String update_time = "";//修改时间
	private String update_user = "";//修改人	
	private String user_name = "";
	private int is_delete = 0;//是否删除
	private String site_id = "";
	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String siteId) {
		site_id = siteId;
	}

	@SuppressWarnings("unchecked")
	public Map toMap()
	{
		Map m = new HashMap();
		m.put("id", this.id);
		m.put("category_id",this.category_id );
		m.put("category_name",this.category_name );
		m.put("description",this.description);
		m.put("publish_status",this.publish_status );
		m.put("is_grade", this.is_grade);
		m.put("is_comment",this.is_comment);
		m.put("description",this.description );
		m.put("is_register", is_register);
		m.put("is_com_audit",is_com_audit );
		m.put("is_com_filter",is_com_filter );
		m.put("is_p_audit", is_p_audit);
		m.put("is_p_filter", is_p_filter);
		m.put("is_show_text", is_show_text);
		m.put("is_register", is_register);		
		m.put("is_t_flink",is_t_flink );
		m.put("is_t_keyw", is_t_keyw);
		m.put("is_t_audit", is_t_audit);
		m.put("m_forecast_path", m_forecast_path);
		m.put("m_hlist_path", m_hlist_path);
		
		m.put("m_on_path", m_on_path);		
		m.put("m_h_path",m_h_path );
		m.put("m_rlist_path", m_rlist_path);
		m.put("m_rcontent_list", m_rcontent_list);
		return m;
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
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
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
	public int getIs_com_audit() {
		return is_com_audit;
	}
	public void setIs_com_audit(int is_com_audit) {
		this.is_com_audit = is_com_audit;
	}
	public int getIs_com_filter() {
		return is_com_filter;
	}
	public void setIs_com_filter(int is_com_filter) {
		this.is_com_filter = is_com_filter;
	}
	public int getIs_comment() {
		return is_comment;
	}
	public void setIs_comment(int is_comment) {
		this.is_comment = is_comment;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public int getIs_grade() {
		return is_grade;
	}
	public void setIs_grade(int is_grade) {
		this.is_grade = is_grade;
	}
	public int getIs_p_audit() {
		return is_p_audit;
	}
	public void setIs_p_audit(int is_p_audit) {
		this.is_p_audit = is_p_audit;
	}
	public int getIs_p_filter() {
		return is_p_filter;
	}
	public void setIs_p_filter(int is_p_filter) {
		this.is_p_filter = is_p_filter;
	}
	public int getIs_register() {
		return is_register;
	}
	public void setIs_register(int is_register) {
		this.is_register = is_register;
	}
	public int getIs_show_text() {
		return is_show_text;
	}
	public void setIs_show_text(int is_show_text) {
		this.is_show_text = is_show_text;
	}
	public int getIs_t_audit() {
		return is_t_audit;
	}
	public void setIs_t_audit(int is_t_audit) {
		this.is_t_audit = is_t_audit;
	}
	public int getIs_t_flink() {
		return is_t_flink;
	}
	public void setIs_t_flink(int is_t_flink) {
		this.is_t_flink = is_t_flink;
	}
	public int getIs_t_keyw() {
		return is_t_keyw;
	}
	public void setIs_t_keyw(int is_t_keyw) {
		this.is_t_keyw = is_t_keyw;
	}
	public String getM_forecast_path() {
		return m_forecast_path;
	}
	public void setM_forecast_path(String m_forecast_path) {
		this.m_forecast_path = m_forecast_path;
	}
	public String getM_h_path() {
		return m_h_path;
	}
	public void setM_h_path(String m_h_path) {
		this.m_h_path = m_h_path;
	}
	public String getM_hlist_path() {
		return m_hlist_path;
	}
	public void setM_hlist_path(String m_hlist_path) {
		this.m_hlist_path = m_hlist_path;
	}
	public String getM_on_path() {
		return m_on_path;
	}
	public void setM_on_path(String m_on_path) {
		this.m_on_path = m_on_path;
	}
	public String getM_rcontent_list() {
		return m_rcontent_list;
	}
	public void setM_rcontent_list(String m_rcontent_list) {
		this.m_rcontent_list = m_rcontent_list;
	}
	public String getM_rlist_path() {
		return m_rlist_path;
	}
	public void setM_rlist_path(String m_rlist_path) {
		this.m_rlist_path = m_rlist_path;
	}
	public int getPublish_status() {
		return publish_status;
	}
	public void setPublish_status(int publish_status) {
		this.publish_status = publish_status;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
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
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
