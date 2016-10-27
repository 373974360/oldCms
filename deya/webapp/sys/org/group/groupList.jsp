<%@ page contentType="text/html; charset=utf-8"%>
<%
	String appId = request.getParameter("appId");
	String siteId = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户组管理</title>


<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/groupList.js"></script>
<script type="text/javascript">
	var appId = "<%=appId%>";
	var siteId = "<%=siteId%>";

$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	// 区分应用ID，节点ID 调试中，暂时注释掉
	initPageType(); 
	initTable();
	reloadGroupList();
	
	if(siteId != "")
	{
		//如果是站点或节点下的，选择站点或节点内的人员
		$("input[id='selectUser']").each(function(){
			$(this).click(function(){
				publicSelectSinglCheckbox(table,'group_id',"openSelectOnlySiteUserPage('选择用户','saveGroupUser',siteId)");
			});
		});
		$("#selectRole").hide();
	}
	else
	{
		$(".setOperate").hide();

		$("input[id='selectUser']").each(function(){
			$(this).click(function(){
				publicSelectSinglCheckbox(table,'group_id',"openSelectUserPage('选择用户','saveGroupUser')");
			});
		});
	}	

	$(":button").click(function(){
		is_button_click = true;	
	});
});

// 添加AppID，siteId
function initPageType()
{
	if(appId != "" && appId != null &&appId != "null")
	{
		con_m.put("con_appid",appId);
	}
	if(siteId != "" && siteId != null && siteId != "null")
	{
		con_m.put("con_site_id",siteId);
	}
}

function setUserOperate()
{
	reloadGroupList();
}
</script>
</head>
<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>	
		<td align="left" valign="middle" id="dept_search" class="search_td fromTabs" >
			<input id="btnAddGruop" name="btnAddGroup" type="button" onclick="addGroup()" value="新建用户组" />
				<input id="btnUpdateGruop" 
					name="btnUpdateGroup" type="button" onclick="updateRecord( table, 'group_id', 'updateGroup()')" value="修改" />
				<input id="setOperate" name="btnSetGruopRole" type="button" class="setOperate"
					onclick="publicSelectSinglCheckbox(table,'group_id','openSetOperate(\'权限管理\',\'setUserOperate\',appId,siteId,1)')" value="权限管理" />
					
				<input id="btnDeleteGruop" 
					name="btnDeleteGroup" type="button" onclick="deleteRecord( table, 'group_id', 'deleteGroup()')" value="删除组" /><span class="blank3"></span>
		</td>		
	</tr>
</table>
<span class="blank3"></span>
</div>
<div id="group_table"></div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btnAddGruop" name="btnAddGroup" type="button" onclick="addGroup()" value="新建用户组" />
				<input id="btnUpdateGruop" 
					name="btnUpdateGroup" type="button" onclick="updateRecord( table, 'group_id', 'updateGroup()')" value="修改" />
				<input id="selectUser" name="btnManaUser" type="button" 
					onclick="publicSelectSinglCheckbox(table,'group_id','openSelectUserPage(\'选择用户\',\'saveGroupUser\')')" value="关联用户" />
				<input id="selectRole" name="btnSetGruopRole" type="button" 
					onclick="publicSelectSinglCheckbox(table,'group_id','openSelectRolePage(\'选择角色\',\'saveGroupRole\',appId,siteId)')" value="关联角色" />	
				<input id="setOperate" name="btnSetGruopRole" type="button"  class="setOperate"
					onclick="publicSelectSinglCheckbox(table,'group_id','openSetOperate(\'权限管理\',\'setUserOperate\',appId,siteId,1)')" value="权限管理" />
				<input id="btnDeleteGruop" 
					name="btnDeleteGroup" type="button" onclick="deleteRecord( table, 'group_id', 'deleteGroup()')" value="删除组" />
			</td>
		</tr>
	</table>
</div>
</body>
</html>