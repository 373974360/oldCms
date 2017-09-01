package com.yinhai.restful;

import com.alibaba.fastjson.JSON;
import com.deya.wcm.db.DBManager;
import com.yinhai.model.HotInfoBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class InfoContent extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String info_id = request.getParameter("infoId");
        Map<String, String> params = new HashMap<String, String>();
        if (info_id != null) {
            params.put("info_id", info_id);
        }
        List infoContentByInfoId = DBManager.queryFList("getInfoContentByInfoId", params);
        HotInfoBean hotInfoBean = new HotInfoBean();
        if (infoContentByInfoId != null && infoContentByInfoId.size() > 0) {
            hotInfoBean = (HotInfoBean) infoContentByInfoId.get(0);
        }
        String s = JSON.toJSONString(hotInfoBean);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(s);
    }


}
