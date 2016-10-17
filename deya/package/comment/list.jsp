<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String id = request.getParameter("id");
	String template_id = request.getParameter("tm_id");
	
	if(id==null || "".equals(id))
		return;

	if(template_id != null && !"".equals(template_id))
	{//手动传模板ID
		VelocityInfoContextImp vc = new VelocityInfoContextImp(request);
		vc.setTemplateID(site_id,template_id);
		out.println(vc.parseTemplate());
	}
%>