<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%	
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String ser_id = request.getParameter("ser_id");
	if(ser_id==null || ser_id=="")
	{
		out.println("错误:未找到相应栏目");
		return;
	}
	
	VelocitySerContextImp vc = new VelocitySerContextImp(request);
	vc.setSerTopicTemplateID(ser_id,"list");
	out.println(vc.parseTemplate());
%>