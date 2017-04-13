<%@ page contentType="text/html; charset=utf-8"%>
<%
	String hot_id = request.getParameter("hot_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>热词修改</title>


<jsp:include page="../../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/hotWords.js"></script>
<script type="text/javascript">

var hot_id = "<%=hot_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(hot_id != "" && hot_id != "null" && hot_id != null)
	{		
		defaultBean = AssistRPC.getHotWordById(hot_id);
		if(defaultBean)
		{
			$("#hotword_table").autoFill(defaultBean);					
		}
		disabledWidget();
	}
	
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="hotword_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>热词名称：</th>
			<td class="width250">
				<input id="hot_name" name="hot_name" type="text" class="width200" value="" onblur="checkInputValue('hot_name',false,80,'热词名称','')"/>
				<input type="hidden" name="hot_id" id="hot_id" value="0" />
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>关联地址：</th>
			<td >
				<input id="hot_url" name="hot_url" type="text" class="width200" value="" onblur="checkInputValue('hot_url',false,240,'关联地址','')"/>
				<input type="hidden" name="app_id" id="app_id" value="0"/>
				<input type="hidden" name="site_id" id="site_id" value="0"/>
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
			<input id="userAddButton" name="btn1" type="button" onclick="tab_colseOnclick(curTabIndex)" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
