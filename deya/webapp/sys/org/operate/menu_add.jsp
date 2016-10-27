<%@ page contentType="text/html; charset=utf-8"%>
<%
	String parent_id = request.getParameter("parentID");
	String menu_id = request.getParameter("menu_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/menuList.js"></script>
<script type="text/javascript">

var parent_id = "<%=parent_id%>";
var menu_id = "<%=menu_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(menu_id != "" && menu_id != "null" && menu_id != null)
	{		
		defaultBean = MenuRPC.getMenuBean(menu_id);
		if(defaultBean)
		{
			$("#menu_table").autoFill(defaultBean);					
		}
		$("#addButton").click(updateMenu);
	}
	else
	{
		$("#addButton").click(addMenu);
	}
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="menu_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>菜单名称：</th>
			<td class="width250">
				<input id="menu_name" name="menu_name" type="text" class="width300" value="" onblur="checkInputValue('menu_name',false,80,'菜单名称','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>权限ID：</th>
			<td class="width250">
				<input id="opt_id" name="opt_id" type="text" class="width300" value="0" onblur="checkInputValue('opt_id',false,10,'权限ID','checkInt')"/>
			</td>			
		</tr>
		<tr>
			<th>菜单级别：</th>
			<td class="width250">
				<select id="menu_level" name="menu_level" class="width305" value="0"></select>
			</td>			
		</tr>
		<tr>
			<th>执行函数：</th>
			<td class="width250">
				<input id="handls" name="handls" type="text" class="width300" value="" onblur="checkInputValue('handls',true,100,'执行函数','')"/>
			</td>			
		</tr>
		<tr>
			<th>链接地址：</th>
			<td class="width250">
				<input id="menu_url" name="menu_url" type="text" class="width300" value="" onblur="checkInputValue('menu_url',true,100,'链接地址','')"/>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">菜单说明：</th>
			<td colspan="3">
				<textarea id="menu_memo" name="menu_memo" style="width:300px;;height:50px;" onblur="checkInputValue('menu_memo',true,900,'菜单说明','')"></textarea>		
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('menu_table',menu_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
