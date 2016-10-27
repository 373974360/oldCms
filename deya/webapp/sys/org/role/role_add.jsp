<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app");
	String role_id = request.getParameter("role_id");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/roleList.js"></script>
<script type="text/javascript">

var app_id = "<%=app_id%>";
var role_id = "<%=role_id%>";
var site_id = "<%=site_id%>";
if(site_id == null || site_id == "null")
	site_id = "";
var defaultBean;

$(document).ready(function(){
	if(app_id == "system")
	{
		$("#system_selected").show();
		var AppRPC = jsonrpc.AppRPC;
		var app_list = AppRPC.getAppList();
		app_list = List.toJSList(app_list);
		if(app_list != null && app_list.size() > 0)
		{
			for(var i=0;i<app_list.size();i++)
			{
				if(app_list.get(i).app_id != app_id)
					$("#app_list").append('<li style="float:none;height:20px"><input type="checkbox" id="a_app_ids" value="'+app_list.get(i).app_id+'"><label>'+app_list.get(i).app_name+'</label></li>');
			}
		}
	}

	initButtomStyle();
	init_input();

	if(role_id != "" && role_id != "null" && role_id != null)
	{		
		defaultBean = RoleRPC.getRoleBeanByID(role_id);
		if(defaultBean)
		{
			$("#role_table").autoFill(defaultBean);	
			
			var a_app_ids = defaultBean.a_app_id;
			
			if(a_app_ids != "" && a_app_ids != null)
			{
				var tempA = a_app_ids.split(",");				
				for(var i=0;i<tempA.length;i++)
				{
					$(":checkbox[value="+tempA[i]+"]").attr("checked","true");
				}
			}
		}
		$("#addButton").click(updateRole);
	}
	else
	{
		$("#addButton").click(addRole);
	}
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="role_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>角色名称：</th>
			<td class="width250">
				<input id="role_name" name="role_name" type="text" class="width300" value="" onblur="checkInputValue('role_name',false,80,'角色名称','')"/>
			</td>			
		</tr>
		<tr id="system_selected" style="display:none">
			<th>所属应用系统：</th>
			<td class="width250">
				<div style="width:303px;height:160px;overflow:auto;" class="border_color">
					<ul id="app_list" style="margin:10px">
					</ul>
				</div>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">角色说明：</th>
			<td colspan="3">
				<textarea id="role_memo" name="role_memo" style="width:300px;;height:50px;" onblur="checkInputValue('role_memo',true,900,'角色说明','')"></textarea>		
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('role_table',role_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
