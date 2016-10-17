<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/roleList.js"></script>
<script type="text/javascript">

var app_id = "<%=app_id%>";
var site_id = "<%=site_id%>";
if(site_id == null || site_id == "null")
	site_id = "";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadRoleList();	

	if(app_id == "appeal")
	{
		$("input[id='selectUser']").each(function(){
			$(this).click(function(){
				publicSelectSinglCheckbox(table,'role_id',"openSelectSQUserPage('选择用户','saveRoleUser')");
			});
		});
	}else
	{
		if(site_id != "")
		{
			//如果是站点或节点下的，选择站点或节点内的人员
			$("input[id='selectUser']").each(function(){
				$(this).click(function(){
					publicSelectSinglCheckbox(table,'role_id',"openSelectSiteUserPage('选择用户','saveRoleUserGroup',site_id)");
				});
			});
		}
		else
		{
			$("input[id='selectUser']").each(function(){
				$(this).click(function(){
					publicSelectSinglCheckbox(table,'role_id',"openSelectUserPage('选择用户','saveRoleUser')");
				});
			});
		}
	}

	$(":button").click(function(){
		is_button_click = true;	
	});
});



</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="openAddRolePage();" value="新建角色" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'role_id','openUpdateRolePage()');" value="修改" />
				<!-- <input id="selectUser" name="btn3" type="button" onclick="" value="关联用户" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'role_id','openSelectOperatePage(\'选择权限\',\'saveRoleOperate\')')" value="关联权限" /> -->
				<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'role_id','deleteRole()');" value="删除" />	
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >				
				<input id="searchkey" type="text" class="input_text" value=""  /><input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="roleSearchHandl(this)"/>
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	<div id="turn"></div><!-- 翻页DIV -->
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="openAddRolePage();" value="新建角色" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'role_id','openUpdateRolePage()');" value="修改" />
			<!-- <input id="selectUser" name="btn3" type="button" onclick="" value="关联用户" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'role_id','openSelectOperatePage(\'选择权限\',\'saveRoleOperate\')')" value="关联权限" /> -->
			<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'role_id','deleteRole()');" value="删除" />			
		</td>
	</tr>
   </table>	
</div>
</body>
</html>