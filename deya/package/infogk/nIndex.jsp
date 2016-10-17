<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%
	String node_id = FormatUtil.formatNullString(request.getParameter("node_id"));
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String cat_id = request.getParameter("cat_id");
	if(node_id==null || node_id=="")
	{
		out.println("错误:未找到相应栏目");
		return;
	}
	
	//System.out.println("cat_id=="+cat_id+"=====node_id==="+node_id+"======site_id=="+site_id);

	VelocityInfoContextImp vc = new VelocityInfoContextImp(request);
	vc.setTemplateID(cat_id,site_id,node_id,"nodeIndex");
	out.println(vc.parseTemplate());
%>
