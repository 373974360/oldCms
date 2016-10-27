<%@ page contentType="text/html; charset=utf-8"%>
<%
	String dept_id = request.getParameter("dept_id");
	String parent_id = request.getParameter("parent_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加注册用户</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/deptList.js"></script>
<script type="text/javascript">

var dept_id = "<%=dept_id%>";
var parent_id = <%=parent_id%>;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(dept_id != "" && dept_id != "null" && dept_id != null)
	{
	
	}
	else
	{
		$("#deptAddButton").click(addDept);
	}
});
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="register_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>用户：</th>
			<td colspan="7">
				<input id="dept_name" name="dept_name" type="text" style="width:300px;" value="" />
			</td>
		</tr>
		<tr>
			<th>帐号名称：</th>
			<td colspan="7">
				<input id="dept_fullname" name="dept_fullname" type="text" style="width:300px;" value="" />
			</td>
		</tr>
		<tr>
			<th>密　　码：</th>
			<td colspan="7">
				<select id="deptlevel_value" name="deptlevel_value" style="width:305px;"><option value=""></option></select>
			</td>
		</tr>
		<tr>
			<th>确认密码：</th>
			<td colspan="7">
				<input id="area_code" name="area_code" type="text" style="width:300px;" value="" />
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
			<input id="deptAddButton" name="btn1" type="button" onclick="" value="添加" />			
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
