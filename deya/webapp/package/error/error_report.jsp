<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.search.search.SearchManager"%>
<%@page import="com.deya.wcm.bean.cms.info.InfoBean"%>
<%@page import="com.deya.wcm.services.cms.info.InfoBaseManager"%>
<%@page import="com.deya.wcm.bean.system.error.ErrorReportBean"%>
<%
	
	String tm_id = request.getParameter("tm_id");
	if(tm_id==null || tm_id=="")
	{
		out.println("--------");
		return;
	}

	
	String from_url = (String)request.getHeader("referer");
    String info_id = (String)request.getParameter("info_id");
    InfoBean infoBean = InfoBaseManager.getInfoById(info_id);
    if(from_url==null){  
    	from_url = "";
    }   
    ErrorReportBean errorReportBean = new ErrorReportBean();
    errorReportBean.setSite_id(infoBean.getSite_id());
    errorReportBean.setInfo_id(Integer.valueOf(info_id));
    errorReportBean.setInfo_url(from_url);
    errorReportBean.setInfo_title(infoBean.getTitle());
    
	VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
	vc.vcontextPut("errorReportBean",errorReportBean);
	vc.setTemplate_id(tm_id); 
	out.println(vc.parseTemplate()); 
    
%>
