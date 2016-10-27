<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.wcm.services.cms.info.InfoBaseManager"%>
<%
	String info_id = request.getParameter("info_id");
	String siteid = request.getParameter("site_id");	
	out.println(InfoBaseManager.getInfoTemplateContent(info_id,siteid));
%>
