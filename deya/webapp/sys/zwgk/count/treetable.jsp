<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@page import="com.deya.util.DateUtil"%>
<%
	String siteid = request.getParameter("site_id");
	String app_id = request.getParameter("app_id");
	
	String start_day = request.getParameter("start_day");
	String end_day = request.getParameter("end_day");
	String type = request.getParameter("type");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//CN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>政务公开站点工作量统计</title>



<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="/sys/js/jquery-plugin/jquery.treeTable.js"></script>
<script type="text/javascript" src="js/treetable.js"></script>
<script type="text/javascript">


var site_id = "<%=siteid%>";
var app = "<%=app_id%>";
var start_day = "<%=start_day%>";
var end_day = "<%=end_day%>";
var type = "<%=type%>";
var node_ids = ""; //

$(document).ready(function(){
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	if(type == "allsite")
	{
		node_ids = window.node_ids;
		createTableAllSite();
	}
	else
	{
		createTable();
	}
});

</script>
</head>
<body>
<div>
<table id="treeTableCount" class="treeTableCSS table_border"  border="0" cellpadding="0" cellspacing="0">

</table>
<span class="blank9"></span>
</div>
</body>
</html>