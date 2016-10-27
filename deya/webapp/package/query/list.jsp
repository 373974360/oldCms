<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%	
	String conf_id = request.getParameter("conf_id");
	if(conf_id==null || conf_id=="")
	{
		out.println("--------");
		return;
	}
		
	VelocityQueryContextImp  vc = new VelocityQueryContextImp(request);
	vc.setTemplatID(conf_id,"list");	
	out.println(vc.parseTemplate());

%>
