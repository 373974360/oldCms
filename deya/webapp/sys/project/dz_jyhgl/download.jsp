<%@ page contentType="text/html; charset=utf-8"%>
<%
	String site_id=request.getParameter("site_id");
	String gz_id=request.getParameter("gz_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内容更新规则</title>
<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/download.js"></script>
<script type="text/javascript">
var site_id="<%=site_id%>";
var gz_id="<%=gz_id%>";
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll");
	initTable();
	reloadDataList();
});
</script>
</head>
<body>
<div>
<div id="table"></div>
<div id="turn"></div>
</div>
</body>
</html>
