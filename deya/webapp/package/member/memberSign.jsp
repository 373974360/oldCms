<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.util.*"%>
<%@ page import="com.deya.project.dz_sign.SignBean" %>
<%@ page import="com.deya.project.dz_sign.SignManager" %>
<%
	String member_id = FormatUtil.formatNullString(request.getParameter("member_id"));
	String hall_name = FormatUtil.formatNullString(request.getParameter("hall_name"));

	SignBean signBean = new SignBean();
	signBean.setMember_id(Integer.parseInt(member_id));
	signBean.setHall_name(hall_name);

	boolean result = SignManager.insertSign(signBean);
	if(result){
		out.println("<script>");
		out.println("top.alert('签到成功！')");
		out.println("top.location.href='/'");
		out.println("</script>");
	}else{
		out.println("<script>");
		out.println("top.alert('签到失败！')");
		out.println("</script>");
	}
%>