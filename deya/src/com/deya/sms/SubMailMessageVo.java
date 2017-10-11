package com.deya.sms;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by program on 2016/11/24.
 */
public class SubMailMessageVo implements Serializable {
    private String to;
    private Map<String,String> vars;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Map<String, String> getVars() {
        return vars;
    }

    public void setVars(Map<String, String> vars) {
        this.vars = vars;
    }
}
