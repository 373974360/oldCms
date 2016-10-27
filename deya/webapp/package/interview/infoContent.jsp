<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%

	
	String id = request.getParameter("id");
	if(!FormatUtil.isValiditySQL(id))
	{
		return;
	}
	String cat_id = request.getParameter("cat_id");
	if(cat_id==null || "".equals(cat_id))
	{
		cat_id = "";
	}
		
	VelocityInterViewContextImp vc = new VelocityInterViewContextImp(request);
	vc.setTemplateID(cat_id,"infoContent");
	out.println(vc.parseTemplate());

%>