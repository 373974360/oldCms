<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String type = request.getParameter("type");
	String deptlevel_id = request.getParameter("deptlevel_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/deptLevelList.js"></script>
<script type="text/javascript">

var type = "<%=type%>";
var deptlevel_id = "<%=deptlevel_id%>";
var val = new Validator();
var addBean = BeanUtil.getCopy(DeptLevelBean);
var defaultBean;

$(document).ready(function(){
	
	initButtomStyle();
	init_input();

	if(type == "update")
	{
		defaultBean = DeptRPC.getDeptLevelBeanByID(deptlevel_id);
		if(defaultBean)
		{
			$("#level_table").autoFill(defaultBean);
		}
		$("#deptlevel_value").attr("disabled","true");
		$("#addButton").click(updateDeptLevel);
		$("#levelAddReset").unbind("click");
		$("#levelAddReset").click(resetTable);
	}
	else
	{
		$("#addButton").click(addDeptLevel);
	}
});

// 更新部门级别函数
function updateDeptLevel()
{
	$("#level_table").autoBind(addBean);
	addBean.deptlevel_id = defaultBean.deptlevel_id;
	
	if(!DeptRPC.updateDeptLevel(addBean))
	{
		top.msgWargin("用户级别"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("用户级别"+WCMLang.Add_success);
	top.getCurrentFrameObj().reloadDeptLevelList();
	top.CloseModalWindow();
}

// 新建部门级别函数
function addDeptLevel()
{
	if(!checkDeptLevel())
	{
		return;
	}
	$("#level_table").autoBind(addBean);
	
	if(!DeptRPC.insertDeptLevel(addBean))
	{
		top.msgWargin("用户级别"+WCMLang.Add_fail);
		return;
	}
	top.msgAlert("用户级别"+WCMLang.Add_success);
	top.getCurrentFrameObj().reloadDeptLevelList();
	top.CloseModalWindow();
}

// 修改状态下的重置
function resetTable()
{
	$("#level_table").autoFill(defaultBean);	
	$("#level_table").autoBind(addBean);
}

function checkDeptLevel()
{
	isFocus = true;
	val.check_result = true;
	
	$("#deptlevel_name").blur();
	$("#deptlevel_value").blur();
	$("#deptlevel_memo").blur();
	
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
			<th><span class="f_red">*</span>部门级别名称:</th>
			<td >
				<input id="deptlevel_name" name="deptlevel_name" 
					type="text" class="width300" value="" onblur="checkInputValue('deptlevel_name',false,80,'部门级别名称','')"/>
			</td>			
		</tr>
		<tr >
			<th><span class="f_red">*</span>部门级别数值:</th>
			<td >
				<input id="deptlevel_value" name="deptlevel_value" 
					type="text" class="width300" value="" maxlength="4" onkeypress="checkNumberKey()" onblur="checkInputValue('deptlevel_value',false,4,'部门级别数值','')"/>
			</td>			
		</tr>
		<tr>
			<th style="vertical-align:top;">备注:</th>
			<td class="width250">
				<textarea id="deptlevel_memo" name="deptlevel_memo" 
					style="width:300px;;height:80px;" onblur="checkInputValue('deptlevel_memo',true,900,'部门级别备注','')"></textarea>		
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
			<input id="levelAddReset" name="btn1" type="button" onclick="formReSet('level_table',deptlevel_name)" value="重置" />	
			<input id="levelAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
