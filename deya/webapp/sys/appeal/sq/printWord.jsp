<%@ page contentType="plication/msword; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.appeal.sq.SQManager,com.deya.wcm.bean.appeal.sq.SQBean"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.member.*,com.deya.wcm.bean.member.*"%>
<%	
	String sq_id = FormatUtil.formatNullString(request.getParameter("sq_id"));
	String model_id = FormatUtil.formatNullString(request.getParameter("model_id"));
	
	if(sq_id==null || sq_id=="")
	{			
		return;
	}
	
	VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
	vc.setModelID(model_id,"print");
	out.println(vc.parseTemplate());

%>
