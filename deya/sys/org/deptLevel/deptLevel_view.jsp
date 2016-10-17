<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String deptlevel_id = request.getParameter("deptlevel_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>&ucirc;</title>

<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/deptLevelList.js"></script>
<script type="text/javascript">
	var deptlevel_id = "<%=deptlevel_id%>";
	var viewbean;
	
$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initGroupView();
	disabledWidget();
});

function initGroupView()
{
	viewbean = DeptRPC.getDeptLevelBeanByID(deptlevel_id);
		$("#level_name").val(viewbean.deptlevel_name);
		$("#level_value").val(viewbean.deptlevel_value);
		$("#level_memo").val(viewbean.deptlevel_memo);

}

</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="deptLevel_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>级别名称:</th>
			<td >
				<input id="level_name" name="level_name" type="text" class="width300" value="" />
			</td>
		</tr>
		<tr>
			<th>级别数值:</th>
			<td >
				<input id="level_value" name="level_value" type="text" class="width300" value="" />
			</td>
		</tr>
		<tr>
			<th>备注:</th>
			<td >
				<textarea id="level_memo" name="level_memo" style="width:300px;;height:80px;"></textarea>
			</td>
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="viewCancel" name="btn1" type="button" onclick="top.CloseModalWindow()" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>

</body>
</html>
