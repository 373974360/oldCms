<%@ page contentType="text/html; charset=utf-8"%>
<%
String app_id = request.getParameter("app");
if(app_id == null){
	app_id = "0";
}

String site_id = request.getParameter("site_id");
if(site_id == null){
	site_id = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>邮件设置</title>
		
		
		<jsp:include page="../../../include/include_tools.jsp"/>		
		<script type="text/javascript" src="js/mail.js">
</script>
		<script type="text/javascript">

var site_id = "<%=site_id%>";
var app_id = "<%=app_id%>";

$(document).ready(function(){
	initButtomStyle();
	init_input();
	initMailConfig();
	$("#addButton").click(saveMailConfig);
});

</script>
	</head>
	<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<input type="hidden" name="group" id="group" value="sendmail" />
	<table id="mail_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<th><span class="f_red">*</span>SMTP 服务器：</th>
				<td><input type="text" name="smtp_server"  id="smtp_server" value="smtp.cicro.com"	class="width200"/></td>
			</tr>
			<tr>
				<th>SMTP 端口：</th>
				<td><input id="smtp_port" name="smtp_port" type="text" class="width200" value="25" /></td>
			</tr>
			<tr>
				<th>SMTP 身份验证：</th>
				<td><input type="radio"  name="smtp_is_auth" id="f" value="0"/><label for="f">否</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" checked="checked" name="smtp_is_auth" id="t" value="1"/><label for="t">是（默认）</label></td>
			</tr>
			<tr>
				<th>SMTP 用户名</th>
				<td><input type="text" name="smtp_loginname"  id="smtp_loginname"  class="width200"/></td>
			</tr>
			<tr>
				<th>SMTP 密码：</th>
				<td><input type="password" name="smtp_password"  id="smtp_password" class="width200"/></td>
			</tr>
			<tr>
				<th>发件人：</th>
				<td><input type="text" name="smtp_send_mail"  id="smtp_send_mail" class="width200"/></td>
			</tr>
		</tbody>
	</table>
	<span class="blank12"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<table class="table_option" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td align="left" valign="middle" style="text-indent: 100px;">
				<input id="addButton" name="btn1" type="button" onclick=""
					value="保存" />
			</td>
		</tr>
	</table>
	<span class="blank3"></span>
</form>
		
	</body>
</html>