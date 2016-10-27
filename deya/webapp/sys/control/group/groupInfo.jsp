<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>站群配置</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/groupInfo.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	initButtomStyle();
	init_input();
	initData();
});

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="sgroup_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>站群ID：</th>
			<td colspan="3">
				<input id="sgroup_id" name="sgroup_id" type="text" class="width585" value="" disabled="disabled"/>
			</td>
		</tr> 
		<tr>
			<th><span class="f_red">*</span>站群名称：</th>
			<td colspan="3">
				<input id="sgroup_name" name="sgroup_name" type="text" class="width585" value="" onblur="checkInputValue('sgroup_name',false,240,'站群名称','')"/>
			</td>
		</tr>	 
		<tr>
			<th><span class="f_red">*</span>站群服务器IP：</th>
			<td colspan="3">
				<input id="sgroup_ip" name="sgroup_ip" type="text" class="width585" value="" onblur="checkInputValue('sgroup_ip',false,240,'站群服务器IP','checkIP')"/>
			</td>
		</tr>	
		<tr>
			<th>远程服务端口：</th>
			<td colspan="3">
				<input id="sgroup_prot" name="sgroup_prot" type="text" class="width585" value="" />
			</td>
		</tr>
		<tr>
			<th>备注：</th>
			<td colspan="3">
				<textarea id="sgroup_memo" name="sgroup_memo" style="width:585px;;height:50px;" onblur="checkInputValue('sgroup_memo',true,900,'备注','')"></textarea>
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
			<input id="addButton" name="btn1" type="button" onclick="funOK()" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('sgroup_table','1')" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="window.history.go(-1)" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
