package com.yinhai.model;

import java.util.Map;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-08-31 13:46
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class HotInfoBean {

    private int newsid;
    private String title;
    private String newstitle;
    private String subtitle;
    private String top_title;
    private String author;
    private String pubname;
    private String source;
    private int tvisitnum;
    private int look;
    private String input_dtime;
    private String pubdate;
    private String newsurl;
    private String newspic;
    private String content;

    public HotInfoBean(Map m) {
        if (m.get("info_id") != null) {
            this.newsid = Integer.parseInt(m.get("info_id").toString());
        }
        if (m.get("title") != null) {
            this.title = m.get("title").toString();
            this.newstitle = m.get("title").toString();
        }
        if (m.get("subtitle") != null) {
            this.subtitle = m.get("subtitle").toString();
        }
        if (m.get("top_title") != null) {
            this.top_title = m.get("top_title").toString();
        }
        if (m.get("author") != null) {
            this.author = m.get("author").toString();
        }
        if (m.get("editor") != null) {
            this.pubname = m.get("editor").toString();
        }
        if (m.get("source") != null) {
            this.source = m.get("source").toString();
        }
        if (m.get("hits") != null) {
            this.look = Integer.parseInt(m.get("hits").toString());
            this.tvisitnum = Integer.parseInt(m.get("hits").toString());
        }
        if (m.get("input_dtime") != null) {
            this.input_dtime = m.get("input_dtime").toString();
        }
        if (m.get("thumb_url") != null) {
            this.newspic = m.get("thumb_url").toString();
        }
        if (m.get("content_url") != null) {
            this.newsurl = m.get("content_url").toString();
        }
        if (m.get("released_dtime") != null) {
            this.pubdate = m.get("released_dtime").toString();
        }
        if (m.get("info_content") != null) {
            this.content = m.get("info_content").toString();
        }
    }

    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTop_title() {
        return top_title;
    }

    public void setTop_title(String top_title) {
        this.top_title = top_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPubname() {
        return pubname;
    }

    public void setPubname(String pubname) {
        this.pubname = pubname;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getTvisitnum() {
        return tvisitnum;
    }

    public void setTvisitnum(int tvisitnum) {
        this.tvisitnum = tvisitnum;
    }

    public int getLook() {
        return look;
    }

    public void setLook(int look) {
        this.look = look;
    }

    public String getInput_dtime() {
        return input_dtime;
    }

    public void setInput_dtime(String input_dtime) {
        this.input_dtime = input_dtime;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getNewsurl() {
        return newsurl;
    }

    public void setNewsurl(String newsurl) {
        this.newsurl = newsurl;
    }

    public String getNewspic() {
        return newspic;
    }

    public void setNewspic(String newspic) {
        this.newspic = newspic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
