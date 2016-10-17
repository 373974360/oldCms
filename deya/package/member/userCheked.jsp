<%@ page contentType="text/html; charset=utf-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.deya.wcm.services.member.*"%>
<%
	String action_type = request.getParameter("action_type");
	if("account".equals(action_type))
	{
		String me_account = request.getParameter("me_account");
		out.println(!MemberManager.isAccountExisted(me_account));
	}

	if("security_code".equals(action_type))
	{
		String codeSession = (String)request.getSession().getAttribute("valiCode");
		String auth_code = request.getParameter("auth_code");
		out.println(auth_code.equals(codeSession));		
	}
%>