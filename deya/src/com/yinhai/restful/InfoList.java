package com.yinhai.restful;

import com.alibaba.fastjson.JSON;
import com.deya.wcm.db.DBManager;
import com.yinhai.model.HotInfoBean;
import com.yinhai.model.InfoListResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: like
 * @Date: 2017-09-01 11:49
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class InfoList extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String catId = request.getParameter("colid");
        String source = request.getParameter("source");
        String start = request.getParameter("start");
        String limit = request.getParameter("limit");
        InfoListResult infoListResult = new InfoListResult();
        Map<String, String> params = new HashMap<String, String>();
        if (catId != null) {
            params.put("cat_id",catId);
        }
        if (source != null) {
            params.put("publish_source",source + "%");
        }
        if (start != null) {
            params.put("start",start);
            infoListResult.setStart(Integer.parseInt(start));
        }
        if (limit != null) {
            params.put("limit",limit);
            infoListResult.setLimit(Integer.parseInt(limit));
        }
        List<Map> infoListByCatId = DBManager.queryFList("getInfoListByCatId", params);
        ArrayList<HotInfoBean> hotInfoBeans = new ArrayList<HotInfoBean>();
        if (infoListByCatId != null && infoListByCatId.size() > 0) {
            for (Map m : infoListByCatId) {
                HotInfoBean hotInfoBean = new HotInfoBean(m);
                hotInfoBeans.add(hotInfoBean);
            }
        }
        Object count = DBManager.queryFObj("getInfoListCountByCatId", params);
        infoListResult.setTotal(Integer.parseInt(count.toString()));
        infoListResult.setList(hotInfoBeans);
        String s = JSON.toJSONString(infoListResult);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(s);
    }


}
