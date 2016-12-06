package com.deya.wcm.dataCollection.services;

import com.deya.wcm.bean.logs.SettingLogsBean;
import com.deya.wcm.dataCollection.bean.ArticleInfoBean;
import com.deya.wcm.dataCollection.bean.CollRuleBean;
import com.deya.wcm.dataCollection.bean.RuleCatReleUser;
import com.deya.wcm.dataCollection.bean.RuleCategoryBean;
import com.deya.wcm.services.Log.LogManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class CollectionDataRPC {
    public static boolean addCollectionRule(CollRuleBean ruleBean) {
        if (ruleBean.getColl_url().contains("amp;")) {
            ruleBean.setColl_url(ruleBean.getColl_url().replaceAll("amp;", ""));
        }
        return CollectionDataManager.addCollectionRule(ruleBean);
    }

    public static List<CollRuleBean> getRuleList(Map<String, String> m) {
        return CollectionDataManager.getRuleList(m);
    }

    public static List<CollRuleBean> getAllCollRuleBeanList() {
        return CollectionDataManager.getAllCollRuleBeanList();
    }

    public static String getRuleListCount() {
        return CollectionDataManager.getRuleListCount();
    }

    public static boolean deleteRuleById(String ids) {
        return CollectionDataManager.deleteRuleById(ids);
    }

    public static CollRuleBean getCollRuleBeanByID(String id) {
        return CollectionDataManager.getCollRuleBeanByID(id);
    }

    public static boolean updateRuleInfo(CollRuleBean collBean, int id) {
        collBean.setId(id);
        if (collBean.getColl_url().contains("amp;")) {
            collBean.setColl_url(collBean.getColl_url().replaceAll("amp;", ""));
        }
        return CollectionDataManager.updateRuleInfo(collBean);
    }

    public static String getCollUrlJsonStr(String urlStr, String listTagS, String listTagE, String encoding) {
        if ((urlStr.contains("&lt;")) && (urlStr.contains("&gt;"))) {
            urlStr = urlStr.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        }
        return CollectionDataManager.getCollUrlJsonStr(urlStr, listTagS, listTagE, encoding);
    }

    public static void startCollectionData(String ruleid, String type) {
        if (type.equals("pause")) {
            CaijiBean.putMapString(ruleid, type);
        } else if (type.equals("start_pause")) {
            CaijiBean.putMapString(ruleid, "start");
        } else if (type.equals("end")) {
            CaijiBean.putMapString(ruleid, "end");
            CaijiBean.removeMapString(ruleid);
        } else if (type.equals("start")) {
            CaijiBean.putMapString(ruleid, "start");
            CollectionDataManager.startCollectionData(ruleid);
        }
    }

    public static void collTimer(String ruleid) {
        CaijiBean.putMapString(ruleid, "start");
        CollectionDataManager.startCollectionData(ruleid);
    }

    public static boolean isHaveCollIsRuning() {
        Map m = CaijiBean.map;
        boolean b = true;
        Iterator it = m.entrySet().iterator();
        if (it.hasNext()) {
            b = false;
        }

        return b;
    }

    public static List<String> getAllCollIsRuning() {
        List list = new ArrayList();
        Map<String, String> m = CaijiBean.map;
        for (Map.Entry entry : m.entrySet()) {
            String key = ((String) entry.getKey()).toString();
            list.add(key);
        }
        return list;
    }

    public static String getCollInfoListCount(Map<String, String> m) {
        return CollectionDataManager.getCollInfoListCount(m);
    }

    public static List<ArticleInfoBean> getCollDataList(Map<String, String> m) {
        return CollectionDataManager.getCollDataList(m);
    }

    public static boolean deleteCollInfoById(String ids) {
        return CollectionDataManager.deleteCollInfoById(ids);
    }

    public static List<ArticleInfoBean> getcollBeanListByIds(String ids) {
        return CollectionDataManager.getcollBeanListByIds(ids);
    }

    public static boolean changeCollInfoStatus(String id) {
        return CollectionDataManager.changeCollInfoStatus(id);
    }

    public static ArticleInfoBean getCollDataInfobyid(String infoid) {
        return CollectionDataManager.getCollDataInfobyid(infoid);
    }

    public static boolean updateCollDataInfo(ArticleInfoBean artBean) {
        return CollectionDataManager.updateCollDataInfo(artBean);
    }

    public static String readCollLogByRuleID(String rule_id, int lineNum) {
//        return CollectionDataManager.readCollLogByRuleID(rule_id, lineNum);
        return "请到数据采集记录查看结果";
    }

    public static String getJSONTreeBySiteUser(int user_id,String site_id){
        return RuleCategoryManager.getJSONTreeBySiteUser(user_id, site_id);
    }

    public static String getJSONTreeStr(Map<String,String> m){
        return RuleCategoryManager.getJSONTreeStr(m);
    }

    public static List<RuleCatReleUser> getRuleCatReleUserListByCat(int wcat_id, String site_id) {
        return RuleCatReleUserManager.getRuleCatReleUserListByCat(wcat_id, site_id);
    }

    public static boolean insertRuleCatReleUserByCat(int wcat_id, String usre_ids, String group_ids, String app_id, String site_id, HttpServletRequest request) {
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        return RuleCatReleUserManager.insertRuleCatReleUserByCat(wcat_id, usre_ids, group_ids, app_id, site_id, stl);
    }

    public static boolean saveSort(String ids, HttpServletRequest request){
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        return RuleCategoryManager.saveSort(ids, stl);
    }

    public static boolean deleteRuleCategory(Map<String, String> mp, HttpServletRequest request){
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        return RuleCategoryManager.deleteRuleCategory(mp, stl);
    }

    public static boolean insertRuleCate(RuleCategoryBean wcb, HttpServletRequest request){
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        return RuleCategoryManager.insertRuleCate(wcb, stl);
    }

    public static boolean updateRuleCategory(RuleCategoryBean wcb, HttpServletRequest request){
        SettingLogsBean stl = LogManager.getSettingLogsByRequest(request);
        return RuleCategoryManager.updateRuleCategory(wcb, stl);
    }

    public static RuleCategoryBean getRuleCategoryByID(String id){
        return RuleCategoryManager.getRuleCategoryByID(id);
    }

    public static List<RuleCategoryBean> getRuleCateList(String id, Map<String, String> mp)
    {
        return RuleCategoryManager.getRuleCateList(id, mp);
    }

    public static void main(String[] args) {
    }
}
