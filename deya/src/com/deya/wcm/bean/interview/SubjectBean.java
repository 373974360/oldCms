package com.deya.wcm.bean.interview;

import java.util.*;

import com.deya.wcm.services.lable.data.IBean;

/**
 * 访谈主题Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 访谈主题Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SubjectBean implements IBean,java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7088726010521403119L;
	private int id;
	private String sub_id = "";//主题ID
	private String category_id = "";//分类ID
	private String category_name = "";//分类名称
	private String sub_name = "";//主题名称
	private String summary = "";//访谈摘要
	private String intro = "";//访谈简介
	private String info = "";//访谈新闻稿
	private String start_time = "";//开始时间
	private String end_time = "";//结束时间
	private int status = 0;	//访谈状态
	private int audit_status = 0;//审核状态
	private int publish_status = 0;//发布状态
	private String publish_time = "";//发布时间
	private int is_auto_audit = 0;//是否启用自动审核
	private int auto_audit_time = 0;//自动审核时间
	private String apply_time = "";//申请时间
	private String apply_user = "";//申请人id
	private String apply_dept = "";//申请部门id
	
	private String update_time = "";//修改时间
	private String update_user = "";//修改人
	private String audit_time = "";//审核时间
	private String audit_user = "";//审核步骤
	private int is_delete = 0;//是否删除
	private int sort = 0;//排序
	private int click_count = 0;//排序
	private int submit_status = 0;//提交状态
	private String user_name = "";//
	private String s_for_video = "";//访谈视频地址

	private String s_for_pic = "";//访谈图片地址
	private String s_live_video = "";//直播视频地址
	private String s_history_video = "";//历史视频地址	
	private int recommend_flag = 0;//推荐设置
	private String recommend_time = "";//推荐时间	
	private String history_record_pic = "";//图文区历史记录维护
	private String history_record_text = "";//互动区历史记录维护
	private int count_user = 0;//参与访谈人数
	
	private String actor_name = "";//得到访谈参与者 表字段中没有
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
		Map<String,String> m = new HashMap();
		m.put("id", ""+this.id);
		m.put("sub_id",this.sub_id );
		m.put("category_id",this.category_id );
		m.put("sub_name",this.sub_name);
		m.put("start_time",this.start_time );
		m.put("end_time", this.end_time);
		m.put("intro",this.intro);
		m.put("info",this.info );
		m.put("start_time", start_time);
		m.put("end_time",end_time );
		m.put("status",""+status );
		m.put("publish_status", ""+publish_status);
		m.put("s_for_video", s_for_video);
		m.put("s_for_pic", s_for_pic);
		m.put("s_live_video", s_live_video);
		m.put("s_history_video", s_history_video);
		m.put("history_record_pic", history_record_pic);
		m.put("history_record_text", history_record_text);
		m.put("count_user", ""+count_user);
		m.put("actor_name", getActor_Name(this.sub_id));
		return m;
	}
	
	@SuppressWarnings("unchecked")
	public String getActor_Name(String sub_id)
	{
		String a_name = "";
		List<SubjectActor> l = com.deya.wcm.services.interview.SubjectActorServices.getAllActorName(sub_id);
		if(l != null && l.size() > 0)
		{
			for(int i=0;i<l.size();i++)
			{
				a_name += ","+l.get(i).getActor_name();
			}
			a_name = a_name.substring(1).replace(",", "&nbsp;");
			this.actor_name = a_name;
		}
		return a_name;
	}
	
	public String getActor_name() {
		return actor_name;
	}

	public void setActor_name(String actorName) {
		actor_name = actorName;
	}
	
	public int getRecommend_flag() {
		return recommend_flag;
	}
	public void setRecommend_flag(int recommend_flag) {
		this.recommend_flag = recommend_flag;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getApply_dept() {
		return apply_dept;
	}
	public void setApply_dept(String apply_dept) {
		this.apply_dept = apply_dept;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getApply_user() {
		return apply_user;
	}
	public void setApply_user(String apply_user) {
		this.apply_user = apply_user;
	}
	public int getAudit_status() {
		return audit_status;
	}
	public void setAudit_status(int audit_status) {
		this.audit_status = audit_status;
	}
	public String getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}
	public String getAudit_user() {
		return audit_user;
	}
	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}
	public int getAuto_audit_time() {
		return auto_audit_time;
	}
	public void setAuto_audit_time(int auto_audit_time) {
		this.auto_audit_time = auto_audit_time;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public int getClick_count() {
		return click_count;
	}
	public void setClick_count(int click_count) {
		this.click_count = click_count;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getIs_auto_audit() {
		return is_auto_audit;
	}
	public void setIs_auto_audit(int is_auto_audit) {
		this.is_auto_audit = is_auto_audit;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public int getPublish_status() {
		return publish_status;
	}
	public void setPublish_status(int publish_status) {
		this.publish_status = publish_status;
	}
	public String getS_for_pic() {
		return s_for_pic;
	}
	public void setS_for_pic(String s_for_pic) {
		this.s_for_pic = s_for_pic;
	}
	public String getS_for_video() {
		return s_for_video;
	}
	public void setS_for_video(String s_for_video) {
		this.s_for_video = s_for_video;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSub_id() {
		return sub_id;
	}
	public void setSub_id(String sub_id) {
		this.sub_id = sub_id;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public int getSubmit_status() {
		return submit_status;
	}
	public void setSubmit_status(int submit_status) {
		this.submit_status = submit_status;
	}
	
	public String getS_history_video() {
		return s_history_video;
	}
	public void setS_history_video(String s_history_video) {
		this.s_history_video = s_history_video;
	}
	public String getS_live_video() {
		return s_live_video;
	}
	public void setS_live_video(String s_live_video) {
		this.s_live_video = s_live_video;
	}
	public String getRecommend_time() {
		return recommend_time;
	}
	public void setRecommend_time(String recommend_time) {
		this.recommend_time = recommend_time;
	}

	public int getCount_user() {
		return count_user;
	}

	public void setCount_user(int count_user) {
		this.count_user = count_user;
	}

	public String getHistory_record_pic() {
		return history_record_pic;
	}

	public void setHistory_record_pic(String history_record_pic) {
		this.history_record_pic = history_record_pic;
	}

	public String getHistory_record_text() {
		return history_record_text;
	}

	public void setHistory_record_text(String history_record_text) {
		this.history_record_text = history_record_text;
	}	
	
	
}
