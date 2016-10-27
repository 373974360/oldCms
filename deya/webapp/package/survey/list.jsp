<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%

	
	String cat_id = request.getParameter("cat_id");
	
	if(cat_id==null || "null".equals(cat_id))
	{
		cat_id = "";
	}
		
	VelocitySurveyContextImp vc = new VelocitySurveyContextImp(request);
	vc.setTemplateID(cat_id,"list");
	out.println(vc.parseTemplate());

%>