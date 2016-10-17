package com.deya.wcm.bean.survey;
import com.deya.wcm.services.lable.data.IBean;
import java.util.*;
public class SurveyCategory implements IBean,java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8010505842065440635L;
	private int id;//主键id
	private String category_id = "";//记录ID
	private String c_name = "";//
	private String description = "";//
	private String model_path = "";//
	private int publish_status = 0;//发布状态
	private String publish_time = "";
	private String add_time = "";//添加时间
	private String add_user = "";//添加人
	private String update_time = "";//修改时间
	private String update_user = "";//修改人
	private int Is_delete = 0;//是否删除
	private int sort = 0;//排序
	private String template_list_path = "0";//列表页模板地址
	private String template_content_path = "0";//内容页模板地址
	private String template_result_path = "0";
	private String site_id = "";
	
	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String siteId) {
		site_id = siteId;
	}

	public Map toMap()
	{
		Map m = new HashMap();
		m.put("id", this.id);
		m.put("category_id",this.category_id );
		m.put("c_name",this.c_name);
		m.put("description",this.description );
		m.put("model_path", this.model_path);	
		m.put("publish_status", this.publish_status);
		return m;
	}
	
	public String getTemplate_result_path() {
		return template_result_path;
	}

	public void setTemplate_result_path(String templateResultPath) {
		template_result_path = templateResultPath;
	}
	
	public String getTemplate_list_path() {
		return template_list_path;
	}

	public void setTemplate_list_path(String templateListPath) {
		template_list_path = templateListPath;
	}

	public String getTemplate_content_path() {
		return template_content_path;
	}

	public void setTemplate_content_path(String templateContentPath) {
		template_content_path = templateContentPath;
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
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
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
	public int getIs_delete() {
		return Is_delete;
	}
	public void setIs_delete(int is_delete) {
		Is_delete = is_delete;
	}
	public String getModel_path() {
		return model_path;
	}
	public void setModel_path(String model_path) {
		this.model_path = model_path;
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
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
