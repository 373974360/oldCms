<%@ page contentType="text/html; charset=utf-8"%>
<%
	String site_id=request.getParameter("site_id");
	String gz_id=request.getParameter("gz_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新建更新规则</title>
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/infoUpdateList.js"></script>
<script type="text/javascript">
var defaultBean;
var gz_id="<%=gz_id%>"
$(document).ready(function(){
	initButtomStyle();
	init_input();
	if(gz_id != "" && gz_id != "null" && gz_id != null)
	{	 
		defaultBean = InfoUpdateRPC.getInfoUpdateById(gz_id);
		if(defaultBean)
		{
			$("#infoupate_table").autoFill(defaultBean);
		} 
		$("#addButton").click(funUpdate);
	}
	else
	{
		$("#addButton").click(funAdd);
	}
});
</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
	<table id="infoupate_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
		<input type="hidden" name="site_id" id="site_id" value="<%=site_id %>"/>
		<tbody>
			<tr>
				<th><span class="f_red">*</span>规则名称：</th>
				<td class="width250">
					<input id="gz_name" name="gz_name" type="text" class="width300" value="" onblur="checkInputValue('gz_name',false,80,'规则名称','')"/>
				</td>
			</tr>
			<tr>
				<th><span class="f_red">*</span>间隔天数：</th>
				<td class="width250">
					<input id="gz_day" name="gz_day" type="text" class="width300" value="" onblur="checkInputValue('gz_day',false,80,'间隔天数','checkInt')"/>
				</td>
			</tr>
			<tr>
				<th><span class="f_red">*</span>更新条数：</th>
				<td class="width250">
					<input id="gz_count" name="gz_count" type="text" class="width300" value="" onblur="checkInputValue('gz_count',false,80,'更新条数','checkInt')"/>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('infoupate_table',gz_id)" value="重置" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
