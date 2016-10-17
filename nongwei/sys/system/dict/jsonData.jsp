<%@ page contentType="text/html; charset=utf-8"%><%@page import="java.util.*,com.deya.wcm.services.system.dict.DataDictRPC"%><%
Map<String, String> map = new HashMap<String, String>();
String json = DataDictRPC.getDcTreeJson(map);
out.println(json);
%>
