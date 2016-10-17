<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.appeal.sq.SQManager,com.deya.wcm.bean.appeal.sq.SQBean"%>
<%@page import="com.deya.wcm.template.velocity.*,com.deya.wcm.template.velocity.impl.*,com.deya.util.FormatUtil"%>
<%@page import="com.deya.wcm.services.member.*,com.deya.wcm.bean.member.*"%>
<%	
	String sq_id = FormatUtil.formatNullString(request.getParameter("sq_id"));
	String model_id = FormatUtil.formatNullString(request.getParameter("model_id"));

	String sq_code = FormatUtil.formatNullString(request.getParameter("sq_code"));
	String query_code = FormatUtil.formatNullString(request.getParameter("query_code"));

	if(sq_code != null && !"".equals(sq_code) && query_code != null && !"".equals(query_code) && (sq_id == null || "".equals(sq_id)))
	{
		SQBean sb = SQManager.searchBrowserSQBean(sq_code,query_code);
		if(sb != null)
		{
			sq_id = sb.getSq_id()+"";
			model_id = sb.getModel_id()+"";
			response.sendRedirect("view.jsp?sq_id="+sq_id+"&model_id="+model_id+"&sq_code="+sq_code+"&query_code="+query_code);
		}else
		{
			out.println("<script>");
			out.println("alert('没有查询到相关信件')");
			out.println("window.close()");
			out.println("</script>");
		}
	}else
	{
		if(sq_id==null || sq_id=="")
		{
			out.println("<script>");
			out.println("alert('没有查询到相关信件')");
			out.println("window.close()");
			out.println("</script>");
			return;
		}
		
		VelocityAppealContextImp vc = new VelocityAppealContextImp(request);
		vc.setModelID(model_id,"content");
		out.println(vc.parseTemplate());
	}

%>