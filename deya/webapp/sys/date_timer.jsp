<%@ page import="com.deya.wcm.timer.SQTimerImpl" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%
	new SQTimerImpl().timingTask();
	com.deya.wcm.services.zwgk.count.GKCountManager.CateInfoCounting();
%>