<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%	
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String ser_id = request.getParameter("ser_id");
	String info_type = request.getParameter("info_type");
	if(ser_id==null || ser_id=="")
	{
		out.println("错误:未找到相应栏目");
		return;
	}
	
	if(info_type == null || "".equals(info_type))
		info_type = "xgxx";
	VelocitySerContextImp vc = new VelocitySerContextImp(request);
	vc.vcontextPut("info_type",info_type);
	vc.setSerInfoListTemplateID(ser_id);
	out.println(vc.parseTemplate());
%>