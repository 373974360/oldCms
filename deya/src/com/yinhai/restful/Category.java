package com.yinhai.restful;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deya.wcm.db.DBManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//
        String source = request.getParameter("source");
        String colid = request.getParameter("colid");

        Map<String, String> params = new HashMap<String, String>();
        if (source != null) {
            params.put("source", CategoryMap.getCategorySourceBySourceParamValue(source));
        }
        if (colid != null) {
            params.put("colid", CategoryMap.getCidByColid(colid));
        }
        List<Map> hotInfoList = DBManager.queryFList("getCategoryByParentId", params);
        System.out.println(hotInfoList);
        JSONArray jsonArray = new JSONArray();
        if (hotInfoList != null && hotInfoList.size() > 0) {
            for (Map map : hotInfoList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("colid", map.get("cat_id"));
                jsonObject.put("colname", map.get("cat_cname"));
                jsonArray.add(jsonObject);
            }
        }
        String s = JSON.toJSONString(jsonArray);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(s);
    }

}
