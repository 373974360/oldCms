<%@ page contentType="text/html; charset=utf-8"%>
<%
	String user_id = request.getParameter("user_id");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加注册用户</title>


<link type="text/css" rel="stylesheet" href="/sys/styles/main.css" />
<link type="text/css" rel="stylesheet" href="/sys/styles/sub.css" />
<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/userList.js"></script>
<script type="text/javascript">

var user_id = "<%=user_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(user_id != "" && user_id != "null" && user_id != null)
	{
		var reg_list = UserManRPC.getUserRegisterListByUserID(user_id);
		
		reg_list = List.toJSList(reg_list);
		if(reg_list != null && reg_list.size() > 0)
		{
			defaultBean = reg_list.get(0);
			
			if(defaultBean)
			{
				$("#register_table").autoFill(defaultBean);	
				$("#check_password").val(defaultBean.password);
			}
			$("#addButton").click(updateRegister);
		}else
		{
			$("#addButton").click(insertRegister);
		}
	}	
});

function checkRegInfo(reg_bean)
{
	$("#register_table input[type=text]").each(function(){
		$(this).blur();
	});
	$("#register_table input[type=password]").each(function(){
		$(this).blur();
	});

	if(!val.getResult()){		
		return false;
	}

	if($("#username").val().length < 2)
	{
		val.showError("username","帐号名称不能少于2个字符");
		return false;
	}
	if($("#password").val().length < 6)
	{
		val.showError("password","密码不能少于6个字符");
		return false;
	}
	if($("#password").val() != $("#check_password").val())
	{
		val.showError("password","两次输入的密码不一致");
		return false;
	}

	if(UserManRPC.registerISExist(reg_bean.username,reg_bean.register_id))
	{
		top.msgWargin(WCMLang.register_iSExist);
		return false;
	}
	return true;
}

function insertRegister()
{
	var reg_bean = BeanUtil.getCopy(UserRegisterBean);
	$("#register_table").autoBind(reg_bean);
	if(!checkRegInfo(reg_bean))
	{
		return;
	}	

	if(UserManRPC.insertRegister(reg_bean))
	{
		top.msgAlert("帐号信息"+WCMLang.Add_success);
		top.CloseModalWindow();
	}else
	{
		top.msgWargin("帐号信息"+WCMLang.Add_fail);
	}
	
}

function updateRegister()
{
	var reg_bean = BeanUtil.getCopy(UserRegisterBean);
	$("#register_table").autoBind(reg_bean);
	if(!checkRegInfo(reg_bean))
	{
		return;
	}	

	if(UserManRPC.updateRegister(reg_bean))
	{
		top.msgAlert("帐号信息"+WCMLang.Add_success);
		top.CloseModalWindow();
	}else
	{
		top.msgWargin("帐号信息"+WCMLang.Add_fail);
	}
} 
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="register_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>帐号名称：</th>
			<td >
				<input id="username" name="username" type="text" class="width200" value="" onblur="checkInputValue('username',false,20,'帐号名称','')" />最少2个字符
				<input type="hidden" id="register_id" name="register_id" value="0">
				<input type="hidden" id="user_id" name="user_id" value="<%=user_id%>">
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>密码：</th>
			<td >
				<input id="password" name="password" type="password" class="width200" value="" onblur="checkInputValue('password',false,30,'密码','')"/>请输入6-16位字符
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>密码确认：</th>
			<td >
				<input id="check_password" name="check_password" type="password" class="width200" value=""/>
			</td>
		</tr>
		<tr>
			<th>帐号状态：</th>
			<td >
				<input id="register_status" name="register_status" type="radio" value="0"/><label>启用</label>
				<input id="register_status" name="register_status" type="radio" value="1"/><label>停用</label>
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('register_table',user_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
