<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%	
	String m_id = request.getParameter("m_id");
	String app_id = request.getParameter("app_id");
	String site_id = request.getParameter("site_id");
	if(m_id==null || m_id=="")
	{		
		return;
	}	
	VelocityCommentContextImp vc = new VelocityCommentContextImp(request);	
	vc.setTemplateID(app_id,site_id,m_id);
	out.println(vc.parseTemplate());

%>