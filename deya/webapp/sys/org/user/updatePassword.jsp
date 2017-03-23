<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript">

var val=new Validator();
var UserManRPC = jsonrpc.UserManRPC;

$(document).ready(function(){
	initButtomStyle();
	init_input();
});

function updatePassword()
{
	if(!standard_checkInputInfo("register_table"))
	{
		return;
	}
	var old_password = $("#old_password").val();
	var password = $("#password").val();
	var confirm_password = $("#confirm_password").val();

    if(password.length < 8)
    {
        val.showError("password","密码不能少于6个字符");
        return;
    }
    else
    {
        //校验密码级别
        var level = password.replace(/^(?:(?=.{4})(?=.*([a-z])|.)(?=.*([A-Z])|.)(?=.*(\d)|.)(?=.*(\W)|.).*|.*)$/, "$1$2$3$4").length;
        if(level < 2)
        {
            val.showError("password","密码设置太简单，请重新设置，建议8位以上字母+数字，区分大小写！");
            return false;
        }
    }

	if(password != confirm_password)
	{		
		val.showError("password","两次输入密码不一致，请重新输入");
		return;
	}
	if(!UserManRPC.checkUserLogin2(LoginUserBean.user_name,old_password)){
		val.showError("old_password","原始密码不正确，请重新输入");
		return;
	}
	if(UserManRPC.updatePasswordByUserID(LoginUserBean.user_id,password))
	{		
		msgAlert("密码"+WCMLang.Add_success);
		return;
	}else
	{		
		msgWargin("密码"+WCMLang.Add_fail);
		return;
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
			<th><span class="f_red">*</span>原始密码：</th>
			<td colspan="7">
				<input id="old_password" name="old_password" type="password" class="width200" value="" maxlength="30" onblur="checkInputValue('old_password',false,30,'原始密码','')"/>
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>密　　码：</th>
			<td colspan="7">
				<input id="password" name="password" type="password" class="width200" value="" maxlength="30" onblur="checkInputValue('password',false,30,'密码','')"/>请输入8-16位字符
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>确认密码：</th>
			<td colspan="7">
				<input id="confirm_password" name="confirm_password" type="password" class="width200" value="" maxlength="30"/>
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
			<input id="deptAddButton" name="btn1" type="button" onclick="updatePassword()" value="保存" />
			<input id="userAddReset" name="btn1" type="button" onclick="form1.reset()" value="重置" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
