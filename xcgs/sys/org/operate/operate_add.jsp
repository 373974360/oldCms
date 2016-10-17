<%@ page contentType="text/html; charset=utf-8"%>
<%
	String parent_id = request.getParameter("parentID");
	String opt_id = request.getParameter("opt_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/operateList.js"></script>
<script type="text/javascript">

var parent_id = "<%=parent_id%>";
var opt_id = "<%=opt_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();
	//得到所属应用
	getAppInfo();

	if(opt_id != "" && opt_id != "null" && opt_id != null)
	{		
		defaultBean = OperateRPC.getOperateBean(opt_id);
		if(defaultBean)
		{
			$("#opt_table").autoFill(defaultBean);					
		}
		$("#addButton").click(updateOperate);
	}
	else
	{
		$("#addButton").click(addOperate);
	}
});

function getAppInfo()
{
	var appList = OperateRPC.getAppList();
	appList = List.toJSList(appList);
	$("#app_id").addOptions(appList,"app_id","app_name");
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="opt_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>权限名称：</th>
			<td class="width250">
				<input id="opt_name" name="opt_name" type="text" class="width300" value="" onblur="checkInputValue('opt_name',false,80,'权限名称','')"/>
			</td>			
		</tr>	
		<tr>
			<th>所属应用：</th>
			<td >
				<select id="app_id" name="app_id" class="width305"></select>
			</td>			
		</tr>
		<tr>
			<th>控制器：</th>
			<td >
				<input id="controller" name="controller" type="text" class="width300" value="" onblur="checkInputValue('controller',true,100,'控制器','')"/>
			</td>			
		</tr>
		<tr>
			<th>动作：</th>
			<td >
				<input id="action" name="action" type="text" class="width300" value="" onblur="checkInputValue('action',true,100,'动作','')"/>
			</td>			
		</tr>
		<tr>
			<th>权限标识：</th>
			<td >
				<input id="opt_flag" name="opt_flag" type="text" class="width300" value="" onblur="checkInputValue('opt_flag',true,100,'动作','')"/>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">权限说明：</th>
			<td colspan="3">
				<textarea id="opt_memo" name="opt_memo" style="width:300px;;height:50px;" onblur="checkInputValue('opt_memo',true,900,'权限说明','')"></textarea>		
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
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('opt_table',opt_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
