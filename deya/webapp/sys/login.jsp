<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.wcm.server.LicenseCheck,com.deya.util.CryptoTools"%>
<%
    /*
    String key = request.getParameter("key"); 
	if(key != null && !"".equals(key.trim()))
	{
		boolean result = LicenseCheck.createLicense(key.trim());
		if(!result){//输入的key不正确
			response.sendRedirect("/sys/setLicense.jsp?type=0");
			return;
		}
	}else{
		if(!com.deya.wcm.startup.ServerListener.isLicenseRight){
	    	response.sendRedirect("/sys/setLicense.jsp");
	    	return;
	    }
	}
    */
	String user_name = request.getParameter("username");
	String pass_word = request.getParameter("password");
	String auth_code = request.getParameter("auth_code");
	if(user_name != null && !"".equals(user_name))
	{
		String res = com.deya.wcm.services.org.user.UserLoginRPC.checkUserLogin(user_name,pass_word,auth_code,request);
		if("auth_code_error".equals(res))
		{
			out.println("<script>alert(\"验证码不正确\");top.changeCreateImage();</script>");		
			return;
		}
		else
		{
			if(!"0".equals(res))		
			{
				int msg = Integer.parseInt(res);
				switch(msg)
				{
					case 1:out.println("<script>alert(\"该帐号还未开通\");top.changeCreateImage();</script>");break;
					case 2:out.println("<script>alert(\"该用户已被停用\");top.changeCreateImage();</script>");break;
					case 3:out.println("<script>alert(\"用户名密码不正确\");top.changeCreateImage();</script>");break;//用户名不正确
					case 4:out.println("<script>alert(\"用户名密码不正确\");top.changeCreateImage();</script>");break;//用户名密码不正确
					case 5:out.println("<script>alert(\"该用户不存在\");top.changeCreateImage();</script>");break;//
					default:out.println("<script>alert(\"用户名密码不正确\");top.changeCreateImage();</script>");break;
				}
				
				return;
			}else
			{
				out.println("<script>top.location.href = 'index.jsp';</script>");
			}
		}
	}
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>政务信息管理平台-登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<link rel="stylesheet" type="text/css" href="styles/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/java.js"></script>
<script type="text/javascript" src="js/extend.js"></script>
<script type="text/javascript" src="js/jquery.c.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/validator.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){

	$("#sysname").html('政务信息管理平台');

	if ($.browser.msie && ($.browser.version == "6.0") && !$.support.style)
	{ 
		$('#dd').show();
		$('#dd').dialog();
	}else{
		$('#dd').hide();
	}
        $("#username").focus();	
   
	//2分钟更新一下验证码，以免session失效
	setInterval(function(){
		changeCreateImage();
	},1000*120) ;
});
function loginWCM()
{
	if($.trim($("#username").val())=="")
	{
		alert("用户名不能为空！");
		$("#username").focus();
		return;	
	}
	if($.trim($("#password").val())=="")
	{
		alert("密码不能为空！");
		$("#password").focus();
		return;	
	}
	if($.trim($("#auth_code").val())=="")
	{
		alert("验证码不能为空！");
		$("#auth_code").focus();
		return;	
	}

	$("#form1").submit();
}

