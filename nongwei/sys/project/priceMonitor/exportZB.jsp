<%@ page contentType="text/html; charset=utf-8" %>

<%
    request.getSession().setAttribute("ExcelContent",request.getParameter("ExcelContent"));
    response.sendRedirect("/sys/project/priceMonitor/export.jsp");
%>
