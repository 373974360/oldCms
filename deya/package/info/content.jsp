<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.control.domain.SiteDomainManager,com.deya.wcm.bean.cms.info.*"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.cms.info.*,com.deya.wcm.services.system.formodel.*"%>
<%
	String info_id = request.getParameter("info_id");
	String site_id = SiteDomainManager.getSiteIDByUrl(request.getRequestURL().toString());
	String template_id = request.getParameter("tm_id");
	InfoBean ib = InfoBaseManager.getInfoById(info_id,site_id);
	if(ib != null)
	{
		String model_ename = ModelManager.getModelEName(ib.getModel_id());
		int cat_id = ib.getCat_id();
		int model_id = ib.getModel_id();
		VelocityInfoContextImp vici = new VelocityInfoContextImp(ib.getInfo_id()+"",site_id,template_id,model_ename);
		out.println(vici.parseTemplate());
	}
	else{
        out.println("NO page");
    }
%>
