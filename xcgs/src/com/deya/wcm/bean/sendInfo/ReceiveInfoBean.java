package com.deya.wcm.bean.sendInfo;

public class ReceiveInfoBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2727928635675062713L;
	private int id = 0;
	private int cat_id = 0;
	private String cat_cname = "";
	private int model_id = 0;
	private String top_title = "";
	private String pre_title = "";
	private String title = "";
	private String subtitle = "";
	private String title_color = "";
	private String thumb_url = "";
	private String description = "";
	private String author = "";
	private String editor = "";
	private String source = "";
	private String info_keywords = "";
	private String info_description = "";
	private String tags = "";
	private int input_user = 0;
	private String input_dtime = "";
	private String app_id = "";
	private String site_id = "";
	private int page_count = 0;
	private int is_pic = 0;
	private int is_am_tupage = 0;
	private int tupage_num = 0;
	private String content = "";
	private String s_info_id = "";
	private String s_site_id = "";
	private String s_site_domain = "";
	private String s_site_name = "";
	private int s_user_id = 0;
	private String s_user_name = "";
	private String s_send_dtime = "";
	private String s_content_url = "";
	private int send_record_id = 0;
	private int is_record = 0;
	private int adopt_status = 0;
	private String adopt_dtime = "";
	private int adopt_user = 0;
	private int is_delete = 0;
	private String adopt_desc = "";
	private int score = 0;
	
	
	public String getCat_cname() {
		return cat_cname;
	}
	public void setCat_cname(String catCname) {
		cat_cname = catCname;
	}
	public int getSend_record_id() {
		return send_record_id;
	}
	public void setSend_record_id(int sendRecordId) {
		send_record_id = sendRecordId;
	}
	public String getS_content_url() {
		return s_content_url;
	}
	public void setS_content_url(String sContentUrl) {
		s_content_url = sContentUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int catId) {
		cat_id = catId;
	}
	public int getModel_id() {
		return model_id;
	}
	public void setModel_id(int modelId) {
		model_id = modelId;
	}
	public String getTop_title() {
		return top_title;
	}
	public void setTop_title(String topTitle) {
		top_title = topTitle;
	}
	public String getPre_title() {
		return pre_title;
	}
	public void setPre_title(String preTitle) {
		pre_title = preTitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getTitle_color() {
		return title_color;
	}
	public void setTitle_color(String titleColor) {
		title_color = titleColor;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public void setThumb_url(String thumbUrl) {
		thumb_url = thumbUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getInfo_keywords() {
		return info_keywords;
	}
	public void setInfo_keywords(String infoKeywords) {
		info_keywords = infoKeywords;
	}
	public String getInfo_description() {
		return info_description;
	}
	public void setInfo_description(String infoDescription) {
		info_description = infoDescription;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getInput_user() {
		return input_user;
	}
	public void setInput_user(int inputUser) {
		input_user = inputUser;
	}
	public String getInput_dtime() {
		return input_dtime;
	}
	public void setInput_dtime(String inputDtime) {
		input_dtime = inputDtime;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String appId) {
		app_id = appId;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String siteId) {
		site_id = siteId;
	}
	public int getPage_count() {
		return page_count;
	}
	public void setPage_count(int pageCount) {
		page_count = pageCount;
	}
	public int getIs_pic() {
		return is_pic;
	}
	public void setIs_pic(int isPic) {
		is_pic = isPic;
	}
	public int getIs_am_tupage() {
		return is_am_tupage;
	}
	public void setIs_am_tupage(int isAmTupage) {
		is_am_tupage = isAmTupage;
	}
	public int getTupage_num() {
		return tupage_num;
	}
	public void setTupage_num(int tupageNum) {
		tupage_num = tupageNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getS_info_id() {
		return s_info_id;
	}
	public void setS_info_id(String sInfoId) {
		s_info_id = sInfoId;
	}
	public String getS_site_id() {
		return s_site_id;
	}
	public void setS_site_id(String sSiteId) {
		s_site_id = sSiteId;
	}
	public String getS_site_domain() {
		return s_site_domain;
	}
	public void setS_site_domain(String sSiteDomain) {
		s_site_domain = sSiteDomain;
	}
	public String getS_site_name() {
		return s_site_name;
	}
	public void setS_site_name(String sSiteName) {
		s_site_name = sSiteName;
	}
	public int getS_user_id() {
		return s_user_id;
	}
	public void setS_user_id(int sUserId) {
		s_user_id = sUserId;
	}
	public String getS_user_name() {
		return s_user_name;
	}
	public void setS_user_name(String sUserName) {
		s_user_name = sUserName;
	}
	public String getS_send_dtime() {
		return s_send_dtime;
	}
	public void setS_send_dtime(String sSendDtime) {
		s_send_dtime = sSendDtime;
	}
	public int getIs_record() {
		return is_record;
	}
	public void setIs_record(int isRecord) {
		is_record = isRecord;
	}
	public int getAdopt_status() {
		return adopt_status;
	}
	public void setAdopt_status(int adoptStatus) {
		adopt_status = adoptStatus;
	}
	public String getAdopt_dtime() {
		return adopt_dtime;
	}
	public void setAdopt_dtime(String adoptDtime) {
		adopt_dtime = adoptDtime;
	}
	public int getAdopt_user() {
		return adopt_user;
	}
	public void setAdopt_user(int adoptUser) {
		adopt_user = adoptUser;
	}
	public int getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(int isDelete) {
		is_delete = isDelete;
	}
	public String getAdopt_desc() {
		return adopt_desc;
	}
	public void setAdopt_desc(String adoptDesc) {
		adopt_desc = adoptDesc;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
