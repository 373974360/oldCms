package com.deya.wcm.bean.appeal.sq;

public class SQBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7089029015588100211L;
	private int sq_id = 0;//
	private String sq_code = "";//
	private int model_id = 0;//业务ID 省（市、县、厅）长信箱	部门信箱
	private String model_cname = "";
	private int me_id = 0;//会员ID 
	private String sq_realname = "";//真名
	private int sq_sex = 0;//0：女	1：男	
	private String sq_vocation = "";//职业
	private int sq_age = 0;//
	private String sq_address = "";//
	private String sq_email = "";//
	private String sq_tel = "";//固定电话
	private String sq_phone = "";//手机
	private String sq_card_id = "";//身份证ID
	private String sq_dtime = "";//用户提交时间
	private int is_open = 0;//是否公开(投诉人意愿) 0：不公开	1：公开
	private String sq_ip = "";//写信人IP
	private String sq_title = "";//原件标题
	private String sq_title2 = "";//前台显示标题
	private String sq_content = "";//原件内容
	private String sq_content2 = "";//前台显示内容
	private String sq_reply = "";//发布前由发布员处理的回复内容
	private int sq_type = 0;//0：诉求件	1：信访件
	private String query_code = "";//
	private int cat_id = 0;//投诉所属领域（内容分类）
	private int area_id = 0;//投诉所属地区(问题属地)
	private String area_name = "";	
	private int pur_id = 0;//投诉  建议 咨询
	private String pur_name = "";	
	private String accept_dtime = "";//受理时间
	private String over_dtime  = "";//办结时间
	private int sq_flag = 0;//信件标识  0：正常信件（默认值）	-1：无效信件	 1：重复信件	2：不予受理信件
	private int initial_sq_id = 0;//最初的信件ID 如果信件重复,在此记录最初的信件ID
	private int sq_status = 0;//流程处理状态 0：待处理	1：处理中	2：待审核	3：已办结
	private String sq_status_name = "";	
	private int step_id = 0;//流程状态码 1-99：审核过程中	100：终审通过
	private int publish_status = 0;//发布(公开)标识（政府意愿） 0：未发布（不公开）	1：已发布（公开）
	private int supervise_flag = 0;//督办标识 0：未督办 	1：已督办
	private int is_back = 0;//回退标识 0：正常	1：退回
	private int time_limit = 0;//处理天数限制 受理后，计时开始，默认值从业务模型中取
	private int limit_flag = 0;//延时申请标识 0：正常	1：延时	2：申请延时
	private int alarm_flag = 0;//超期未办警示标识 0：正常	1：预警	2：黄牌	3：红牌
	private int timeout_flag = 0;//超期标识 0：正常	1：超期
	private int prove_type = 1;//信件原始办理类型 1：自办	2：转办	3：交办	4：呈办
	private int people_num = 1;//来信人数 1：1-4人	2：5-99人 	3：100-999人	4：1000人以上
	private int do_dept = 0;//处理部门  如业务设置为不分拣，则提交部门=处理部门
	private String do_dept_name = "";	
	private String submit_name = "";//提交部门(领导人)名称
	private int publish_user = 0;//发布人
	private String publish_dtime = "";//发布时间
	private int sat_score = 0;//满意度分值	
	private int hits = 0;//总点击数	
	private int day_hits = 0;//日点击数
	private int week_hits = 0;//周点击数
	private int month_hits = 0;//月点击数
	private String lasthit_dtime = "";//最后点击时间
	private int weight = 60;//权重
	private int sq_all_number = 0;//按年流水号
	private int sq_number = 0;//	按年及业务流水号
	private int submit_dept_id = 0;//原始提交的部门ID
	public int getSubmit_dept_id() {
		return submit_dept_id;
	}
	public void setSubmit_dept_id(int submitDeptId) {
		submit_dept_id = submitDeptId;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String areaName) {
		area_name = areaName;
	}
	public int getSq_all_number() {
		return sq_all_number;
	}
	public void setSq_all_number(int sqAllNumber) {
		sq_all_number = sqAllNumber;
	}
	public int getSq_number() {
		return sq_number;
	}
	public void setSq_number(int sqNumber) {
		sq_number = sqNumber;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getDay_hits() {
		return day_hits;
	}
	public void setDay_hits(int dayHits) {
		day_hits = dayHits;
	}
	public int getWeek_hits() {
		return week_hits;
	}
	public void setWeek_hits(int weekHits) {
		week_hits = weekHits;
	}
	public int getMonth_hits() {
		return month_hits;
	}
	public void setMonth_hits(int monthHits) {
		month_hits = monthHits;
	}
	public String getLasthit_dtime() {
		return lasthit_dtime;
	}
	public void setLasthit_dtime(String lasthitDtime) {
		lasthit_dtime = lasthitDtime;
	}
	public String getPur_name() {
		return pur_name;
	}
	public void setPur_name(String purName) {
		pur_name = purName;
	}
	public String getDo_dept_name() {
		return do_dept_name;
	}
	public void setDo_dept_name(String doDeptName) {
		do_dept_name = doDeptName;
	}
	public String getSq_status_name() {
		return sq_status_name;
	}
	public void setSq_status_name(String sqStatusName) {
		sq_status_name = sqStatusName;
	}
	public String getModel_cname() {
		return model_cname;
	}
	public void setModel_cname(String modelCname) {
		model_cname = modelCname;
	}	
	public int getSq_id() {
		return sq_id;
	}
	public void setSq_id(int sqId) {
		sq_id = sqId;
	}
	public String getSq_code() {
		return sq_code;
	}
	public void setSq_code(String sqCode) {
		sq_code = sqCode;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public int getMe_id() {
		return me_id;
	}
	public void setMe_id(int meId) {
		me_id = meId;
	}
	public String getSq_realname() {
		return sq_realname;
	}
	public void setSq_realname(String sqRealname) {
		sq_realname = sqRealname;
	}
	public int getSq_sex() {
		return sq_sex;
	}
	public void setSq_sex(int sqSex) {
		sq_sex = sqSex;
	}
	public String getSq_vocation() {
		return sq_vocation;
	}
	public void setSq_vocation(String sqVocation) {
		sq_vocation = sqVocation;
	}
	public int getSq_age() {
		return sq_age;
	}
	public void setSq_age(int sqAge) {
		sq_age = sqAge;
	}
	public String getSq_address() {
		return sq_address;
	}
	public void setSq_address(String sqAddress) {
		sq_address = sqAddress;
	}
	public String getSq_email() {
		return sq_email;
	}
	public void setSq_email(String sqEmail) {
		sq_email = sqEmail;
	}
	public String getSq_tel() {
		return sq_tel;
	}
	public void setSq_tel(String sqTel) {
		sq_tel = sqTel;
	}
	public String getSq_phone() {
		return sq_phone;
	}
	public void setSq_phone(String sqPhone) {
		sq_phone = sqPhone;
	}
	public String getSq_card_id() {
		return sq_card_id;
	}
	public void setSq_card_id(String sqCardId) {
		sq_card_id = sqCardId;
	}
	public String getSq_dtime() {
		return sq_dtime;
	}
	public void setSq_dtime(String sqDtime) {
		sq_dtime = sqDtime;
	}
	public int getIs_open() {
		return is_open;
	}
	public void setIs_open(int isOpen) {
		is_open = isOpen;
	}
	public String getSq_ip() {
		return sq_ip;
	}
	public void setSq_ip(String sqIp) {
		sq_ip = sqIp;
	}
	public String getSq_title() {
		return sq_title;
	}
	public void setSq_title(String sqTitle) {
		sq_title = sqTitle;
	}
	public String getSq_title2() {
		return sq_title2;
	}
	public void setSq_title2(String sqTitle2) {
		sq_title2 = sqTitle2;
	}
	public String getSq_content() {
		return sq_content;
	}
	public void setSq_content(String sqContent) {
		sq_content = sqContent;
	}
	public String getSq_content2() {
		return sq_content2;
	}
	public void setSq_content2(String sqContent2) {
		sq_content2 = sqContent2;
	}
	public String getSq_reply() {
		return sq_reply;
	}
	public void setSq_reply(String sqReply) {
		sq_reply = sqReply;
	}
	public int getSq_type() {
		return sq_type;
	}
	public void setSq_type(int sqType) {
		sq_type = sqType;
	}
	public String getQuery_code() {
		return query_code;
	}
	public void setQuery_code(String queryCode) {
		query_code = queryCode;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int areaId) {
		area_id = areaId;
	}
	public int getPur_id() {
		return pur_id;
	}
	public void setPur_id(int purId) {
		pur_id = purId;
	}
	public String getAccept_dtime() {
		return accept_dtime;
	}
	public void setAccept_dtime(String acceptDtime) {
		accept_dtime = acceptDtime;
	}
	public String getOver_dtime() {
		return over_dtime;
	}
	public void setOver_dtime(String overDtime) {
		over_dtime = overDtime;
	}
	public int getSq_flag() {
		return sq_flag;
	}
	public void setSq_flag(int sqFlag) {
		sq_flag = sqFlag;
	}
	public int getInitial_sq_id() {
		return initial_sq_id;
	}
	public void setInitial_sq_id(int initialSqId) {
		initial_sq_id = initialSqId;
	}
	public int getSq_status() {
		return sq_status;
	}
	public void setSq_status(int sqStatus) {
		sq_status = sqStatus;
	}
	public int getStep_id() {
		return step_id;
	}
	public void setStep_id(int stepId) {
		step_id = stepId;
	}
	public int getPublish_status() {
		return publish_status;
	}
	public void setPublish_status(int publishStatus) {
		publish_status = publishStatus;
	}
	public int getSupervise_flag() {
		return supervise_flag;
	}
	public void setSupervise_flag(int superviseFlag) {
		supervise_flag = superviseFlag;
	}
	public int getIs_back() {
		return is_back;
	}
	public void setIs_back(int isBack) {
		is_back = isBack;
	}
	public int getTime_limit() {
		return time_limit;
	}
	public void setTime_limit(int timeLimit) {
		time_limit = timeLimit;
	}
	public int getLimit_flag() {
		return limit_flag;
	}
	public void setLimit_flag(int limitFlag) {
		limit_flag = limitFlag;
	}
	public int getAlarm_flag() {
		return alarm_flag;
	}
	public void setAlarm_flag(int alarmFlag) {
		alarm_flag = alarmFlag;
	}
	public int getTimeout_flag() {
		return timeout_flag;
	}
	public void setTimeout_flag(int timeoutFlag) {
		timeout_flag = timeoutFlag;
	}
	public int getProve_type() {
		return prove_type;
	}
	public void setProve_type(int proveType) {
		prove_type = proveType;
	}
	public int getPeople_num() {
		return people_num;
	}
	public void setPeople_num(int peopleNum) {
		people_num = peopleNum;
	}
	public int getDo_dept() {
		return do_dept;
	}
	public void setDo_dept(int doDept) {
		do_dept = doDept;
	}
	public String getSubmit_name() {
		return submit_name;
	}
	public void setSubmit_name(String submitName) {
		submit_name = submitName;
	}
	public int getPublish_user() {
		return publish_user;
	}
	public void setPublish_user(int publishUser) {
		publish_user = publishUser;
	}
	public String getPublish_dtime() {
		return publish_dtime;
	}
	public void setPublish_dtime(String publishDtime) {
		publish_dtime = publishDtime;
	}
	public int getSat_score() {
		return sat_score;
	}
	public void setSat_score(int satScore) {
		sat_score = satScore;
	}
	
}
