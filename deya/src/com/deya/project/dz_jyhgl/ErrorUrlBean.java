package com.deya.project.dz_jyhgl;

import java.io.Serializable;

public class ErrorUrlBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String url="";
    private int code=0;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
