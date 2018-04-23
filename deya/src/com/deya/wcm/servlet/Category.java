package com.deya.wcm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deya.wcm.services.cms.category.CategoryTreeUtil;

public class Category extends HttpServlet {

	// Process the HTTP Get request

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String site_id = request.getParameter("site_id");
		String user_id = request.getParameter("user_id");
		String pid = request.getParameter("pid");
		String type = request.getParameter("type");
		if(user_id == null || "".equals(user_id))
		{
			user_id = "0";
		}
		if(type == null || "".equals(type))
		{
			type = "info";
		}
		String jsonStr = CategoryTreeUtil.getInfoCategoryTreeByUserIDSync(site_id,Integer.parseInt(user_id), Integer.parseInt(pid),type);
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().print(jsonStr);
	}

}
