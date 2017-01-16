<%@ page import="com.sso.SyncOrg"%><%@ page contentType="application/json; charset=utf-8"%>
<%@ page language="java"%>
<%
    SyncOrg.syncOrgDeptOrUser("user",1);
    out.println("<script>");
    out.println("top.alert('全量同步用户同步成功！')");
    out.println("</script>");
%>
