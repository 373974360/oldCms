package com.yinhai.webservice.server;

import com.deya.wcm.db.DBManager;
import com.yinhai.model.HotInfoBean;
import com.yinhai.model.InfoCount;
import com.yinhai.model.InfoCountResult;

import java.util.*;

/**
 * @Description: 银海网站信息统计webservice
 * @Author: like
 * @Date: 2017-08-29 16:34
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class InfoCountServiceImpl implements InfoCountService {

    /**
     * 根据时间和站点id按发布渠道统计所有栏目的当日发布量、当日访问量、总发布量以及总访问量
     *
     * @param qrdate
     * @param siteId
     * @return
     */
    @Override
    public InfoCountResult[] getInfoCount(String qrdate, String siteId) {
        List<Map> publishSource = DBManager.queryFList("getPublishSource", null);
        InfoCountResult[] infoCountResults = null;
        if (publishSource != null && publishSource.size() > 0) {
            infoCountResults = new InfoCountResult[publishSource.size()];
            for (int i = 0; i < publishSource.size(); i++) {
                Map map = publishSource.get(i);
                InfoCountResult infoCountResult = new InfoCountResult();
                if (map.get("publish_source") != null) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("publish_source", map.get("publish_source").toString());
                    if (qrdate != null && !"".equals(qrdate)) {
                        params.put("date", qrdate + " 23:59:59");
                    }
                    if (siteId != null && !"".equals(siteId)) {
                        params.put("site_id", siteId);
                    }
                    List<Map> visitCount = DBManager.queryFList("getVisitCount", params);
                    params.remove("date");
                    params.put("date", qrdate + "%");
                    List<Map> publishCount = DBManager.queryFList("getPublishCount", params);
                    ArrayList<InfoCount> infoCounts = new ArrayList<InfoCount>();
                    for (Map visitMap : visitCount) {
                        InfoCount infoCount = new InfoCount();
                        infoCount.setCatId(Integer.parseInt(visitMap.get("cat_id").toString()));
//                        infoCount.setParentId(Integer.parseInt(visitMap.get("parent_id").toString()));
                        infoCount.setColname(visitMap.get("cat_cname").toString());
                        infoCount.setSortno(visitMap.get("cat_sort").toString());
//                        infoCount.setCatPosition(visitMap.get("cat_position").toString());
                        if (visitMap.get("total") != null) {
                            infoCount.setTpubnum(Integer.parseInt(visitMap.get("total").toString()));
                        } else {
                            infoCount.setTpubnum(0);
                        }
                        if (visitMap.get("hits") != null) {
                            infoCount.setTvisitnum(Integer.parseInt(visitMap.get("hits").toString()));
                        } else {
                            infoCount.setTvisitnum(0);
                        }
                        if (visitMap.get("day_hits") != null) {
                            infoCount.setVisitnum(Integer.parseInt(visitMap.get("day_hits").toString()));
                        } else {
                            infoCount.setVisitnum(0);
                        }
                        for (Map publishMap : publishCount) {
                            if (publishMap.get("cat_id") != null && infoCount.getCatId() == Integer.parseInt(publishMap.get("cat_id").toString())) {
                                if (publishMap.get("total") != null) {
                                    infoCount.setPubnum(Integer.parseInt(publishMap.get("total").toString()));
                                }
                            }
                        }
                        infoCounts.add(infoCount);
                    }
                    System.out.println(infoCounts.size() + "************");
                    infoCountResult.setSource(map.get("publish_source").toString());
                    infoCountResult.setInfoCounts(infoCounts);
                    infoCountResults[i] = infoCountResult;
                }
            }
        }
        return infoCountResults;
    }

    /**
     * 按照开始时间和结束时间获取该时间段内访问量排行前十的信息
     *
     * @param start
     * @param end
     * @param siteId
     * @return
     */
    @Override
    public HotInfoBean[] getHotInfoList(String start, String end, String siteId) {
        Map<String, String> params = new HashMap<String, String>();
        if (start != null && !"".equals(start)) {
            params.put("start_day", start);
        }
        if (end != null && !"".equals(end)) {
            params.put("end_day", end);
        }
        if (siteId != null && !"".equals(siteId)) {
            params.put("site_id", siteId);
        }
        List<Map> hotInfoList = DBManager.queryFList("getHotInfoList", params);
        HotInfoBean[] hotInfoBeans = null;
        if (hotInfoList != null && hotInfoList.size() > 0) {
            hotInfoBeans = new HotInfoBean[hotInfoList.size()];
            for (int i = 0; i < hotInfoList.size(); i++) {
                HotInfoBean hotInfoBean = new HotInfoBean(hotInfoList.get(i));
                hotInfoBeans[i] = hotInfoBean;
            }
        }
        return hotInfoBeans;
    }

}
