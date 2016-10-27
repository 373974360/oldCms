<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%	
	String status = request.getParameter("status");
	String cat_id = request.getParameter("cat_id");
	if(cat_id==null || "".equals(cat_id))
	{
		cat_id = "";
	}
		
	VelocityInterViewContextImp vc = new VelocityInterViewContextImp(request);
	if("2".equals(status))
	{
		vc.setTemplateID(cat_id,"historyContent");
	}
	else
		vc.setTemplateID(cat_id,"live");
	out.println(vc.parseTemplate());

%>