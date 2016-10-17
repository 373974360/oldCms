package com.deya.wcm.dataCollection.bean;

public class ArticleInfoBean
{
    private static final long serialVersionUID = 1L;
    private int id = 0;
    private int rule_id = 0;
    private String art_title = "";
    private String art_content = "";
    private String art_pubTime = "";
    private String art_source = "";
    private String art_author = "";
    private String art_keyWords = "";
    private int art_hits = 0;
    private String cat_id = "";
    private String model_id = "";
    private int artis_user = 0;
    private String art_picURL = "";
    private String coll_time = "";
    private String url = "";

    private String cat_name = "";
    private String title = "";

    public String getCat_name() {
        return this.cat_name;
    }
    public void setCat_name(String catName) {
        this.cat_name = catName;
    }
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
    public String getArt_title() {
        return this.art_title;
    }
    public void setArt_title(String artTitle) {
        this.art_title = artTitle;
    }
    public String getArt_content() {
        return this.art_content;
    }
    public void setArt_content(String artContent) {
        this.art_content = artContent;
    }

    public String getArt_author() {
        return this.art_author;
    }
    public void setArt_author(String artAuthor) {
        this.art_author = artAuthor;
    }
    public String getArt_keyWords() {
        return this.art_keyWords;
    }
    public void setArt_keyWords(String artKeyWords) {
        this.art_keyWords = artKeyWords;
    }
    public String getCat_id() {
        return this.cat_id;
    }
    public void setCat_id(String catId) {
        this.cat_id = catId;
    }

    public int getArtis_user() {
        return this.artis_user;
    }
    public void setArtis_user(int artisUser) {
        this.artis_user = artisUser;
    }
    public int getArt_hits() {
        return this.art_hits;
    }
    public void setArt_hits(int artHits) {
        this.art_hits = artHits;
    }
    public String getArt_source() {
        return this.art_source;
    }
    public void setArt_source(String artSource) {
        this.art_source = artSource;
    }
    public String getArt_pubTime() {
        return this.art_pubTime;
    }
    public void setArt_pubTime(String artPubTime) {
        this.art_pubTime = artPubTime;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArt_picURL() {
        return this.art_picURL;
    }
    public void setArt_picURL(String artPicURL) {
        this.art_picURL = artPicURL;
    }
    public String getColl_time() {
        return this.coll_time;
    }
    public void setColl_time(String collTime) {
        this.coll_time = collTime;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getModel_id() {
        return model_id;
    }
    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

}
