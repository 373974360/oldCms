<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%
	String node_id = FormatUtil.formatNullString(request.getParameter("node_id"));
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String cat_id = request.getParameter("cat_id");
	String catalog_id = request.getParameter("catalog_id");
	if(cat_id==null || "".equals(cat_id))
		cat_id = "0";
	
	if(catalog_id != null && !"".equals(catalog_id))
	{
		VelocityInfoContextImp vc = new VelocityInfoContextImp(request);
		vc.setGKAppCatelogTemplateID(catalog_id,site_id,"index");
		out.println(vc.parseTemplate());
		return;
	}	
	VelocityInfoContextImp vc = new VelocityInfoContextImp(request);
	vc.setTemplateID(cat_id,site_id,node_id,"index");
	out.println(vc.parseTemplate());
%>
