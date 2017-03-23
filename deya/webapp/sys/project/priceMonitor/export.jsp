<%
    response.setHeader("Content-disposition","attachment; filename=export.xls");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
    <TITLE> New Document </TITLE>
    <META NAME="Generator" CONTENT="EditPlus">
    <META NAME="Author" CONTENT="">
    <META NAME="Keywords" CONTENT="">
    <META NAME="Description" CONTENT="">
</HEAD>

<body>

<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" >
<head>

</head>
<body >
<%=request.getSession().getAttribute("ExcelContent")%>
<%request.getSession().removeAttribute("ExcelContent");%>
</body>
</HTML>
