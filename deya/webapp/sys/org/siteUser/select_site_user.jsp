<%@ page contentType="text/html; charset=utf-8"%>
<%
	String handl_name = request.getParameter("handl_name");
	String siteId = request.getParameter("site_id");
	String app_id = request.getParameter("app_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>站点用户管理</title>


<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript">
	var handl_name = "<%=handl_name%>";
	var siteId = "<%=siteId%>";
	var app_id = "<%=app_id%>";

	var SiteUserRPC = jsonrpc.SiteUserRPC;
	var GroupManRPC = jsonrpc.GroupManRPC;
	var user_map = new Map();
	var group_map = new Map();
$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	getGroupList();
	getSiteUserList();

	getSelectedUserIDS();
	getSelectedGroupIDS();
	$("#select_list").show();
});

//得到已选过的用户
function getSelectedUserIDS()
{	
	setIDSINList(top.getCurrentFrameObj().getSelectedUserIDS(),"user_list");
	
}
//得到已选过的用户组
function getSelectedGroupIDS()
{
	setIDSINList(top.getCurrentFrameObj().getSelectedGroupIDS(),"group_list");
}

function setIDSINList(ids,div_list_name)
{
	if(ids != "" && ids != null)
	{
		var tempA = ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{			
			toUserList(true,tempA[i],div_list_name);
			if(div_list_name == "user_list")
			{
				$("#select_list :checkbox[value='"+tempA[i]+"']").attr("checked",true);
			}
		}
	}
}

function changeUserType(val)
{
	if(val == "user")
		getSiteUserList();
	else
		getGroupList();
}

function getSiteUserList()
{
	var userList = SiteUserRPC.getUserBeanBySite(app_id,siteId);
	userList = List.toJSList(userList);
	$("#select_list").empty();
	if(userList != null && userList.size() > 0)
	{
		var s_ids = ","+getSelectUserIDS("user_list")+",";
		for(var i=0;i<userList.size();i++)
		{
			user_map.put(userList.get(i).user_id,userList.get(i).user_realname);
			setValInSelectList(userList.get(i).user_id,userList.get(i).user_realname,s_ids);
		}
	}
	init_select_list("user_list");
}

function getGroupList()
{
	var groupList = GroupManRPC.getGroupListByAppSiteID(app_id,siteId);
	groupList = List.toJSList(groupList);
	$("#select_list").empty();
	if(groupList != null && groupList.size() > 0)
	{
		var s_ids = ","+getSelectUserIDS("group_list")+",";
		for(var i=0;i<groupList.size();i++)
		{
			group_map.put(groupList.get(i).group_id,groupList.get(i).group_name);
			setValInSelectList(groupList.get(i).group_id,groupList.get(i).group_name,s_ids);
		}
	}
	init_select_list("group_list");
}

function setValInSelectList(id,name,s_ids)
{
	var checked_str = '';	
	if(s_ids.indexOf(","+id+",") > -1)
	{
		checked_str = 'checked = "true"';
	}
	$("#select_list").append('<li style="float:none;height:20px"><input type="checkbox" '+checked_str+' id="id" value="'+id+'"><label id="'+id+'">'+name+'</label></li>');	
}

function init_select_list(div_name)
{
	init_input();
	$("label").unbind("click").click(function(){
		var isChecked = $(this).prev().is(':checked') ? false:true;
		$(this).prev().attr("checked",isChecked);		
		toUserList(isChecked,$(this).prev().val(),div_name);
	});

	$("#select_list :checkbox").click(function(){
		toUserList($(this).is(':checked'),$(this).val(),div_name)
	});
}

function toUserList(isChecked,u_id,div_name)
{
	if(isChecked)
	{		
		var name = "";
		if(div_name == "user_list")
			name = user_map.get(u_id);
		else
			name = group_map.get(u_id);
		
		$("#"+div_name).append('<li style="float:none;height:20px" user_id="'+u_id+'">'+name+'<img onclick="deleteUser(this,\''+u_id+'\')" src="../../images/delete.png" width="15" height="15" alt="" align="right"/></li>');	
	}else
	{
		$("#"+div_name+" li[user_id='"+u_id+"']").remove();
	}
}

function deleteUser(obj,ids)
{		
	if($(obj).parent().parent().attr("id").indexOf($("#user_type :selected").val()) > -1)
		$("#select_list input[value='"+ids+"']").removeAttr("checked");

	$(obj).parent().remove();
}

function getSelectUserIDS(div_name)
{
	var ids = "";	
	$("#"+div_name+" li").each(function(i){
		if(i > 0)
			ids += ",";
		ids += $(this).attr("user_id");		
	});	
	return ids;
}

function returnUserID()
{
	eval("top.getCurrentFrameObj()."+handl_name+"('"+getSelectUserIDS("user_list")+"','"+getSelectUserIDS("group_list")+"')");	
	top.CloseModalWindow();
}

</script>
</head>
<body>
<span class="blank3"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
		 <th class="table_form_th">
			<select id="user_type" onchange="changeUserType(this.value)">
				<option value="user">选择用户</option>
				<option value="group">选择用户组</option>
			</select></th>
		 <th class="table_form_th"></th>
		 <th class="table_form_th">已选用户</th>
		</tr>	
		<tr>			
			<td align="center" valign="top" rowspan="3">				
				<div style="width:210px;height:389px;overflow:auto;background:#FFFFFF;text-align:left" class="border_color">
					<ul id="select_list" style="margin:10px" class="hidden">
					</ul>
				</div>
			</td>
			<td class="width10" rowspan="3"></td>
			<td align="center" >				
				<div style="width:230px;height:190px;overflow:auto;background:#FFFFFF;text-align:left" class="border_color">
					<ul id="user_list" style="margin:10px">
					</ul>
				</div>
			</td>
		</tr>
		<tr>
			<th class="table_form_th">已选用户组</th>
		</tr>
		<tr>
			<td>
				<div style="width:230px;height:175px;overflow:auto;background:#FFFFFF;text-align:left" class="border_color">
					<ul id="group_list" style="margin:10px">
					</ul>
				</div>
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
			<input id="addButton" name="btn1" type="button" onclick="returnUserID()" value="保存" />				
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>