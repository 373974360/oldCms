<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.minlu.*,com.deya.wcm.bean.minlu.*,com.deya.wcm.services.system.formodel.*"%>
<%
	String tm_id = "";
	String template_id = FormatUtil.formatNullString(request.getParameter("tm_id"));
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String info_id = FormatUtil.formatNullString(request.getParameter("info_id"));
	String model_id = FormatUtil.formatNullString(request.getParameter("model_id"));
	String cur_page = FormatUtil.formatNullString(request.getParameter("cur_page"));
	int current_page = 1;
	if(cur_page != null && !"".equals(cur_page))
		current_page = Integer.parseInt(cur_page);
	
	if(template_id != null && !"".equals(template_id))
	{
		tm_id = template_id;
	}
	else
	{
		MingLuBean mlb = MingLuManager.getMingLuBean(site_id);
		if(mlb != null)
		{
			tm_id = mlb.getReinfo_temp_content()+"";
		}
	}
	String model_ename = ModelManager.getModelEName(Integer.parseInt(model_id));
	VelocityInfoContextImp vc = new VelocityInfoContextImp(info_id,site_id,tm_id,model_ename,current_page,request);		
	out.println(vc.parseTemplate());
%>