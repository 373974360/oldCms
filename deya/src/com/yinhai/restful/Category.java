package com.yinhai.restful;

import com.deya.wcm.services.cms.category.CategoryTreeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Category extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String site_id = request.getParameter("siteId");
        String user_id = request.getParameter("user_id");
        String pid = request.getParameter("parentId");
        if (user_id == null || "".equals(user_id)) {
            user_id = "0";
        }
        String jsonStr = CategoryTreeUtil.getInfoCategoryTreeByUserIDSync(site_id, Integer.parseInt(user_id), Integer.parseInt(pid));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(jsonStr);
    }

}
