package com.deya.project.dz_szb;

import java.util.List;

/**
 * Created by yangyan on 2017/2/18.
 */
public class SzbImage {
    private String t;
    private String imgUrl;
    private List<SzbImageArea> areas;


    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<SzbImageArea> getAreas() {
        return areas;
    }

    public void setAreas(List<SzbImageArea> areas) {
        this.areas = areas;
    }
}
