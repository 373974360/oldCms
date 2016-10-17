package com.deya.wcm.dataCollection.bean;

import java.io.Serializable;

public class CollRuleBean
        implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id = 0;
    private int rule_id = 0;
    private String title = "";
    private String listUrl_start = "";
    private String listUrl_end = "";
    private String infotitle_start = "";
    private String infotitle_end = "";
    private String description_start = "";
    private String description_end = "";
    private String content_start = "";
    private String content_end = "";
    private String source_start = "";
    private String source_end = "";
    private String addTime_start = "";
    private String addTime_end = "";
    private String keywords_start = "";
    private String keywords_end = "";
    private String hits_start = "";
    private String hits_end = "";
    private String author_start = "";
    private String author_end = "";
    private String site_id = "";
    private String cate_ids = "";
    private int stop_time = 0;
    private int pic_isGet = 0;
    private String coll_interval = "";

    private String timeFormatType = "yyyy-MM-dd HH:MM:ss";
    private String pageEncoding = "";
    private String coll_url = "";
    private String contentUrl_start = "";
    private String contentUrl_end = "";
    private String cat_id = "";
    private String cat_name = "";
    private String model_id = "";
    private String rcat_id = "";

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRule_id() {
        return this.rule_id;
    }
    public void setRule_id(int ruleId) {
        this.rule_id = ruleId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getInfotitle_start() {
        return this.infotitle_start;
    }
    public void setInfotitle_start(String infotitleStart) {
        this.infotitle_start = infotitleStart;
    }
    public String getInfotitle_end() {
        return this.infotitle_end;
    }
    public void setInfotitle_end(String infotitleEnd) {
        this.infotitle_end = infotitleEnd;
    }
    public String getDescription_start() {
        return this.description_start;
    }
    public void setDescription_start(String descriptionStart) {
        this.description_start = descriptionStart;
    }
    public String getDescription_end() {
        return this.description_end;
    }
    public void setDescription_end(String descriptionEnd) {
        this.description_end = descriptionEnd;
    }
    public String getContent_start() {
        return this.content_start;
    }
    public void setContent_start(String contentStart) {
        this.content_start = contentStart;
    }
    public String getContent_end() {
        return this.content_end;
    }
    public void setContent_end(String contentEnd) {
        this.content_end = contentEnd;
    }
    public String getSource_start() {
        return this.source_start;
    }
    public void setSource_start(String sourceStart) {
        this.source_start = sourceStart;
    }
    public String getSource_end() {
        return this.source_end;
    }
    public void setSource_end(String sourceEnd) {
        this.source_end = sourceEnd;
    }
    public String getAddTime_start() {
        return this.addTime_start;
    }
    public void setAddTime_start(String addTimeStart) {
        this.addTime_start = addTimeStart;
    }
    public String getAddTime_end() {
        return this.addTime_end;
    }
    public void setAddTime_end(String addTimeEnd) {
        this.addTime_end = addTimeEnd;
    }
    public String getKeywords_start() {
        return this.keywords_start;
    }
    public void setKeywords_start(String keywordsStart) {
        this.keywords_start = keywordsStart;
    }
    public String getKeywords_end() {
        return this.keywords_end;
    }
    public void setKeywords_end(String keywordsEnd) {
        this.keywords_end = keywordsEnd;
    }
    public String getHits_start() {
        return this.hits_start;
    }
    public void setHits_start(String hitsStart) {
        this.hits_start = hitsStart;
    }
    public String getHits_end() {
        return this.hits_end;
    }
    public void setHits_end(String hitsEnd) {
        this.hits_end = hitsEnd;
    }
    public String getAuthor_start() {
        return this.author_start;
    }
    public void setAuthor_start(String authorStart) {
        this.author_start = authorStart;
    }
    public String getAuthor_end() {
        return this.author_end;
    }
    public void setAuthor_end(String authorEnd) {
        this.author_end = authorEnd;
    }
    public String getSite_id() {
        return this.site_id;
    }
    public void setSite_id(String siteId) {
        this.site_id = siteId;
    }
    public String getCate_ids() {
        return this.cate_ids;
    }
    public void setCate_ids(String cateIds) {
        this.cate_ids = cateIds;
    }
    public int getStop_time() {
        return this.stop_time;
    }
    public void setStop_time(int stopTime) {
        this.stop_time = stopTime;
    }
    public int getPic_isGet() {
        return this.pic_isGet;
    }
    public void setPic_isGet(int picIsGet) {
        this.pic_isGet = picIsGet;
    }
    public String getTimeFormatType() {
        return this.timeFormatType;
    }
    public void setTimeFormatType(String timeFormatType) {
        this.timeFormatType = timeFormatType;
    }
    public String getPageEncoding() {
        return this.pageEncoding;
    }
    public void setPageEncoding(String pageEncoding) {
        this.pageEncoding = pageEncoding;
    }
    public String getColl_url() {
        return this.coll_url;
    }
    public void setColl_url(String collUrl) {
        this.coll_url = collUrl;
    }
    public String getContentUrl_start() {
        return this.contentUrl_start;
    }
    public void setContentUrl_start(String contentUrlStart) {
        this.contentUrl_start = contentUrlStart;
    }
    public String getContentUrl_end() {
        return this.contentUrl_end;
    }
    public void setContentUrl_end(String contentUrlEnd) {
        this.contentUrl_end = contentUrlEnd;
    }
    public String getListUrl_start() {
        return this.listUrl_start;
    }
    public void setListUrl_start(String listUrlStart) {
        this.listUrl_start = listUrlStart;
    }
    public String getListUrl_end() {
        return this.listUrl_end;
    }
    public void setListUrl_end(String listUrlEnd) {
        this.listUrl_end = listUrlEnd;
    }
    public String getCat_id() {
        return this.cat_id;
    }
    public void setCat_id(String catId) {
        this.cat_id = catId;
    }
    public String getCat_name() {
        return this.cat_name;
    }
    public void setCat_name(String catName) {
        this.cat_name = catName;
    }

    public String getColl_interval() {
        return this.coll_interval;
    }
    public void setColl_interval(String collInterval) {
        this.coll_interval = collInterval;
    }
    public String getModel_id() {
        return model_id;
    }
    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }
    public String getRcat_id() {
        return rcat_id;
    }
    public void setRcat_id(String rcat_id) {
        this.rcat_id = rcat_id;
    }
}
