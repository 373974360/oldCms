<%@ page contentType="text/html; charset=utf-8"%>
<%
	String fw_id = request.getParameter("fw_id");
	
	String site_id = request.getParameter("site_id");
	if(site_id == null){
		site_id = "";
	}
	
	String app_id = request.getParameter("app_id");
	if(app_id == null){
		app_id = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>过滤词维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/filterword.js"></script>
<script type="text/javascript">

var fw_id = "<%=fw_id%>";


var defaultBean;
var current_obj;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	showbean(fw_id);

	if(fw_id != "" && fw_id != "null" && fw_id != null)
	{		
		$("#addButton").click(updateFilterWord);
	}
	else
	{
		$("#addButton").click(addFilterWord);
	}
});

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="filterWord_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>过滤词：</th>
			<td>
				<input id="pattern" name="pattern" type="text" class="width300" value="" onblur="checkInputValue('pattern',false,240,'过滤词','')"/>
				<input id="site_id" name="site_id" type="hidden" value="<%=site_id%>" />
				<input id="app_id"  name="app_id" type="hidden" value="<%=app_id%>" />
				<input id="filterword_id" name="filterword_id" type="hidden" value="<%=fw_id%>" />
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>替换词：</th>
			<td>
				<input id="replacement" name="replacement" type="text" class="width300" value="" onblur="checkInputValue('replacement',false,240,'替换词','')"/>
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<!-- <input id="userAddReset" name="btn1" type="button" onclick="formReSet('filterWord_table',fw_id);" value="重置" />	 -->
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
