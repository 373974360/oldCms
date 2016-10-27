<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());	
	String cat_id = request.getParameter("cat_id");
	if(cat_id==null || "null".equals(cat_id))
	{
		cat_id = "";
	}
		
	VelocityInterViewContextImp vc = new VelocityInterViewContextImp(request);
	vc.vcontextPut("site_id",site_id);
	vc.setTemplateID(cat_id,"list");
	out.println(vc.parseTemplate());

%>