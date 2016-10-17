package com.deya.wcm.bean.zwgk.ysqgk;

/**
 * 依申请 公开信息Bean
 * <p>Title: CicroDate</p>
 * <p>Description: 依申请 公开信息Bean</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * @author zhangqiang
 * @version 1.0
 * @date 2011-07-28 晚20:33:34
 */

public class YsqgkBean implements java.io.Serializable{

	private static final long serialVersionUID = -4157169494257766965L;
	private int ysq_id=0;
	private String ysq_code="";
	private String query_code="";
	private int ysq_type=0;
	private String name="";
	private String company="";
	private String card_name="";
	private String card_code="";
	private String org_code="";
	private String licence="";
	private String legalperson="";
	private String linkman="";
	private String tel="";
	private String fax="";
	private String phone="";
	private String email="";
	private String postcode="";
	private String address="";
	private String put_dtime="";
	private String content=""; 
	private String gk_index="";
	private String description="";
	private int is_derate=0;
	private int do_state=0;
	private String offer_type="";
	private String get_method="";
	private int is_other=0;
	private int is_third=0;
	private int is_extend=0;
	private String accept_content="";
	private String accept_dtime="";
	private int accept_user=0;
	private int reply_type=1;
	private String reply_content="";
	private String reply_dtime="";
	private int reply_user=0;
	private int is_mail=0;
	private String node_id="";
	private String node_name="";
	private int o_state=0;
	private int final_status=0;
	private int publish_state=0;
	private int supervise_flag=0;
	private int time_limit=15;
	private int timeout_flag=0;
	private int sat_score=0;
	private int hits=0;
	private int day_hits=0;
	private int week_hits=0;
	private int month_hits=0;
	private String lasthit_dtime="";
	private int weight=60;
	

	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getDo_state() {
		return do_state;
	}
	public void setDo_state(int doState) {
		do_state = doState;
	}
	public int getYsq_id() {
		return ysq_id;
	}
	public String getYsq_code() {
		return ysq_code;
	}
	public String getQuery_code() {
		return query_code;
	}
	public int getYsq_type() {
		return ysq_type;
	}
	public String getName() {
		return name;
	}
	public String getCompany() {
		return company;
	}
	public String getCard_name() {
		return card_name;
	}
	public String getCard_code() {
		return card_code;
	}
	public String getOrg_code() {
		return org_code;
	}
	public String getLicence() {
		return licence;
	}
	public String getLegalperson() {
		return legalperson;
	}
	public String getLinkman() {
		return linkman;
	}
	public String getTel() {
		return tel;
	}
	public String getFax() {
		return fax;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getAddress() {
		return address;
	}
	public String getPut_dtime() {
		return put_dtime;
	}
	public String getContent() {
		return content;
	}
	public String getGk_index() {
		return gk_index;
	}
	public String getDescription() {
		return description;
	}
	public int getIs_derate() {
		return is_derate;
	}
	public String getOffer_type() {
		return offer_type;
	}
	public String getGet_method() {
		return get_method;
	}
	public int getIs_other() {
		return is_other;
	}
	public int getIs_third() {
		return is_third;
	}
	public int getIs_extend() {
		return is_extend;
	}
	public String getAccept_content() {
		return accept_content;
	}
	public String getAccept_dtime() {
		return accept_dtime;
	}
	public int getAccept_user() {
		return accept_user;
	}
	public int getReply_type() {
		return reply_type;
	}
	public String getReply_content() {
		return reply_content;
	}
	public String getReply_dtime() {
		return reply_dtime;
	}
	public int getReply_user() {
		return reply_user;
	}
	public int getIs_mail() {
		return is_mail;
	}
	public String getNode_id() {
		return node_id;
	}
	public String getNode_name() {
		return node_name;
	}
	public int getO_state() {
		return o_state;
	}
	public int getFinal_status() {
		return final_status;
	}
	public int getPublish_state() {
		return publish_state;
	}
	public int getSupervise_flag() {
		return supervise_flag;
	}
	public int getTime_limit() {
		return time_limit;
	}
	public int getTimeout_flag() {
		return timeout_flag;
	}
	public int getSat_score() {
		return sat_score;
	}
	public int getHits() {
		return hits;
	}
	public int getDay_hits() {
		return day_hits;
	}
	public int getWeek_hits() {
		return week_hits;
	}
	public int getMonth_hits() {
		return month_hits;
	}
	public String getLasthit_dtime() {
		return lasthit_dtime;
	}
	public void setYsq_id(int ysqId) {
		ysq_id = ysqId;
	}
	public void setYsq_code(String ysqCode) {
		ysq_code = ysqCode;
	}
	public void setQuery_code(String queryCode) {
		query_code = queryCode;
	}
	public void setYsq_type(int ysqType) {
		ysq_type = ysqType;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public void setCard_name(String cardName) {
		card_name = cardName;
	}
	public void setCard_code(String cardCode) {
		card_code = cardCode;
	}
	public void setOrg_code(String orgCode) {
		org_code = orgCode;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPut_dtime(String putDtime) {
		put_dtime = putDtime;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setGk_index(String gkIndex) {
		gk_index = gkIndex;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setIs_derate(int isDerate) {
		is_derate = isDerate;
	}
	public void setOffer_type(String offerType) {
		offer_type = offerType;
	}
	public void setGet_method(String getMethod) {
		get_method = getMethod;
	}
	public void setIs_other(int isOther) {
		is_other = isOther;
	}
	public void setIs_third(int isThird) {
		is_third = isThird;
	}
	public void setIs_extend(int isExtend) {
		is_extend = isExtend;
	}
	public void setAccept_content(String acceptContent) {
		accept_content = acceptContent;
	}
	public void setAccept_dtime(String acceptDtime) {
		accept_dtime = acceptDtime;
	}
	public void setAccept_user(int acceptUser) {
		accept_user = acceptUser;
	}
	public void setReply_type(int replyType) {
		reply_type = replyType;
	}
	public void setReply_content(String replyContent) {
		reply_content = replyContent;
	}
	public void setReply_dtime(String replyDtime) {
		reply_dtime = replyDtime;
	}
	public void setReply_user(int replyUser) {
		reply_user = replyUser;
	}
	public void setIs_mail(int isMail) {
		is_mail = isMail;
	}
	public void setNode_id(String nodeId) {
		node_id = nodeId;
	}
	public void setNode_name(String nodeName) {
		node_name = nodeName;
	}
	public void setO_state(int oState) {
		o_state = oState;
	}
	public void setFinal_status(int finalStatus) {
		final_status = finalStatus;
	}
	public void setPublish_state(int publishState) {
		publish_state = publishState;
	}
	public void setSupervise_flag(int superviseFlag) {
		supervise_flag = superviseFlag;
	}
	public void setTime_limit(int timeLimit) {
		time_limit = timeLimit;
	}
	public void setTimeout_flag(int timeoutFlag) {
		timeout_flag = timeoutFlag;
	}
	public void setSat_score(int satScore) {
		sat_score = satScore;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public void setDay_hits(int dayHits) {
		day_hits = dayHits;
	}
	public void setWeek_hits(int weekHits) {
		week_hits = weekHits;
	}
	public void setMonth_hits(int monthHits) {
		month_hits = monthHits;
	}
	public void setLasthit_dtime(String lasthitDtime) {
		lasthit_dtime = lasthitDtime;
	}
}
