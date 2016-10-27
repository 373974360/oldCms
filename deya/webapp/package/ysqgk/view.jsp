<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.services.appeal.sq.SQManager,com.deya.wcm.bean.appeal.sq.SQBean"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.member.*,com.deya.wcm.bean.member.*"%>
<%	
	String template_id = request.getParameter("tm_id");	
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	if(template_id != null && !"".equals(template_id))
	{//手动传模板ID
		VelocityYSQGKContextImp vc = new VelocityYSQGKContextImp(request);
		vc.setTemplateID(site_id,template_id);
		out.println(vc.parseTemplate());
	}else
	{
		VelocityYSQGKContextImp vc = new VelocityYSQGKContextImp(request);
		vc.setYSQGKTemplateID(site_id,"content");
		out.println(vc.parseTemplate());
	}
%>