<%@ page contentType="text/html; charset=utf-8"%>
<%
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员管理系统</title>


<jsp:include  page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/memberConfig.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	initButtomStyle();
	init_input();
	init();
	//init_editer_min("reg_pro");
	//init_editer_min("close_pro");
});

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="config_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>会员注册：</th>
			<td >
				<input id="is_allow" name="is_allow" type="radio" value="0" checked="true"/><label>允许</label>
				<input id="is_allow" name="is_allow" type="radio" value="1"/><label>禁止</label>			
			</td>
		</tr>
		<tr >
			<th>注册审核：</th>
			<td colspan="3">
				<input id="is_check" name="is_check" type="radio" value="1"/><label>需要</label>
				<input id="is_check" name="is_check" type="radio" value="0" checked="true"/><label>不需要</label>			
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">注册协议：</th>
			<td colspan="3">
				<textarea id="reg_pro" name="reg_pro" style="width:600px;;height:200px;" ></textarea>			
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">关闭注册时显示：</th>
			<td colspan="3">
				<textarea id="close_pro" name="close_pro" style="width:600px;height:120px;" ></textarea>			
			</td>
		</tr>
		<tr>
			<th style="vertical-align:top;">禁用用户名：</th>
			<td colspan="3">
				<textarea id="forbidden_name" name="forbidden_name" style="width:600px;;height:100px;" ></textarea>			
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
			<input id="config_save" name="btn1" type="button" onclick="savaConfig()" value="保存" />
			<input id="userAddButton" name="btn1" type="button" onclick="init()" value="重置" />
		</td>
		<td align="left" valign="middle" style="text-indent:100px;">
			<!--<input id="userAddButton" name="btn1" type="button" onclick="init()" value="重置" />-->
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
