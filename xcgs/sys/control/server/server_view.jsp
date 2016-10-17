<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看服务器信息</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/serverList.js"></script>
<script type="text/javascript">

var server_id = "${param.server_id}"; 
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(server_id != "" && server_id != "null" && server_id != null)
	{	 
		defaultBean = SiteServerRPC.getServerBeanByID(server_id);
		if(defaultBean)
		{
			$("#server_table").autoFill(defaultBean);					
		} 
		$("#addButton").click(funUpdate);
		//$("#site_id").attr("disabled","disabled");
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
<table id="server_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>服务器名称：</th>
			<td class="width250">
				<input id="server_name" name="server_name" type="text" class="width300" disabled="disabled" value="" onblur="checkInputValue('server_name',false,20,'服务器名称','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>服务器IP：</th>
			<td class="width250">
				<input id="server_ip" name="server_ip" type="text" class="width300" value="" disabled="disabled" onblur="checkInputValue('server_ip',false,80,'服务器IP','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>服务器类型：</th>
			<td class="width250">
				<select id="server_type" name="server_type" disabled="disabled" class="width305">
				  <option value="1">数据库服务器</option>
				  <option value="2">发布服务器</option>
				  <option value="3">访问服务器</option>
				  <option value="4">素材服务器</option>
				</select>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td colspan="3">
				<textarea id="server_memo" name="server_memo" disabled="disabled" style="width:300px;;height:50px;" onblur="checkInputValue('server_memo',true,900,'备注','')"></textarea>		
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
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="关闭" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
