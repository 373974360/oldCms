<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%
	String template_id = FormatUtil.formatNullString(request.getParameter("tm_id"));
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
		
	VelocityMingLuContextImp vc = new VelocityMingLuContextImp(request);
	if(template_id != null && !"".equals(template_id))
	{
		vc.setTemplateID(site_id,template_id);
	}
	else
		vc.setMLTemplateID(site_id,"piclist");
	out.println(vc.parseTemplate());
%>