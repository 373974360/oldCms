<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String groupId = request.getParameter("groupId");
	String appID = request.getParameter("appID");
	String siteID = request.getParameter("siteID");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>û</title>

<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/groupList.js"></script>
<script type="text/javascript">
	var groupId = "<%=groupId%>";
	var appID = "<%=appID%>";
	var siteID = "<%=siteID%>";
	var viewbean;
	var groupRole;

$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initGroupView();
	disabledWidget();
});

function initGroupView()
{
	viewbean = GroupManRPC.getGroupBeanByID(groupId);
		$("#group_name").val(viewbean.group_name);
		$("#group_memo").val(viewbean.group_memo);
	
	var userNames = GroupManRPC.getUserNameByGroupID(groupId);
	$("#group_user").val(userNames);
	
	var roleNames = GroupManRPC.getRolesByGroupID(groupId,appID,siteID);
	$("#group_role").val(roleNames);
	
}

</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="addGroup_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>用户组名称:</th>
			<td >
				<input id="group_name" name="group_name" type="text" class="width200" value="" />
			</td>
		</tr>
		<tr>
			<th>用户组说明：</th>
			<td >
				<textarea id="group_memo" name="group_memo" style="width:585px;;height:80px;">
				</textarea>
			</td>
		</tr>
		<tr>
			<th>关联角色：</th>
			<td >
				<textarea id="group_role" name="group_role" style="width:585px;;height:80px;">
				</textarea>
			</td>
		</tr>
		<tr>
			<th>关联用户：</th>
			<td >
				<textarea id="group_user" name="group_user" style="width:585px;;height:80px;">
				</textarea>
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
			<input id="viewCancel" name="btn1" type="button" onclick="top.tab_colseOnclick(top.curTabIndex)" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>

</body>
</html>
