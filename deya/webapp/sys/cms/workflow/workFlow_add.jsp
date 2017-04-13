<%@ page contentType="text/html; charset=utf-8"%>
<%
	String wf_id = request.getParameter("wf_id");
	String app_id = request.getParameter("app_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作流修改</title>


<jsp:include page="../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/workFlowList.js"></script>
<script type="text/javascript">
var app_id = "<%=app_id%>";
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
		$("#addButton").click(updateWorkFlow);
	}
	else
	{
		$("#addButton").click(addWorkFlow);
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
			$("#wf_step_table tr").eq(i).find("#wf_role_name"+i).val(getRoleNameByRoleID(step_list.get(i).role_id));
            if(step_list.get(i).required == 1)
            {
                $("#wf_step_table tr").eq(i).find("#required"+i).attr("checked","checked");
            }
            else
            {
                $("#wf_step_table tr").eq(i).find("#required"+i).removeAttr("checked");
            }

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
	var str = '<tr><th style="width:45px">第<span id="c_index">'+($("#wf_step_table tr").length+1)+'</span>步：</th><td><input id="step_name'+$("#wf_step_table tr").length+'" name="step_name'+$("#wf_step_table tr").length+'" type="text" class="width100" value="步骤名称" maxlength="40"/>&nbsp;<input id="wf_role_name'+$("#wf_step_table tr").length+'" name="wf_role_name'+$("#wf_step_table tr").length+'" type="text" class="width300" value="" readOnly="true"/><input type="hidden" id="wf_role_id">&nbsp;<input id="userAddCancel" name="btn1" type="button" onclick="selectRole(this)" value="关联角色" />&nbsp;<input id="userAddCancel" name="btn1" type="button" onclick="deleteTd(this)" value="删除" />&nbsp;&nbsp;<input type="checkbox" value="1"  id="required'+$("#wf_step_table tr").length +'" checked="checked"/>是否必选</td></tr>';
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
	openSelectRolePage("选择角色","setRoleIDInInput",app_id,"");	
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
				<input id="userAddCancel" name="btn1" type="button" onclick="addStep();" value="添加步骤" />	
			</td>			
		</tr>
		<tr>
			<th></th>
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('workflow_table',wf_id);setStepList(1);" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="locationWorkFlow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
