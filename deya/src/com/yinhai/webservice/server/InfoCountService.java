package com.yinhai.webservice.server;

import com.yinhai.model.HotInfoResult;
import com.yinhai.model.InfoCountResult;

/**
 * @Description: 银海网站信息统计webservice
 * @Author: like
 * @Date: 2017-08-29 16:34
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public interface InfoCountService {

    /**
     * 根据时间和站点id按发布渠道统计所有栏目的当日发布量、当日访问量、总发布量以及总访问量
     * @param qrdate
     * @param siteId
     * @return
     */
    public InfoCountResult getInfoCount(String qrdate, String siteId);

    /**
     * 按照开始时间和结束时间获取该时间段内访问量排行前十的信息
     * @param start
     * @param end
     * @param siteId
     * @return
     */
    public HotInfoResult getHotInfoList(String start, String end, String siteId);

}
