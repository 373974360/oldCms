<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.search.search.SearchManager"%>
<%
	
	String t_id = request.getParameter("t_id");
	if(t_id==null || t_id=="")
	{
		out.println("--------");
		return;
	}

	VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
	//vc.vcontextPut("result",result);
	vc.setTemplate_id(t_id); 
	out.println(vc.parseTemplate()); 
    
%>
