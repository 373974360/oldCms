<%@ page import="com.sso.SyncOrg"%><%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java"%>
<%
    SyncOrg.syncOrgDeptOrUser("user",0);
    out.println("<script>");
    out.println("top.alert('增量用户同步成功！')");
    out.println("</script>");
%>
