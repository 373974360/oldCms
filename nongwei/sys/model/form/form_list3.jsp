<%@ page contentType="text/html; charset=utf-8"%>
<%
String model_id = request.getParameter("model_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内容模型字段管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/form_list3.js"></script>
<script type="text/javascript">

var model_id = "<%=model_id%>";

$(document).ready(function(){
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadList();
	//$("#turn span").click(clickTurnEvent);
	$(".system").show();
})


</script>
</head>

<body>
<div>

	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>

</div>
</body>
</html>