<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String type = request.getParameter("type");
	String userlevel_id = request.getParameter("userlevel_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/userLevelList.js"></script>
<script type="text/javascript">

var type = "<%=type%>";
var userlevel_id = "<%=userlevel_id%>";
var val = new Validator();
var addBean = BeanUtil.getCopy(UserLevelBean);
var defaultBean;

$(document).ready(function(){
	
	initButtomStyle();
	init_input();

	if(type == "update")
	{
		defaultBean = UserManRPC.getUserLevelBeanByID(userlevel_id);
		if(defaultBean)
		{
			$("#level_table").autoFill(defaultBean);
		}
		$("#userlevel_value").attr("disabled","true");
		$("#addButton").click(updateUserLevel);
		$("#levelAddReset").unbind("click");
		$("#levelAddReset").click(resetTable);
	}
	else
	{
		$("#addButton").click(addUserLevel);
	}
});

// 更新用户级别函数
function updateUserLevel()
{
	$("#level_table").autoBind(addBean);
	addBean.userlevel_id = defaultBean.userlevel_id;
	if(!UserManRPC.updateUserLevel(addBean))
	{
		top.msgWargin("用户级别"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("用户级别"+WCMLang.Add_success);
	top.getCurrentFrameObj().reloaduserLevelList();
	top.CloseModalWindow();
}

// 新建用户级别函数
function addUserLevel()
{	
	if(!checkUserLevel())
	{
		return;
	}
	
	if(UserManRPC.getUserLevelCountByLevel(0,$("#userlevel_value").val()))
	{				
		jQuery.simpleTips("userlevel_value","该级别值已存在，请重新设置",2000);
		return;
	}	
	

	$("#level_table").autoBind(addBean);
	if(!UserManRPC.insertUserLevel(addBean))
	{
		top.msgWargin("用户级别"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("用户级别"+WCMLang.Add_success);
	top.getCurrentFrameObj().reloaduserLevelList();
	top.CloseModalWindow();
}

// 修改状态下的重置
function resetTable()
{
	$("#level_table").autoFill(defaultBean);	
}

function checkUserLevel()
{
	isFocus = true;
	val.check_result = true;
	
	$("#userlevel_name").blur();
	$("#userlevel_value").blur();
	$("#userlevel_memo").blur();
	
	if(!val.getResult())
	{
		return false;
	}
	return true;
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="level_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>用户级别名称:</th>
			<td >
				<input id="userlevel_name" name="userlevel_name" 
					type="text" class="width300" value="" onblur="checkInputValue('userlevel_name',false,80,'用户级别名称','')"/>
			</td>			
		</tr>
		<tr >
			<th><span class="f_red">*</span>用户级别数值:</th>
			<td >
				<input id="userlevel_value" name="userlevel_value" onkeypress="checkNumberKey()" maxlength="4"
					type="text" class="width300" value="" onblur="checkInputValue('userlevel_value',false,4,'用户级别数值','')"/>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">备注:</th>
			<td class="width250">
				<textarea id="userlevel_memo" name="userlevel_memo" 
					style="width:300px;;height:80px;" onblur="checkInputValue('userlevel_memo',true,900,'用户级别备注','')"></textarea>		
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
			<input id="addButton" name="btn1" type="submit" onclick="" value="保存" />	
			<input id="levelAddReset" name="btn1" type="button" onclick="formReSet('level_table',userlevel_name)" value="重置" />	
			<input id="levelAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
