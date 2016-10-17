<%@ page contentType="text/html; charset=utf-8"%>
<%
String app_id = request.getParameter("app");
if(app_id == null){
	app_id = "0";
}
String site_id = request.getParameter("site_id");
if(site_id == null){
	site_id = "0";
}
String tc_id = request.getParameter("tc_id");
String t_id = request.getParameter("t_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模板管理</title>

<meta name="template" content="cicro" />
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/templateVer.js"></script>
<script type="text/javascript">

var site_id = "<%=site_id%>";
var app = "<%=app_id%>";
var t_id = "<%=t_id%>";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	initTable();
	reloadTemplateVerDataList();	
});


</script>
</head>

<body>
<div>
	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>
	<span class="blank12"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="center" valign="middle">
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />			
		</td>
	</tr>
   </table>	
</div>
</body>
</html>