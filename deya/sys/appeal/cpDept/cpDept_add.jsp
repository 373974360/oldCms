<%@ page contentType="text/html; charset=utf-8"%>
<%
	String dept_id = request.getParameter("dept_id");
	String parent_id = request.getParameter("p_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机构注册维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/cpDept.js"></script>
<script type="text/javascript">

var dept_id = "<%=dept_id%>";
var parent_id = "<%=parent_id%>";
var defaultBean;
var current_obj;
var step_list = new List();

$(document).ready(function(){
	initButtomStyle();
	init_input();
	showCpDeptBean(dept_id);

	if(dept_id != "" && dept_id != "null" && dept_id != null)
	{		
		$("#addButton").click(updateCpDept);
	}
	else
	{
		$("#addButton").click(addCpDept);
	}
});

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="cpDept_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>机构名称：</th>
			<td class="width250">
				<input id="dept_name" name="dept_name" type="text" class="width300" value="" onblur="checkInputValue('dept_name',false,240,'机构名称','')"/>
				<input id="dept_id" name="dept_id" type="hidden" class="width300" value="<%=dept_id %>" />
				<input id="parent_id" name="parent_id" type="hidden" class="width300" value="<%=parent_id %>" />
			</td>			
		</tr>
		<tr>
			<th>机构描述：</th>
			<td>
				<textarea id="dept_memo" name="dept_memo" style="width:300px;height:50px" onblur="checkInputValue('dept_memo',true,900,'机构描述：','')"></textarea>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('cpDept_table',dept_id);" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
