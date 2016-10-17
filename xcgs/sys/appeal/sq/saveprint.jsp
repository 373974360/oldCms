<%@page contentType="application/msword; charset=UTF-8" %>
<%@page language="java" import="java.util.*"%>
<%@page import="com.deya.wcm.services.appeal.sq.SQManager,com.deya.wcm.bean.appeal.sq.SQBean"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.member.*,com.deya.wcm.bean.member.*"%>
<%@page import="java.net.*"%>
<%@page import="com.deya.util.Encode"%>
<%	
	String ie=request.getHeader("User-Agent"); //得到浏览器等相关信息
	System.out.println("ie==="+ie);
    String sq_id = FormatUtil.formatNullString(request.getParameter("sq_id"));
    SQBean bean = SQManager.getSqBean(Integer.parseInt(sq_id));
    String fileName = bean.getSq_title();  
    if(ie.contains("MSIE")){
    	fileName = java.net.URLEncoder.encode(fileName);
    }else if(ie.contains("Firefox")){
    	fileName = Encode.utf8ToIso_8859_1(fileName);
    }
	    //fileName = java.net.URLEncoder.encode(fileName,"UTF-8");
	    //fileName = Encode.systemToUtf8(fileName+bean.getSq_code()+".doc");
	response.setHeader("Content-Type","text/plain");
	response.setHeader("Content-Disposition","attachment;filename="+fileName+"_"+bean.getSq_code()+".doc");
	String model_id = FormatUtil.formatNullString(request.getParameter("model_id"));
	if(sq_id==null || sq_id=="")
	{			
		return;
	}
	VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
	vc.setModelID(model_id,"print");
	out.println(vc.parseTemplate()); 
%>
