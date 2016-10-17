<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager,com.deya.wcm.bean.appeal.sq.SQBean"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.member.*,com.deya.wcm.bean.member.*"%>
<%	
	String ysq_id = FormatUtil.formatNullString(request.getParameter("ysq_id"));
	String model_id = FormatUtil.formatNullString(request.getParameter("tm_id"));
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	
	if(ysq_id==null || ysq_id=="")
	{			
		return;
	}
	
	VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
	vc.setTemplateID(site_id,model_id);
	out.println(vc.parseTemplate());

%>