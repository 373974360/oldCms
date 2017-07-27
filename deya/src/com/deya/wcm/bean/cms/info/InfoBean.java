package com.deya.wcm.bean.cms.info;

import java.io.Serializable;

public class InfoBean
		implements Serializable, Cloneable
{
	private static final long serialVersionUID = 7656316283038335035L;
	protected int id = 0;
	protected int info_id = 0;
	protected int cat_id = 0;
	protected String cat_cname = "";
	protected int model_id = 0;
	protected int from_id = 0;
	protected String top_title = "";
	protected String title = "";
	protected String subtitle = "";
	protected String title_color = "";
	protected String thumb_url = "";
	protected String description = "";
	protected String author = "";
	protected String editor = "";
	protected String source = "";
	protected String info_keywords = "";
	protected String info_description = "";
	protected String tags = "";
	protected String content_url = "";
	protected int wf_id = 0;
	protected int step_id = 0;
	protected int info_status = 0;
	protected int final_status = 0;
	protected int weight = 0;
	protected int hits = 0;
	protected int day_hits = 0;
	protected int week_hits = 0;
	protected int month_hits = 0;
	protected String lasthit_dtime = "";
	protected int is_allow_comment = 0;
	protected int comment_num = 0;
	protected String released_dtime = "";
	protected int input_user = 0;
	protected String input_dtime = "";
	protected int modify_user = 0;
	protected String modify_dtime = "";
	protected String opt_dtime = "";
	protected int is_auto_up = 0;
	protected String up_dtime = "";
	protected int is_auto_down = 0;
	protected String down_dtime = "";
	protected int is_host = 0;
	protected int title_hashkey = 0;
	protected String app_id = "";
	protected String site_id = "";
	protected int i_ver = 0;
	protected int page_count = 0;
	protected int is_pic = 0;
	protected String pre_title = "";
	protected String auto_desc = "";
	protected int is_am_tupage = 0;
	protected int tupage_num = 1000;
	protected int istop = 0;
	private String input_user_name = "";
	private String modify_user_name = "";
	protected String info_level = "";
	protected String isIpLimit = "";
	protected String publish_source = "";
	protected int wxhits = 0;
	protected int wxday_hits = 0;
	protected int wxweek_hits = 0;
	protected int wxmonth_hits = 0;
	protected int apphits = 0;
	protected int appday_hits = 0;
	protected int appweek_hits = 0;
	protected int appmonth_hits = 0;
	protected String c_xjbh = "";
	protected String c_hfzt = "";
	protected String c_lxsj = "";
	protected String c_xjnr = "";
	protected String c_hfnr = "";

	public String getC_xjbh() {
		return c_xjbh;
	}

	public void setC_xjbh(String c_xjbh) {
		this.c_xjbh = c_xjbh;
	}

	public String getC_hfzt() {
		return c_hfzt;
	}

	public void setC_hfzt(String c_hfzt) {
		this.c_hfzt = c_hfzt;
	}

	public String getC_lxsj() {
		return c_lxsj;
	}

	public void setC_lxsj(String c_lxsj) {
		this.c_lxsj = c_lxsj;
	}

	public String getC_xjnr() {
		return c_xjnr;
	}

	public void setC_xjnr(String c_xjnr) {
		this.c_xjnr = c_xjnr;
	}

	public String getC_hfnr() {
		return c_hfnr;
	}

	public void setC_hfnr(String c_hfnr) {
		this.c_hfnr = c_hfnr;
	}

	public int getIs_am_tupage()
	{
		return this.is_am_tupage;
	}

	public void setIs_am_tupage(int isAmTupage)
	{
		this.is_am_tupage = isAmTupage;
	}

	public int getTupage_num()
	{
		return this.tupage_num;
	}

	public void setTupage_num(int tupageNum)
	{
		this.tupage_num = tupageNum;
	}

	public String getAuto_desc()
	{
		return this.auto_desc;
	}

	public void setAuto_desc(String autoDesc)
	{
		this.auto_desc = autoDesc;
	}

	public String getPre_title()
	{
		return this.pre_title;
	}

	public void setPre_title(String preTitle)
	{
		this.pre_title = preTitle;
	}

	public String getEditor()
	{
		return this.editor;
	}

	public void setEditor(String editor)
	{
		this.editor = editor;
	}

	public String getTop_title()
	{
		return this.top_title;
	}

	public void setTop_title(String topTitle)
	{
		this.top_title = topTitle;
	}

	public int getIs_pic()
	{
		return this.is_pic;
	}

	public void setIs_pic(int isPic)
	{
		this.is_pic = isPic;
	}

	public int getPage_count()
	{
		return this.page_count;
	}

	public void setPage_count(int pageCount)
	{
		this.page_count = pageCount;
	}

	public InfoBean clone()
	{
		InfoBean o = null;
		try
		{
			o = (InfoBean)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return o;
	}

	public String getCat_cname()
	{
		return this.cat_cname;
	}

	public void setCat_cname(String catCname)
	{
		this.cat_cname = catCname;
	}

	public int getId()
	{
		return this.id;
	}

	public int getInfo_id()
	{
		return this.info_id;
	}

	public int getCat_id()
	{
		return this.cat_id;
	}

	public int getModel_id()
	{
		return this.model_id;
	}

	public int getFrom_id()
	{
		return this.from_id;
	}

	public String getTitle()
	{
		return this.title;
	}

	public String getSubtitle()
	{
		return this.subtitle;
	}

	public String getTitle_color()
	{
		return this.title_color;
	}

	public String getThumb_url()
	{
		return this.thumb_url;
	}

	public String getDescription()
	{
		return this.description;
	}

	public String getAuthor()
	{
		return this.author;
	}

	public String getSource()
	{
		return this.source;
	}

	public String getInfo_keywords()
	{
		return this.info_keywords;
	}

	public String getInfo_description()
	{
		return this.info_description;
	}

	public String getTags()
	{
		return this.tags;
	}

	public String getContent_url()
	{
		return this.content_url;
	}

	public int getWf_id()
	{
		return this.wf_id;
	}

	public int getStep_id()
	{
		return this.step_id;
	}

	public int getInfo_status()
	{
		return this.info_status;
	}

	public int getFinal_status()
	{
		return this.final_status;
	}

	public int getWeight()
	{
		return this.weight;
	}

	public int getHits()
	{
		return this.hits;
	}

	public int getDay_hits()
	{
		return this.day_hits;
	}

	public int getWeek_hits()
	{
		return this.week_hits;
	}

	public int getMonth_hits()
	{
		return this.month_hits;
	}

	public String getLasthit_dtime()
	{
		return this.lasthit_dtime;
	}

	public int getIs_allow_comment()
	{
		return this.is_allow_comment;
	}

	public int getComment_num()
	{
		return this.comment_num;
	}

	public String getReleased_dtime()
	{
		return this.released_dtime;
	}

	public int getInput_user()
	{
		return this.input_user;
	}

	public String getInput_dtime()
	{
		return this.input_dtime;
	}

	public int getModify_user()
	{
		return this.modify_user;
	}

	public String getModify_dtime()
	{
		return this.modify_dtime;
	}

	public String getOpt_dtime()
	{
		return this.opt_dtime;
	}

	public int getIs_auto_up()
	{
		return this.is_auto_up;
	}

	public String getUp_dtime()
	{
		return this.up_dtime;
	}

	public int getIs_auto_down()
	{
		return this.is_auto_down;
	}

	public String getDown_dtime()
	{
		return this.down_dtime;
	}

	public int getIs_host()
	{
		return this.is_host;
	}

	public int getTitle_hashkey()
	{
		return this.title_hashkey;
	}

	public String getApp_id()
	{
		return this.app_id;
	}

	public String getSite_id()
	{
		return this.site_id;
	}

	public int getI_ver()
	{
		return this.i_ver;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setInfo_id(int info_id)
	{
		this.info_id = info_id;
	}

	public void setCat_id(int cat_id)
	{
		this.cat_id = cat_id;
	}

	public void setModel_id(int model_id)
	{
		this.model_id = model_id;
	}

	public void setFrom_id(int from_id)
	{
		this.from_id = from_id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setSubtitle(String subtitle)
	{
		this.subtitle = subtitle;
	}

	public void setTitle_color(String title_color)
	{
		this.title_color = title_color;
	}

	public void setThumb_url(String thumb_url)
	{
		this.thumb_url = thumb_url;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public void setInfo_keywords(String info_keywords)
	{
		this.info_keywords = info_keywords;
	}

	public void setInfo_description(String info_description)
	{
		this.info_description = info_description;
	}

	public void setTags(String tags)
	{
		this.tags = tags;
	}

	public void setContent_url(String content_url)
	{
		this.content_url = content_url;
	}

	public void setWf_id(int wf_id)
	{
		this.wf_id = wf_id;
	}

	public void setStep_id(int step_id)
	{
		this.step_id = step_id;
	}

	public void setInfo_status(int info_status)
	{
		this.info_status = info_status;
	}

	public void setFinal_status(int final_status)
	{
		this.final_status = final_status;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	public void setHits(int hits)
	{
		this.hits = hits;
	}

	public void setDay_hits(int day_hits)
	{
		this.day_hits = day_hits;
	}

	public void setWeek_hits(int week_hits)
	{
		this.week_hits = week_hits;
	}

	public void setMonth_hits(int month_hits)
	{
		this.month_hits = month_hits;
	}

	public void setLasthit_dtime(String lasthit_dtime)
	{
		this.lasthit_dtime = lasthit_dtime;
	}

	public void setIs_allow_comment(int is_allow_comment)
	{
		this.is_allow_comment = is_allow_comment;
	}

	public void setComment_num(int comment_num)
	{
		this.comment_num = comment_num;
	}

	public void setReleased_dtime(String released_dtime)
	{
		this.released_dtime = released_dtime;
	}

	public void setInput_user(int input_user)
	{
		this.input_user = input_user;
	}

	public void setInput_dtime(String input_dtime)
	{
		this.input_dtime = input_dtime;
	}

	public void setModify_user(int modify_user)
	{
		this.modify_user = modify_user;
	}

	public void setModify_dtime(String modify_dtime)
	{
		this.modify_dtime = modify_dtime;
	}

	public void setOpt_dtime(String opt_dtime)
	{
		this.opt_dtime = opt_dtime;
	}

	public void setIs_auto_up(int is_auto_up)
	{
		this.is_auto_up = is_auto_up;
	}

	public void setUp_dtime(String up_dtime)
	{
		this.up_dtime = up_dtime;
	}

	public void setIs_auto_down(int is_auto_down)
	{
		this.is_auto_down = is_auto_down;
	}

	public void setDown_dtime(String down_dtime)
	{
		this.down_dtime = down_dtime;
	}

	public void setIs_host(int is_host)
	{
		this.is_host = is_host;
	}

	public void setTitle_hashkey(int title_hashkey)
	{
		this.title_hashkey = title_hashkey;
	}

	public void setApp_id(String app_id)
	{
		this.app_id = app_id;
	}

	public void setSite_id(String site_id)
	{
		this.site_id = site_id;
	}

	public void setI_ver(int i_ver)
	{
		this.i_ver = i_ver;
	}

	public String getInput_user_name()
	{
		return this.input_user_name;
	}

	public void setInput_user_name(String input_user_name)
	{
		this.input_user_name = input_user_name;
	}

	public String getModify_user_name()
	{
		return this.modify_user_name;
	}

	public void setModify_user_name(String modify_user_name)
	{
		this.modify_user_name = modify_user_name;
	}

	public int getIstop()
	{
		return this.istop;
	}

	public void setIstop(int istop)
	{
		this.istop = istop;
	}

	public String getInfo_level()
	{
		return this.info_level;
	}

	public void setInfo_level(String info_level)
	{
		this.info_level = info_level;
	}

	public String getIsIpLimit()
	{
		return this.isIpLimit;
	}

	public void setIsIpLimit(String isIpLimit)
	{
		this.isIpLimit = isIpLimit;
	}

	public String getPublish_source()
	{
		return this.publish_source;
	}

	public void setPublish_source(String publish_source)
	{
		this.publish_source = publish_source;
	}

	public int getWxhits()
	{
		return this.wxhits;
	}

	public void setWxhits(int wxhits)
	{
		this.wxhits = wxhits;
	}

	public int getApphits()
	{
		return this.apphits;
	}

	public void setApphits(int apphits)
	{
		this.apphits = apphits;
	}

	public int getWxday_hits()
	{
		return this.wxday_hits;
	}

	public void setWxday_hits(int wxday_hits)
	{
		this.wxday_hits = wxday_hits;
	}

	public int getWxweek_hits()
	{
		return this.wxweek_hits;
	}

	public void setWxweek_hits(int wxweek_hits)
	{
		this.wxweek_hits = wxweek_hits;
	}

	public int getWxmonth_hits()
	{
		return this.wxmonth_hits;
	}

	public void setWxmonth_hits(int wxmonth_hits)
	{
		this.wxmonth_hits = wxmonth_hits;
	}

	public int getAppday_hits()
	{
		return this.appday_hits;
	}

	public void setAppday_hits(int appday_hits)
	{
		this.appday_hits = appday_hits;
	}

	public int getAppweek_hits()
	{
		return this.appweek_hits;
	}

	public void setAppweek_hits(int appweek_hits)
	{
		this.appweek_hits = appweek_hits;
	}

	public int getAppmonth_hits()
	{
		return this.appmonth_hits;
	}

	public void setAppmonth_hits(int appmonth_hits)
	{
		this.appmonth_hits = appmonth_hits;
	}
}
