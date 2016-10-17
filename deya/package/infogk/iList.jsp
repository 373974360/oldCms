<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%
	String isShared = FormatUtil.formatNullString(request.getParameter("isSd"));//是否是显示共享/基础目录树的信息列表
	String node_id = FormatUtil.formatNullString(request.getParameter("node_id"));
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String cat_id = request.getParameter("cat_id");
	String template_id = request.getParameter("tm_id");
	String catalog_id = request.getParameter("catalog_id");
	
	
	if(cat_id==null || "".equals(cat_id))
		cat_id = "0";
	
	if(catalog_id != null && !"".equals(catalog_id))
	{
		VelocityInfoContextImp vc = new VelocityInfoContextImp(request);
		vc.setGKAppCatelogTemplateID(catalog_id,site_id,"list");
		out.println(vc.parseTemplate());
		return;
	}

	if(template_id != null && !"".equals(template_id))
	{//手动传模板ID
		VelocityInfoContextImp vc = new VelocityInfoContextImp(request);
		vc.setTemplateID(site_id,template_id);
		out.println(vc.parseTemplate());
	}else
	{	
		VelocityInfoContextImp vc = new VelocityInfoContextImp(request);
		if("true".equals(isShared))
		{
			vc.setSharedCategoryTemplateID(cat_id,site_id,node_id,"list");
		}
		else
			vc.setTemplateID(cat_id,site_id,node_id,"list");
		out.println(vc.parseTemplate());
	}
%>