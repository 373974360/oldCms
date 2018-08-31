package com.deya.project.dz_szb;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 电子报对象
 * Created by yangyan on 2016/12/6.
 */
public class SzbBean {

    public static ObjectMapper m = new ObjectMapper();

    static {
        // 此配置的作用为当使用此工具将json中的属性还原到bean时，如果有bean中没有的属性，是否报错
        m.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private int id;
    private String title;
    private String jsonData;
    private Date createTime;
    private int status;
    private Date pubDate;

    private List<SzbImage> json;


    public List<SzbImage> getJson() {
        if (this.jsonData!=null) {
            this.json = new ArrayList<>();
            try {
                this.json  = m.readValue(this.jsonData, new TypeReference<List<SzbImage>>(){});
                return this.json;
            } catch (IOException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Date getPubDate() {
        return pubDate;
    }
}
