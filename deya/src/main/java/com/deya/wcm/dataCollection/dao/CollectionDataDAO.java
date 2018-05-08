package com.deya.wcm.dataCollection.dao;

import com.deya.wcm.dataCollection.bean.ArticleInfoBean;
import com.deya.wcm.dataCollection.bean.CollRuleBean;
import com.deya.wcm.db.DBManager;
import com.deya.wcm.server.ServerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionDataDAO {
    public static boolean addCollectionRule(CollRuleBean ruleBean) {
        return DBManager.insert("addCollRule", ruleBean);
    }

    public static List<CollRuleBean> getRuleList(Map<String, String> m) {
        return DBManager.queryFList("getRuleList", m);
    }

    public static List<CollRuleBean> getAllCollRuleBeanList() {
        return DBManager.queryFList("getAllCollRuleBeanList", "");
    }

    public static String getRuleListCount() {
        return DBManager.getString("getRuleListCount", "");
    }

    public static boolean deleteRuleById(Map<String, String> m) {
        return DBManager.delete("deleteRuleById", m);
    }

    public static CollRuleBean getCollRuleBeanByID(String id) {
        return (CollRuleBean) DBManager.queryFObj("getCollRuleBeanByID", id);
    }

    public static boolean updateRuleInfo(CollRuleBean collBean) {
        return DBManager.update("updateRuleInfo", collBean);
    }

    public static boolean addCollDataInfo(ArticleInfoBean articleBean) {
        return DBManager.insert("addCollDataInfo", articleBean);
    }

    public static String getCollInfoListCount(Map<String, String> m) {
        return DBManager.getString("getCollInfoListCount", m);
    }

    public static List<ArticleInfoBean> getCollDataList(Map<String, String> m) {
        return DBManager.queryFList("getCollDataList", m);
    }

    public static boolean deleteCollInfoById(Map<String, String> m) {
        return DBManager.delete("deleteCollInfoById", m);
    }

    public static List<ArticleInfoBean> getcollBeanListByIds(Map<String, String> m) {
        return DBManager.queryFList("getcollBeanListByIds", m);
    }

    public static boolean changeCollInfoStatus(String id) {
        return DBManager.update("changeCollInfoStatus", id);
    }

    public static ArticleInfoBean getCollDataInfobyid(String infoid) {
        return (ArticleInfoBean) DBManager.queryFObj("getCollDataInfobyid", infoid);
    }

    public static ArticleInfoBean getCollDataInfobyUrl(String url) {
        return (ArticleInfoBean) DBManager.queryFObj("getcollBeanByUrl", url);
    }

    public static boolean updateCollDataInfo(ArticleInfoBean artBean) {
        return DBManager.update("updateCollDataInfo", artBean);
    }

    public static List<CollRuleBean> getTimerCollList(String current_time) {
        Map m = new HashMap();
        m.put("current_time", current_time);
        String ip = ServerManager.LOCAL_IP;
        if ((ip != null) && (!"".equals(ip)) && (!"127.0.0.1".equals(ip)))
            m.put("server_ip", ServerManager.LOCAL_IP);
        return DBManager.queryFList("getTimerCollList", m);
    }

    public static boolean deleteRuleByRuleCatId(Map<String, String> m) {
        return DBManager.delete("deleteRuleByRuleCatId", m);
    }

    public static void updateCollTime(Map<String, String> m) {
        DBManager.update("updateCollTime", m);
    }
}
