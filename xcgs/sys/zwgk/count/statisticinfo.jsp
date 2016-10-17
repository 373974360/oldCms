<%@page contentType="text/html;charset=utf-8"%>
<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>详细信息</title>


<jsp:include page="../../include/include_tools.jsp"/>
<link type="text/css" rel="stylesheet" href="/sys/styles/main.css" />
<link type="text/css" rel="stylesheet" href="/sys/styles/sub.css" />
<%--<script type="text/javascript">
var nodename = "${param.nodename}";
var entername = "${param.entername}";
alert(nodename);
alert(entername);
</script> 
--%></head> 
<body>
<table  cellpadding="0" cellspacing="0" style="width:90%;text-algin:center;"  >
<tr>
<td>标题</td>
<td>时间</td>
<%
String key = request.getParameter("entername");

System.out.println("key==============================================================="+key);

%>
</tr>
</table>
</body>
</html>