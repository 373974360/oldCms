<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@ page import="com.deya.wcm.services.survey.SurveyService" %>
<%@ page import="com.deya.wcm.bean.survey.SurveyBean" %>
<%

	
	String s_id = request.getParameter("s_id");
	String cat_id = "";
	if(s_id==null || "null".equals(s_id))
	{
		s_id = "";
	}
    SurveyBean surveyBean = SurveyService.getSurveyBean(s_id);
	if(surveyBean != null){
        cat_id = surveyBean.getCategory_id();
    }

    VelocitySurveyContextImp vc = new VelocitySurveyContextImp(request);
	vc.setTemplateID(cat_id,"content");
	out.println(vc.parseTemplate());

%>