<%@page contentType="text/html; charset=utf-8"%>
<%@page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil" %>
<%@page import="com.deya.wcm.services.search.search.SearchManager" %>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager" %>
<%@page import="com.deya.project.searchkeys.*" %>
<%
	String t_id = request.getParameter("t_id");
	if(t_id==null || t_id=="")
	{
		out.println("--------");
		return;
	}
	String title = com.deya.util.Encode.iso_8859_1ToUtf8(request.getParameter("q"));
	String site_id = FormatUtil.formatNullString(request.getParameter("site_id"));
	if(title !=""){
		SearchCustomKeyService.addKeys(title,site_id);
	}
	request.setAttribute();
	com.deya.wcm.bean.search.SearchResult result = SearchManager.searchGJ(request);
	VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
	vc.vcontextPut("result",result);
	//vc.setModelID("",t_id);
	vc.setTemplate_id(t_id); 
	out.println(vc.parseTemplate()); 
%>