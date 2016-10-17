<%@ page contentType="text/html; charset=utf-8"%>
<%	
	String app_id = request.getParameter("app_id");
	String site_id = request.getParameter("site_id");
	String handl_name = request.getParameter("handl_name");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择部门</title>


<link rel="stylesheet" type="text/css" href="../../styles/themes/default/tree.css">
<link rel="stylesheet" type="text/css" href="../../styles/themes/icon.css">
<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript">

var json_data;
var handl_name = "<%=handl_name%>";
var app_id = "<%=app_id%>";
var site_id = "<%=site_id%>";
if(site_id == "null")
	site_id = "";

$(document).ready(function(){
	initButtomStyle();
	getRoleList();
	getSelectedRoleID();
});

function getRoleList()
{
	var RoleRPC = jsonrpc.RoleRPC;
	var role_list = RoleRPC.getRoleListByAPPAndSite(app_id,site_id);
	role_list = List.toJSList(role_list);
	if(role_list != null && role_list.size() > 0)
	{
		for(var i=0;i<role_list.size();i++)
		{
			if(role_list.get(i) != null)
				$("#role_list").append('<li style="float:none;height:20px"><input type="checkbox" id="role_id_checkbox" value="'+role_list.get(i).role_id+'"><label>'+role_list.get(i).role_name+'</label></li>');
		}
	}
	init_input();
}

function getSelectedRoleID()
{
	var role_ids = top.getCurrentFrameObj().getCurrentSelectedRoleID();
	
	if(role_ids != "" && role_ids != null)
	{
		var tempA = role_ids.split(",");
		for(var i=0;i<tempA.length;i++)
		{
			$(":checkbox[value="+tempA[i]+"]").attr("checked",true);
		}
	}
}

function returnRoleID()
{
	var role_ids = "";	
	$(":checkbox[checked]").each(function(){
		role_ids += ","+$(this).val();
	});
	if(role_ids != "")
		role_ids = role_ids.substring(1);

	eval("top.getCurrentFrameObj()."+handl_name+"('"+role_ids+"')");
	top.CloseModalWindow();
}

</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="table" class="table_form" border="0" cellpadding="0" cellspacing="0" style="width:94%">
	<tbody>
		<tr>			
			<td align="center" >
				<div style="width:100%;height:395px;overflow:auto;background:#FFFFFF;">
					<ul id="role_list" style="margin:10px">
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
			<input id="addButton" name="btn1" type="button" onclick="returnRoleID()" value="保存" />
			<input id="userAddCancel" name="btn1" type="button" onclick="top.CloseModalWindow();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>