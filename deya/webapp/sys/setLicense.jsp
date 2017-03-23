<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.deya.license.server.GetServerInfo"%>
<%
	String type = request.getParameter("type"); 
    String result = "";
	if(type != null && !"".equals(type.trim()))
	{
		result = "请输入正确的SERVER KEY";
	}
   String code = GetServerInfo.getServerInfoCodeByEncrypt();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>政务信息管理平台</title>
<meta http-equiv="X-UA-Compatible" content="IE=8" />


<link rel="stylesheet" type="text/css" href="styles/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="styles/themes/icon.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/java.js"></script>
<script type="text/javascript" src="js/extend.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/jquery.c.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/validator.js"></script>
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/jquery.easyui.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	//$("#sysname").html('<img alt="" src="images/login_logo.gif" />');
	$("#sysname").html('门户网站管理系统');

	if ($.browser.msie && ($.browser.version == "6.0") && !$.support.style)
	{ 
		$('#dd').show();
		$('#dd').dialog();
		//$("#browserSpan").html("检测到浏览器版本为IE6.0，为了更好的管理后台，建议升级到IE7以上版本，<a href='http://windows.microsoft.com/zh-CN/internet-explorer/downloads/ie'>IE下载地址</a>。");
	}else{
		$('#dd').hide();
	}

});

function setCode(){
	$("#form1").submit();
}
  function jsCopy(content){ 
   window.clipboardData.setData("text",content);
        alert("复制成功！");  
   } 
</script>
<style>
body {font-family: Tahoma, Geneva, sans-serif,"\5FAE\8F6F\96C5\9ED1","\5B8B\4F53","\534E\6587\9ED1\4F53",Arial Narrow,arial,serif;background:#C0C0C0;font-size:12px;}
body,div,ul,ol,form,input,textarea{padding:0; margin:0; color:#2b2b2b;}
table,td,tr,th{font-size:12px;}
li{list-style-type:none;list-style:none;}
table{ margin:0 auto;border-spacing:0; border-collapse:collapse;}
img{border:0;}
ol,ul {list-style:none;}
.red{ border:1px solid red;}
.blue{ border:1px solid blue;}
a{color:#CDE7FF; text-decoration:none;}

#sysname{ color:#FFF; line-height:27px; font-size:20px; padding-left:1px; letter-spacing:2px; font-family:"黑体"; font-weight:bold;}

.bodyDiv{ width:890px; height:391px; margin:0 auto; background:url(images/login_bg.png) no-repeat;}

.note{ margin:0px;}
.note li{ line-height:22px; color:#CDE7FF; list-style-image:url(images/login_dot.gif);}

.areaLeft{width:448px; height:96px; float:left; background:url(images/login_vline.gif) right repeat-y;}
.areaRight{width:420px; height:96px; float:right; text-align:left;}

.loginForm{ color:#FFFFFF;}
.loginForm tr{line-height:30px;}
.loginForm th{text-align:right; font-size:13px; font-weight:bold;letter-spacing:3px;}
.loginForm td{text-align:left; padding:3px 0px;  vertical-align:middle;line-height:30px;  }

.loginInput{ width:210px; height:21px; border:1px solid #6797BD; font-size:14px; line-height:21px; font-weight:bold; padding-left:4px;letter-spacing:1px;}

.btn{ width:65px; height:30px;}
.copyright{ font-size:12px; color:#DCEDFD; text-align:center;}
.hidden{display:none;}
</style>
</head>
<body>
<div id="dd" style="padding:5px;width:320px;height:180px;color:#fff;display:none" title="温馨提示" >
		<div style="font-size:13px;text-algin:left;line-height:24px;padding:10px;">
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;检测到浏览器版本为IE6.0，为了更好的管理后台和操作，建议升级到IE7以上版本,<a href='http://download.microsoft.com/download/1/6/1/16174D37-73C1-4F76-A305-902E9D32BAC9/IE8-WindowsXP-x86-CHS.exe' target="_blank"><font style="color:red;">IE下载安装</font></a>,谢谢!
		</div>
</div>
<form id="form1" name="form1" method="post" action="login.jsp" >
<div style="height:80px;"></div>
<div class="bodyDiv">
<table width="100%" class="" border="0" cellpadding="0" cellspacing="0">
	<tr style="height:25px;">
		<td>&nbsp;
			
		</td>
	</tr>
	<tr style="height:63px;">
		<td style="text-indent:55px;" valign="middle">
			<div style="width:460px; float:left;">
				<div id="sysname"><img alt="" src="images/login_logo.gif" /></div>
                <div><img alt="" src="images/login_logo_en.gif" /></div>
			</div>
		</td>
	</tr>
	<tr style="height:24px">
		<td style="padding-left:40px">&nbsp;<font color="red"><%=result%></font></td>
	</tr>
	<tr>
		<td>
<table class="loginForm" width="100%" cellpadding="0" border="0" cellspacing="0">
<tr>
	<th width="200">SERVWER CODE：</th>
	<td width="700"><input id="code" name="code" type="text" class="loginInput" style="width:575px" value="<%=code%>" />
	</td>
	<td width="12"></td>
</tr>
<tr> 
	<th></th>
	<td>请把上面生成的SERVER CODE发到公司申请产品KEY</td>
	<td></td>
</tr>
<tr> 
	<th>SERVER KEY：</th>
	<td><textarea rows="4" cols="70" name="key"></textarea> </td>
	<td></td>
</tr>
<tr> 
	<th></th>
	<td>请输入公司返回的SERVER KEY，并点击下面的注册按钮</td>
	<td></td>
</tr>
</table>

</td>
	</tr>
	<tr>
		<td align="left" style="">
			<table border="0" width="100%">
				<tr>
					<td width="300"></td>
					<td>
			<input id="btnSubmit" name="btnSubmit" type="button" onclick="setCode()" class="btn" value="注册" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr style="height:24px;">
		<td></td>
	</tr>
	<tr>
		<td class="copyright">Copyright © 2002-2014 Dyt Technology Co., Ltd.. All rights reserved. 软件 版权所有</td>
	</tr>
</table>
<br />
<div id="browserSpan"></div>
</div>
</form>
<iframe id="submitFrame" name="submitFrame" src="" frameborder="0" width="0" height="0"></iframe>
</body>
</html>
