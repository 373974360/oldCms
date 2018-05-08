package com.deya.wcm.bean.appeal.model;

import java.io.Serializable;

/**
 *  诉求业务表Bean类.
 * <p>Title: CicroDate</p>
 * <p>Description: 诉求业务表Bean类</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author yuduochao
 * @version 1.0
 */

public class ModelBean implements Serializable {

	/**
	 * 对应 ---诉求业务表cp_model
	 */
	private static final long serialVersionUID = -91709240422419253L;
     
	private int model_id;            //业务ID
	private String model_cname="";      //业务名称
	private int relevance_type=0;    //关联类型      0：部门1：领导人
	private int is_sort=0;           //是否需要分拣    0：不需要1：需要
	private int time_limit=15;       //办理时限
	private int warn_num=-5;          //提醒时间
	private int yellow_num=-2;        //黄牌时间
	private int red_num=2;           //红牌时间
	private String code_pre="GPPS";  //业务流水号：字头
	private String code_rule="YYYYMMDD";        //业务流水号：日期码
	private int code_num=4;          //业务流水号：随时位数
	private int query_num=4;         //查询密码位数
	private int must_member=0;       //只许会员参与0：可以非会员1：必须是会员
	private int wf_id;               //工作流ID
	private String remind_type="";      //公众提醒方式email：邮件提醒 sms：手机短信提醒	
	private int is_allow_comment;    // 是否允许评论
	private int is_comment_checked;  // 评论是否需要审核
	private int user_secret=0;  
	private int is_auto_publish=0;  
	private String model_memo="";
	private int template_form=0; 
	private int template_list=0; 
	private int template_content=0; 
	private int template_comment=0; 
	private int template_print=0; 	
	private int template_search_list=0;
	 
	public int getTemplate_search_list() {
		return template_search_list;
	}
	public void setTemplate_search_list(int templateSearchList) {
		template_search_list = templateSearchList;
	}
	public int getTemplate_print() {
		return template_print;
	}
	public void setTemplate_print(int templatePrint) {
		template_print = templatePrint;
	}
	public int getTemplate_comment() {
		return template_comment;
	}
	public void setTemplate_comment(int templateComment) {
		template_comment = templateComment;
	}
	public int getTemplate_form() {
		return template_form;
	}
	public void setTemplate_form(int templateForm) {
		template_form = templateForm;
	}
	public int getTemplate_list() {
		return template_list;
	}
	public void setTemplate_list(int templateList) {
		template_list = templateList;
	}
	public int getTemplate_content() {
		return template_content;
	}
	public void setTemplate_content(int templateContent) {
		template_content = templateContent;
	}
	public int getUser_secret() {
		return user_secret;
	}
	public void setUser_secret(int userSecret) {
		user_secret = userSecret;
	}
	public int getIs_auto_publish() {
		return is_auto_publish;
	}
	public void setIs_auto_publish(int isAutoPublish) {
		is_auto_publish = isAutoPublish;
	}
	public String getModel_memo() {
		return model_memo;
	}
	public void setModel_memo(String modelMemo) {
		model_memo = modelMemo;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public String getModel_cname() {
		return model_cname;
	}
	public void setModel_cname(String modelCname) {
		model_cname = modelCname;
	}
	public int getRelevance_type() {
		return relevance_type;
	}
	public void setRelevance_type(int relevanceType) {
		relevance_type = relevanceType;
	}
	public int getIs_sort() {
		return is_sort;
	}
	public void setIs_sort(int isSort) {
		is_sort = isSort;
	}
	public int getTime_limit() {
		return time_limit;
	}
	public void setTime_limit(int timeLimit) {
		time_limit = timeLimit;
	}
	public int getWarn_num() {
		return warn_num;
	}
	public void setWarn_num(int warnNum) {
		warn_num = warnNum;
	}
	public int getYellow_num() {
		return yellow_num;
	}
	public void setYellow_num(int yellowNum) {
		yellow_num = yellowNum;
	}
	public int getRed_num() {
		return red_num;
	}
	public void setRed_num(int redNum) {
		red_num = redNum;
	}
	public String getCode_pre() {
		return code_pre;
	}
	public void setCode_pre(String codePre) {
		code_pre = codePre;
	}
	public String getCode_rule() {
		return code_rule;
	}
	public void setCode_rule(String codeRule) {
		code_rule = codeRule;
	}
	public int getCode_num() {
		return code_num;
	}
	public void setCode_num(int codeNum) {
		code_num = codeNum;
	}
	public int getQuery_num() {
		return query_num;
	}
	public void setQuery_num(int queryNum) {
		query_num = queryNum;
	}
	public int getMust_member() {
		return must_member;
	}
	public void setMust_member(int mustMember) {
		must_member = mustMember;
	}
	public int getWf_id() {
		return wf_id;
	}
	public void setWf_id(int wfId) {
		wf_id = wfId;
	}
	public String getRemind_type() {
		return remind_type;
	}
	public void setRemind_type(String remindType) {
		remind_type = remindType;
	}
	public void setIs_allow_comment(int is_allow_comment) {
		this.is_allow_comment = is_allow_comment;
	}
	public int getIs_allow_comment() {
		return is_allow_comment;
	}
	public void setIs_comment_checked(int is_comment_checked) {
		this.is_comment_checked = is_comment_checked;
	}
	public int getIs_comment_checked() {
		return is_comment_checked;
	}
}
