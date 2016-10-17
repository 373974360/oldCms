<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>站群维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/groupList.js"></script>
<script type="text/javascript">

var parent_id = "${param.parentID}";
var sgroup_id = "${param.sgroup_id}"; 
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(sgroup_id != "" && sgroup_id != "null" && sgroup_id != null)
	{	
		defaultBean = SiteGroupRPC.getSGroupBeanByID(sgroup_id);
		if(defaultBean)
		{
			$("#group_table").autoFill(defaultBean);					
		}
		$("#addButton").click(updateGroup);
		
	}
	else
	{
		$("#addButton").click(addGroup);
	}
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="group_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
   <input id="parent_id" name="parent_id" type="hidden" class="width300" value="${param.parentID}" />
	<tbody>
		<tr>
			<th><span class="f_red">*</span>站群ID：</th>
			<td class="width250">
				<input id="sgroup_id" name="sgroup_id" type="text" class="width300" value="" onblur="checkInputValue('sgroup_id',false,5,'站群ID','checkLower')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>站群名称：</th>
			<td class="width250">
				<input id="sgroup_name" name="sgroup_name" type="text" class="width300" value="" onblur="checkInputValue('sgroup_name',false,80,'站群名称','')"/>
			</td>			
		</tr>
		<tr>
			<th><span class="f_red">*</span>站群服务器IP：</th>
			<td class="width250">
				<input id="sgroup_ip" name="sgroup_ip" type="text" class="width300" value="" onblur="checkInputValue('sgroup_ip',false,30,'站群服务器IP','checkIP')"/>
			</td>			
		</tr>
		<tr>
			<th>站群服务器端口：</th>
			<td class="width250">
				<input id="sgroup_prot" name="sgroup_prot" type="text" class="width300" value="" maxlength="6" onkeypress="var k=event.keyCode; return k>=48&&k<=57" onpaste="return !clipboardData.getData('text').match(/\D/)" ondragenter="return false" style="ime-mode:Disabled"/>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">备注：</th>
			<td colspan="3">
				<textarea id="sgroup_memo" name="sgroup_memo" style="width:300px;;height:50px;" onblur="checkInputValue('sgroup_memo',true,900,'备注','')"></textarea>		
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('group_table',sgroup_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
