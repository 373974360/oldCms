<%@ page contentType="text/html; charset=utf-8"%>
<%
	String appId = request.getParameter("appId");
	String siteId = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>站点用户管理</title>


<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/SiteUserList.js"></script>
<script type="text/javascript">
	var appId = "<%=appId%>";
	var siteId = "<%=siteId%>";

$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initTable();
	reloadSiteUserList();
});

</script>
</head>
<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle" class="search_td fromTabs">
				<input id="btnManaUser" name="btnManaUser" type="button" 
					onclick="openSelectUserPage('选择用户','saveSiteUser')" value="关联用户" />	
				<!-- <input id="btnSetGruopRole" name="btnSetGruopRole" type="button" 
					onclick="publicSelectSinglCheckbox(table,'user_id','openSetOperate(\'权限管理\',\'setUserOperate\',appId,siteId,0)')" value="权限管理" /> -->	
				<input id="btnDelete" 
					name="btnDelete" type="button" onclick="deleteRecord( table, 'user_id', 'deleteSiteUser()')" value="删除用户" /><span class="blank3"></span>
			</td>
		</tr>
	</table>
<span class="blank3"></span>
</div>
<div id="siteUser_table"></div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">					
				<input id="btnManaUser" name="btnManaUser" type="button" 
					onclick="openSelectUserPage('选择用户','saveSiteUser')" value="关联用户" />	
				<!-- <input id="btnSetGruopRole" name="btnSetGruopRole" type="button" 
					onclick="publicSelectSinglCheckbox(table,'user_id','openSelectRolePage(\'选择角色\',\'saveRoleUser\',appId,siteId)')" value="关联角色" /> -->
				<!-- <input id="btnSetGruopRole" name="btnSetGruopRole" type="button" 
					onclick="publicSelectSinglCheckbox(table,'user_id','openSetOperate(\'权限管理\',\'setUserOperate\',appId,siteId,0)')" value="权限管理" /> -->
				<input id="btnDelete" 
					name="btnDelete" type="button" onclick="deleteRecord( table, 'user_id', 'deleteSiteUser()')" value="删除用户" />
			</td>
		</tr>
	</table>
</div>
</body>
</html>