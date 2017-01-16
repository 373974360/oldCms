<%@ page import="com.sso.SyncOrg"%><%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java"%>
<%
    SyncOrg.syncOrgDeptOrUser("dept",0);
    out.println("<script>");
    out.println("top.alert('增量同步组织机构成功！')");
    out.println("</script>");
%>
