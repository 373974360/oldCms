<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String audit_id = request.getParameter("audit_id");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>审计日志编辑</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../include/include_tools.jsp"/>
	<script type="text/javascript" src="js/csLog.js"></script>
<script type="text/javascript">

var audit_id = "<%=audit_id%>";
var defaultBean;
var current_obj;


$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(audit_id != "" && audit_id != "null" && audit_id != null)
	{		
		defaultBean = LogManager.getLogSettingBean(audit_id);
		if(defaultBean)
		{
			$("#csLog_table").autoFill(defaultBean);	
		}
		disabledWidget();
	}
	
});

</script>

  </head>
  
  <body>
    This is my JSP page. <br>
  </body>
</html>
