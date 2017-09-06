package com.deya.wcm.bean.zwgk.ysqgk;

/**
 *  依申请公开业务Bean
 * <p>Title: CicroDate</p>
 * <p>Description: 依申请公开业务Bean</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Cicro</p>
 * @author zhangqiang
 * @version 1.0
 * * 
 */

public class YsqgkConfigBean implements java.io.Serializable{

	
	private static final long serialVersionUID = -7652375947373208898L;
	private int id; 
	private int time_limit =15;
	private String code_pre ="YSQ";
	private String code_rule ="yyyyMMdd";
	private int code_num =4;
	private int query_num =4;
	private String file_url ="";
	private int must_member =0;
	private String remind_type ="";
	private int user_secret =0;
	private int is_auto_publish =0;
	private int template_form =0;
	private int template_list  =0;
	private int template_content  =0;
	private int template_over  =0;
	private int template_print  =0;
	private int template_query  =0;
	private String site_id="";

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	
	
	public int getId() {
		return id;
	}
	public int getTime_limit() {
		return time_limit;
	}
	public String getCode_pre() {
		return code_pre;
	}
	public String getCode_rule() {
		return code_rule;
	}
	public int getCode_num() {
		return code_num;
	}
	public int getQuery_num() {
		return query_num;
	}
	public String getFile_url() {
		return file_url;
	}
	public int getMust_member() {
		return must_member;
	}
	public String getRemind_type() {
		return remind_type;
	}
	public int getUser_secret() {
		return user_secret;
	}
	public int getIs_auto_publish() {
		return is_auto_publish;
	}
	public int getTemplate_form() {
		return template_form;
	}
	public int getTemplate_list() {
		return template_list;
	}
	public int getTemplate_content() {
		return template_content;
	}
	public int getTemplate_over() {
		return template_over;
	}
	public int getTemplate_print() {
		return template_print;
	}
	public int getTemplate_query() {
		return template_query;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTime_limit(int timeLimit) {
		time_limit = timeLimit;
	}
	public void setCode_pre(String codePre) {
		code_pre = codePre;
	}
	public void setCode_rule(String codeRule) {
		code_rule = codeRule;
	}
	public void setCode_num(int codeNum) {
		code_num = codeNum;
	}
	public void setQuery_num(int queryNum) {
		query_num = queryNum;
	}
	public void setFile_url(String fileUrl) {
		file_url = fileUrl;
	}
	public void setMust_member(int mustMember) {
		must_member = mustMember;
	}
	public void setRemind_type(String remindType) {
		remind_type = remindType;
	}
	public void setUser_secret(int userSecret) {
		user_secret = userSecret;
	}
	public void setIs_auto_publish(int isAutoPublish) {
		is_auto_publish = isAutoPublish;
	}
	public void setTemplate_form(int templateForm) {
		template_form = templateForm;
	}
	public void setTemplate_list(int templateList) {
		template_list = templateList;
	}
	public void setTemplate_content(int templateContent) {
		template_content = templateContent;
	}
	public void setTemplate_over(int templateOver) {
		template_over = templateOver;
	}
	public void setTemplate_print(int templatePrint) {
		template_print = templatePrint;
	}
	public void setTemplate_query(int templateQuery) {
		template_query = templateQuery;
	}
}
