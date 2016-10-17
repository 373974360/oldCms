<%@ page contentType="text/html; charset=utf-8"%>
<%
	String wf_id = request.getParameter("wf_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作流维护</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/workFlowList.js"></script>
<script type="text/javascript">

var wf_id = "<%=wf_id%>";
var defaultBean;
var current_obj;
var role_map = new Map();
var step_list = new List();

$(document).ready(function(){
	initButtomStyle();
	init_input();
	getRoleMap();

	if(wf_id != "" && wf_id != "null" && wf_id != null)
	{		
		defaultBean = WorkFlowRPC.getWorkFlowBean(wf_id);
		if(defaultBean)
		{
			$("#workflow_table").autoFill(defaultBean);	
			step_list = defaultBean.workFlowStep_list;
			step_list = List.toJSList(step_list);
			setStepList(0);
		}
		disabledWidget();
	}
	
});

function setStepList(handl_flag)
{
	if(step_list != null && step_list.size() > 0)
	{
		for(var i=0;i<step_list.size();i++)
		{
			if(handl_flag == 0)
				addStep();
			$("#wf_step_table tr").eq(i).find("#step_name"+i).val(step_list.get(i).step_name);
			$("#wf_step_table tr").eq(i).find("#wf_role_id").val(step_list.get(i).role_id);
			$("#wf_step_table tr").eq(i).find("#wf_role_name"+i).val(getRoleNameByRoleID(step_list.get(i).role_id))
		}
	}
}

//得到所有角色信息
function getRoleMap()
{
	var RoleRPC = jsonrpc.RoleRPC;
	role_map = RoleRPC.getRoleMap();
	role_map = Map.toJSMap(role_map);
}

function addStep()
{	
	var str = '<tr><th style="width:45px">第<span id="c_index">'+($("#wf_step_table tr").length+1)+'</span>步：</th><td><input id="step_name'+$("#wf_step_table tr").length+'" name="step_name'+$("#wf_step_table tr").length+'" type="text" class="width100" value="步骤名称"/>&nbsp;<input id="wf_role_name'+$("#wf_step_table tr").length+'" name="wf_role_name'+$("#wf_step_table tr").length+'" type="text" class="width300" value="" readOnly="true"/><input type="hidden" id="wf_role_id"></td></tr>';
	$("#wf_step_table").append(str);
	init_input();
	initButtomStyle();
}

function deleteTd(obj)
{
	$(obj).parent().parent().remove();
	$("#wf_step_table tr #c_index").each(function(i){
		$(this).text(i+1);
	})
}

//打开角色选择窗口
function selectRole(obj)
{
	current_obj = obj;
	openSelectRolePage("选择角色","setRoleIDInInput","cms","");	
}


function setRoleIDInInput(role_ids)
{
	var role_names = getRoleNameByRoleID(role_ids);
	
	$(current_obj).parent().find("input[readOnly]").val(role_names);
	$(current_obj).parent().find("#wf_role_id").val(role_ids);	
}

function getRoleNameByRoleID(role_ids)
{
	var role_names = "";
	if(role_ids != null && role_ids != "")
	{
		var tempA = role_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			if(tempA[i] != "" && role_map.get(tempA[i]) != null)
				role_names += ","+role_map.get(tempA[i]).role_name;
		}
		role_names = role_names.substring(1);
	}
	return role_names;
}

//得到当前步骤的角色ID
function getCurrentSelectedRoleID()
{
	return $(current_obj).parent().find("#wf_role_id").val();
}



</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="workflow_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>工作流名称：</th>
			<td>
				<input id="wf_name" name="wf_name" type="text" class="width300" value="" onblur="checkInputValue('wf_name',false,240,'工作流名称','')"/>
			</td>			
		</tr>
		<tr>
			<th>工作流描述：</th>
			<td>				
				<textarea id="wf_memo" name="wf_memo" style="width:300px;height:50px" onblur="checkInputValue('wf_memo',true,900,'工作流描述','')"></textarea>
			</td>			
		</tr>
		<tr>
			<th>步骤：</th>
			<td>				
				<table id="wf_step_table" class="table_form" border="0" cellpadding="0" cellspacing="0" >
				 
				</table>
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
			<input id="userAddButton" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
