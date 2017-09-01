package com.yinhai.model;

import java.util.HashMap;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-08-30 10:44
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class InfoCountResult {

    private HashMap<String,InfoCount[]> infoCountMap; // 不要用Map类型

    public HashMap<String, InfoCount[]> getInfoCountMap() {
        return infoCountMap;
    }

    public void setInfoCountMap(HashMap<String, InfoCount[]> infoCountMap) {
        this.infoCountMap = infoCountMap;
    }
}