function changeCreateImage()
{
	$('#img').attr('src','createImage.jsp?'+Math.random());
}
</script>
<style>
body
{
		font-family: "\5FAE\8F6F\96C5\9ED1", "\5B8B\4F53", "\534E\6587\9ED1\4F53", Arial Narrow, arial, serif;
		background: #dddddd;
		font-size: 14px;
		
}
html, body { height: 100% }
body, div, ul, ol, form, input, textarea
{
		padding: 0;
		margin: 0;
		color: #2b2b2b;
}
table, td, tr, th { font-size: 14px; }
li
{
		list-style-type: none;
		list-style: none;
}
table
{
		margin: 0 auto;
		border-spacing: 0;
		border-collapse: collapse;
}
img { border: 0; }
ol, ul { list-style: none; }
.red { border: 1px solid red; }
.blue { border: 1px solid blue; }
a
{
		color: #454545;
		text-decoration: none;
}
input { font-family: "\5FAE\8F6F\96C5\9ED1", "\5B8B\4F53", "\534E\6587\9ED1\4F53" }
.main
{
		width: 100%;
		height: 100%;
		margin: 0 auto;
}
.mbox
{
		margin: 0 auto;
		width: 100%;
		min-height: 600px;
		height: auto;
		background: url(images/bgi.png)  no-repeat center center;
}
.mbox .logo
{
		background: url(images/logo.png) no-repeat center center;
		width: 224px;
		height: 450px;
		margin:0 auto;
		padding-bottom:20px;
				overflow:hidden;
}
.mbox .login{ height:45px; line-height:45px;background:#b63100;  width:100%; margin:0 auto; text-align:center; padding-top:15px; }

.mbox .login .tp{ width:1000px; margin:0 auto;height:26px; line-height:26px; padding:0 15px; }

.tp_name{color:#ffffff; font-size:14px;padding-left: 15px;}
.left{float:left; line-height:26px; height:26px;vertical-align:middle;}
.mbox .login .tp a{color:#ffffff;}
input,img a{vertical-align:middle; magrin-right:10px;}


.loginInput
{
		width: 110px;height: 24px;line-height: 24px;border: 1px solid #ededed;font-size: 14px;
		padding-left: 4px;
		letter-spacing: 0px;
		font-weight: bold;
}



.btn
{
		width: 65px;
		height: 26px; line-height:26px; border:none;
		background: #7c2100;
		margin:0 15px;
		color:#ffffff;
}

.hidden { display: none; }


.note { margin: 0px; }
.note li
{
		line-height: 22px;
		color: #CDE7FF;
		list-style-image: url(images/login_dot.gif);
}

.loginForm { color: #454545; }
</style>
</head>
<body>
<div id="dd" style="padding:5px;width:320px;height:180px;color:#fff;display:none" title="温馨提示" >
	<div style="font-size:13px;text-algin:left;line-height:24px;padding:10px;"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;检测到浏览器版本为IE6.0，为了更好的管理后台和操作，建议升级到IE7以上版本,<a href='http://download.microsoft.com/download/1/6/1/16174D37-73C1-4F76-A305-902E9D32BAC9/IE8-WindowsXP-x86-CHS.exe' target="_blank"><font style="color:red;">IE下载安装</font></a>,谢谢! </div>
</div>
<div class="main">
	<div class="mbox">
		<div class="logo"></div>
		
			<div class="login">
				<div class="tp">
					<form id="form1" name="form1" method="post" action="login.jsp" target="submitFrame">
						<span class="left tp_name" style="padding-left:202px;">用户名：</span>
						<span class="left"><input id="username" name="username" type="text" class="loginInput" maxlength="20" value="" /></span>
						<span class="left tp_name">密码：</span>
						<span class="left"><input id="password" name="password" type="password" class="loginInput"  maxlength="20" value="" /></span>
						<span class="left tp_name">验证码：</span>
						<span class="left "><input id="auth_code" name="auth_code" type="text" class="loginInput" style="width:52px;" maxlength="6" value="" />&nbsp;&nbsp;<img id="img" width="60" height="26" src="createImage.jsp"  style=" height:26px; line-height:26px; overflow:hidden;vertical-align:middle;"/><!--&nbsp;&nbsp;<a href="javascript:changeCreateImage()">看不清，换一个</a>--></span>
						<span class="left"><input id="btnSubmit" name="btnSubmit" type="submit" onClick="loginWCM()" class="btn" value="登 录" />
						
					</form>
				</div>
			</div>
		
		<iframe id="submitFrame" name="submitFrame" src="" frameborder="0" width="0" height="0"></iframe>
	</div>
</div>
</body>
</html>
