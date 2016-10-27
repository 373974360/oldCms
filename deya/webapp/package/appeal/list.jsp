<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%
	String template_id = request.getParameter("tm_id");	
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String model_id = request.getParameter("model_id");
	if(model_id==null || model_id=="")
	{
		model_id = "0";
	}
	
	if(template_id != null && !"".equals(template_id))
	{//手动传模板ID
		VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
		vc.setTemplateID(site_id,template_id);
		out.println(vc.parseTemplate());
	}else
	{
		VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
		vc.setModelID(model_id,"list");
		out.println(vc.parseTemplate());
	}
%>
