package com.deya.wcm.bean.survey;
import com.deya.wcm.services.lable.data.IBean;
import com.deya.util.DateUtil;

import java.util.*;
/**
 * 问卷调查Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 问卷调查Bean</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: Cicro</p>
 * @author zhuliang
 * @version 1.0
 * * 
 */
public class SurveyBean implements IBean,java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3100850564172389597L;
	private int id;//主键id
	private String s_id = "";//记录ID
	private String category_id = "";//问卷分类ID
	private String c_name = "";//分类名称，不存在于表中，只在此对象不存在
	private String s_name = "";//问卷名称
	private String description = "";//描述
	private int is_register = 0;//是否只允许注册会员发言
	private int ip_fre = 1;//同一IP允许重复提交次数
	private String start_time = "";//开始日期
	private String end_time = "";//结束日期
	private int is_show_subsort = 0;//是否显示题目序号
	private String model_path = "";//模板地址
	private String pic_path = "";//焦点图片地址
	private int show_result_status = 1;//显示调查结果状态  0不显示调查结果 1显示调查结果	 2显示文字说明
	private String verdict = "";//调查结论
	private String ip_restrict = "";//Ip段限制
	private int publish_status = 0;//发布状态
	private String publish_time = "";
	private int is_show_title = 0;//是否显示标题
	private int is_show_result = 0;//是否展示调查结果
	private String survey_content = "";//题目内容
	private String add_time = "";//添加时间
	private String add_user = "";//添加人
	private String update_time = "";//修改时间
	private String update_user = "";//修改人
	private int Is_delete = 0;//是否删除
	private int sort = 0;//排序
	private String user_name = "";
	private int answer_counts = 0;//答卷份数 不作为表字段	
	private int recommend_flag = 0;//推荐设置
	private String recommend_time = "";//推荐时间
	private String spacing_interval = "";//两次提交的间隔时间 以d,h,m开头，后面为时间表示天，小时，分钟
	private String is_end = "0";//是否截止，不做为表字段 0为进行状态　1为截止状态
	private String site_id = ""; 
	private int is_checkcode = 1;//是否需要验证码  0;1
	private String ywlsh="";//业务流水号
	private String file_path="";//附件
	private String wjbh="";//问卷编号
	private String fbqd="";//发布渠道

	private String creator="";//发起人
	private String depname="";//发起部门
	private String create_date="";//发起时间
	private String curinfo="";//审核流程

	private int back_status = 0;

	public int getIs_checkcode() {
		return is_checkcode;
	}

	public void setIs_checkcode(int isCheckcode) {
		is_checkcode = isCheckcode;
	}

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public Map<String,String> toMap()
	{
		Map<String,String> m = new HashMap<String,String>();
		m.put("id", ""+this.id);
		m.put("s_id",this.s_id );
		m.put("category_id",this.category_id );
		m.put("s_name",this.s_name);
		m.put("start_time",this.start_time );
		m.put("end_time", this.end_time);
		m.put("c_name",this.c_name);
		m.put("description",this.description );
		m.put("is_register", ""+is_register);
		m.put("ip_fre",""+ip_fre );
		m.put("model_path",model_path );
		m.put("show_result_status", ""+show_result_status);
		m.put("ip_restrict", ip_restrict);
		m.put("is_show_result", ""+is_show_result);
		m.put("survey_content", survey_content);
		m.put("is_end", getIs_end());
		return m;
	}
	
	public String getIs_end()
	{
		if(!"".equals(this.end_time))
		{			
			String current_date = DateUtil.getCurrentDate();
			if(!current_date.equals(this.end_time))
			{
				if(!DateUtil.compare_date(current_date,this.end_time))
					this.is_end = "1";
				else
					this.is_end = "0";
			}
			else
			{
				this.is_end = "0";
			}
		}
		else
		{
			this.is_end = "0";
		}
		return this.is_end;
	}
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	public int getIp_fre() {
		return ip_fre;
	}
	public void setIp_fre(int ip_fre) {
		this.ip_fre = ip_fre;
	}
	public String getIp_restrict() {
		return ip_restrict;
	}
	public void setIp_restrict(String ip_restrict) {
		this.ip_restrict = ip_restrict;
	}
	public int getIs_delete() {
		return Is_delete;
	}
	public void setIs_delete(int is_delete) {
		Is_delete = is_delete;
	}
	public int getIs_register() {
		return is_register;
	}
	public void setIs_register(int is_register) {
		this.is_register = is_register;
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
	public String getS_id() {
		return s_id;
	}
	public void setS_id(String s_id) {
		this.s_id = s_id;
	}
	public String getS_name() {
		return s_name;
	}
	public void setS_name(String s_name) {
		this.s_name = s_name;
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
	public int getIs_show_subsort() {
		return is_show_subsort;
	}
	public void setIs_show_subsort(int is_show_subsort) {
		this.is_show_subsort = is_show_subsort;
	}
	public String getSurvey_content() {
		return survey_content;
	}
	public void setSurvey_content(String survey_content) {
		this.survey_content = survey_content;
	}
	public int getAnswer_counts() {
		return answer_counts;
	}
	public void setAnswer_counts(int answer_counts) {
		this.answer_counts = answer_counts;
	}
	public int getIs_show_result() {
		return is_show_result;
	}
	public void setIs_show_result(int is_show_result) {
		this.is_show_result = is_show_result;
	}
	public int getIs_show_title() {
		return is_show_title;
	}
	public void setIs_show_title(int is_show_title) {
		this.is_show_title = is_show_title;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}
	public int getShow_result_status() {
		return show_result_status;
	}
	public void setShow_result_status(int show_result_status) {
		this.show_result_status = show_result_status;
	}
	public String getVerdict() {
		return verdict;
	}
	public void setVerdict(String verdict) {
		this.verdict = verdict;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public int getRecommend_flag() {
		return recommend_flag;
	}

	public void setRecommend_flag(int recommend_flag) {
		this.recommend_flag = recommend_flag;
	}

	public String getRecommend_time() {
		return recommend_time;
	}

	public void setRecommend_time(String recommend_time) {
		this.recommend_time = recommend_time;
	}

	public String getSpacing_interval() {
		return spacing_interval;
	}

	public void setSpacing_interval(String spacing_interval) {
		this.spacing_interval = spacing_interval;
	}

	public void setIs_end(String is_end) {
		this.is_end = is_end;
	}

	public int getBack_status() {
		return back_status;
	}

	public void setBack_status(int back_status) {
		this.back_status = back_status;
	}

	public String getYwlsh() {
		return ywlsh;
	}

	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getWjbh() {
		return wjbh;
	}

	public void setWjbh(String wjbh) {
		this.wjbh = wjbh;
	}

	public String getFbqd() {
		return fbqd;
	}

	public void setFbqd(String fbqd) {
		this.fbqd = fbqd;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCurinfo() {
		return curinfo;
	}

	public void setCurinfo(String curinfo) {
		this.curinfo = curinfo;
	}
}
