package com.deya.wcm.bean.cms.info;


/**
 * @author 符江波
 * @version 1.0
 * @date 2011-6-14 下午01:24:34
 */
public class InfoBean  implements java.io.Serializable,Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7656316283038335035L;
	protected int id = 0;// bigint 20
	protected int info_id = 0;// bigint 20
	protected int cat_id = 0;// bigint 20
	protected String cat_cname = "";
	protected int model_id = 0;// bigint 20
	protected int from_id = 0;// bigint 20
	protected String top_title = "";// varchar 250
	protected String title = "";// varchar 250
	protected String subtitle = "";// varchar 500
	protected String title_color = "";// varchar 250
	protected String thumb_url = "";// varchar 250
	protected String description = "";// varchar 1000	
	protected String author = "";// varchar 250
	protected String editor = "";// varchar 250
	protected String source = "";// varchar 250
	protected String info_keywords = "";// varchar 250
	protected String info_description = "";// varchar 250
	protected String tags = "";// varchar 250
	protected String content_url = "";// varchar 250
	protected int wf_id = 0;// bigint 20
	protected int step_id = 0;// tinyint 4
	protected int info_status = 0;// tinyint 4
	protected int final_status = 0;// tinyint 4
	protected int weight = 0;// tinyint 4
	protected int hits = 0;// bigint 20
	protected int day_hits = 0;// bigint 20
	protected int week_hits = 0;// bigint 20
	protected int month_hits = 0;// bigint 20
	protected String lasthit_dtime = "";// char 20
	protected int is_allow_comment = 0;// tinyint 4
	protected int comment_num = 0;// bigint 20
	protected String released_dtime = "";// char 20
	protected int input_user = 0;// bigint 20
	protected String input_dtime = "";// char 20
	protected int modify_user = 0;// bigint 20
	protected String modify_dtime = "";// char 20
	protected String opt_dtime = "";// char 20
	protected int is_auto_up = 0;// tinyint 4
	protected String up_dtime = "";// char 20
	protected int is_auto_down = 0;// tinyint 4
	protected String down_dtime = "";// char 20
	protected int is_host = 0;// tinyint 4
	protected int title_hashkey = 0;// bigint 20
	protected String app_id = "";// varchar 250
	protected String site_id = "";// varchar 250
	protected int i_ver = 0;// tinyint 4
	protected int page_count = 0;//内容页总数
	protected int is_pic=0;//内容页总数
	protected String pre_title = "";// varchar 250
	protected String auto_desc = "";// varchar 1000
	protected int is_am_tupage = 0;// 是否自动翻页
	protected int tupage_num = 1000;// 翻页限定字数
	protected int istop = 0;	//是否置顶
	
	private String input_user_name = "";
	private String modify_user_name = "";
    protected String info_level = "";//稿件评级
    protected String isIpLimit = "";//是否IP限制
	
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

	public String getAuto_desc() {
		return auto_desc;
	}

	public void setAuto_desc(String autoDesc) {
		auto_desc = autoDesc;
	}

	public String getPre_title() {
		return pre_title;
	}

	public void setPre_title(String preTitle) {
		pre_title = preTitle;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getTop_title() {
		return top_title;
	}

	public void setTop_title(String topTitle) {
		top_title = topTitle;
	}

	public int getIs_pic() {
		return is_pic;
	}

	public void setIs_pic(int isPic) {
		is_pic = isPic;
	}

	public int getPage_count() {
		return page_count;
	}

	public void setPage_count(int pageCount) {
		page_count = pageCount;
	}

	public InfoBean clone(){
		InfoBean o = null;
        try{
            o = (InfoBean)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return o;
    }
	
	public String getCat_cname() {
		return cat_cname;
	}

	public void setCat_cname(String catCname) {
		cat_cname = catCname;
	}
	
	public int getId() {
		return id;
	}
	public int getInfo_id() {
		return info_id;
	}
	public int getCat_id() {
		return cat_id;
	}
	public int getModel_id() {
		return model_id;
	}
	public int getFrom_id() {
		return from_id;
	}
	public String getTitle() {
		return title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public String getTitle_color() {
		return title_color;
	}
	public String getThumb_url() {
		return thumb_url;
	}
	public String getDescription() {
		return description;
	}
	public String getAuthor() {
		return author;
	}
	public String getSource() {
		return source;
	}
	public String getInfo_keywords() {
		return info_keywords;
	}
	public String getInfo_description() {
		return info_description;
	}
	public String getTags() {
		return tags;
	}
	public String getContent_url() {
		return content_url;
	}
	public int getWf_id() {
		return wf_id;
	}
	public int getStep_id() {
		return step_id;
	}
	public int getInfo_status() {
		return info_status;
	}
	public int getFinal_status() {
		return final_status;
	}
	public int getWeight() {
		return weight;
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
	public int getIs_allow_comment() {
		return is_allow_comment;
	}
	public int getComment_num() {
		return comment_num;
	}
	public String getReleased_dtime() {
		return released_dtime;
	}
	public int getInput_user() {
		return input_user;
	}
	public String getInput_dtime() {
		return input_dtime;
	}
	public int getModify_user() {
		return modify_user;
	}
	public String getModify_dtime() {
		return modify_dtime;
	}
	public String getOpt_dtime() {
		return opt_dtime;
	}
	public int getIs_auto_up() {
		return is_auto_up;
	}
	public String getUp_dtime() {
		return up_dtime;
	}
	public int getIs_auto_down() {
		return is_auto_down;
	}
	public String getDown_dtime() {
		return down_dtime;
	}
	public int getIs_host() {
		return is_host;
	}
	public int getTitle_hashkey() {
		return title_hashkey;
	}
	public String getApp_id() {
		return app_id;
	}
	public String getSite_id() {
		return site_id;
	}
	public int getI_ver() {
		return i_ver;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}
	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}
	public void setModel_id(int model_id) {
		this.model_id = model_id;
	}
	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public void setTitle_color(String title_color) {
		this.title_color = title_color;
	}
	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public void setInfo_keywords(String info_keywords) {
		this.info_keywords = info_keywords;
	}
	public void setInfo_description(String info_description) {
		this.info_description = info_description;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public void setContent_url(String content_url) {
		this.content_url = content_url;
	}
	public void setWf_id(int wf_id) {
		this.wf_id = wf_id;
	}
	public void setStep_id(int step_id) {
		this.step_id = step_id;
	}
	public void setInfo_status(int info_status) {
		this.info_status = info_status;
	}
	public void setFinal_status(int final_status) {
		this.final_status = final_status;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public void setDay_hits(int day_hits) {
		this.day_hits = day_hits;
	}
	public void setWeek_hits(int week_hits) {
		this.week_hits = week_hits;
	}
	public void setMonth_hits(int month_hits) {
		this.month_hits = month_hits;
	}
	public void setLasthit_dtime(String lasthit_dtime) {
		this.lasthit_dtime = lasthit_dtime;
	}
	public void setIs_allow_comment(int is_allow_comment) {
		this.is_allow_comment = is_allow_comment;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public void setReleased_dtime(String released_dtime) {
		this.released_dtime = released_dtime;
	}
	public void setInput_user(int input_user) {
		this.input_user = input_user;
	}
	public void setInput_dtime(String input_dtime) {
		this.input_dtime = input_dtime;
	}
	public void setModify_user(int modify_user) {
		this.modify_user = modify_user;
	}
	public void setModify_dtime(String modify_dtime) {
		this.modify_dtime = modify_dtime;
	}
	public void setOpt_dtime(String opt_dtime) {
		this.opt_dtime = opt_dtime;
	}
	public void setIs_auto_up(int is_auto_up) {
		this.is_auto_up = is_auto_up;
	}
	public void setUp_dtime(String up_dtime) {
		this.up_dtime = up_dtime;
	}
	public void setIs_auto_down(int is_auto_down) {
		this.is_auto_down = is_auto_down;
	}
	public void setDown_dtime(String down_dtime) {
		this.down_dtime = down_dtime;
	}
	public void setIs_host(int is_host) {
		this.is_host = is_host;
	}
	public void setTitle_hashkey(int title_hashkey) {
		this.title_hashkey = title_hashkey;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public void setI_ver(int i_ver) {
		this.i_ver = i_ver;
	}

	public String getInput_user_name() {
		return input_user_name;
	}

	public void setInput_user_name(String input_user_name) {
		this.input_user_name = input_user_name;
	}

	public String getModify_user_name() {
		return modify_user_name;
	}

	public void setModify_user_name(String modify_user_name) {
		this.modify_user_name = modify_user_name;
	}

	public int getIstop() {
		return istop;
	}

	public void setIstop(int istop) {
		this.istop = istop;
	}

    public String getInfo_level() {
        return info_level;
    }

    public void setInfo_level(String info_level) {
        this.info_level = info_level;
    }

    public String getIsIpLimit() {
        return isIpLimit;
    }

    public void setIsIpLimit(String isIpLimit) {
        this.isIpLimit = isIpLimit;
    }
}
