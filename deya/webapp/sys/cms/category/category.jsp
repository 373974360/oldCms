<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.deya.wcm.services.cms.category.CategoryTreeUtil" %>
<%
	String site_id = request.getParameter("site_id");
    String user_id = request.getParameter("user_id");
    String pid = request.getParameter("pid");
    if(user_id == null || "".equals(user_id))
    {
        user_id = "0";
    }
    String jsonStr = CategoryTreeUtil.getInfoCategoryTreeByUserIDSync(site_id,Integer.parseInt(user_id), Integer.parseInt(pid));
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print(jsonStr);
%>