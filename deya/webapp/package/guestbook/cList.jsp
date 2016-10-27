<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String cat_id = FormatUtil.formatNullString(request.getParameter("gbcat_id"));
	String template_id = request.getParameter("tm_id");
	if(cat_id==null || "".equals(cat_id))
	{
		out.println("错误:未找到相应主题");
		return;
	}
	if(template_id !=null && !"".equals(template_id))
	{
		VelocityGuestBookContextImp vc = new VelocityGuestBookContextImp(request);
		vc.setTemplateID(site_id,template_id);
		out.println(vc.parseTemplate());
	}else
	{
		VelocityGuestBookContextImp vc = new VelocityGuestBookContextImp(request);
		vc.setGuestbookIndexTemplateID(cat_id,site_id);
		out.println(vc.parseTemplate());
	}
%>