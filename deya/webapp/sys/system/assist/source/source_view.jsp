<%@ page contentType="text/html; charset=utf-8"%>
<%
	String source_id = request.getParameter("source_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>来源维护</title>


<jsp:include page="../../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/source.js"></script>
<script type="text/javascript">

var source_id = "<%=source_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(source_id != "" && source_id != "null" && source_id != null)
	{		
		defaultBean = AssistRPC.getSourceById(source_id);
		if(defaultBean)
		{
			$("#Source_table").autoFill(defaultBean);					
		}
		disabledWidget();
	}
	
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="Source_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>来源名称：</th>
			<td class="width250">
				<input id="source_name" name="source_name" type="text" class="width200" value="" onblur="checkInputValue('source_name',false,80,'Source名称','')"/>
				<input type="hidden" name="source_id" id="source_id" value="0" />
			</td>
		</tr>
		<tr>
			<th>首字母：</th>
			<td >
				<input id="source_initial" name="source_initial" type="text" class="width200" value=""/>
			</td>
		</tr>	
		<tr>
			<th>来源URL：</th>
			<td >
				<input id="source_url" name="source_url" type="text" class="width200" value=""/>
				<input type="hidden" name="app_id" id="app_id" value="0"/>
				<input type="hidden" name="site_id" id="site_id" value="0"/>
			</td>
		</tr>	
		<tr>
			<th>来源LOGO路径：</th>
			<td >
				<input id="logo_path" name="logo_path" type="text" class="width200" value=""/>
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
			<input id="userAddButton" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
