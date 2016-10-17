<%@ page contentType="text/html; charset=utf-8"%>
<%
	String rtype = request.getParameter("rtype");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>帐号管理</title>


<jsp:include page="../../include/include_tools.jsp" />

<script type="text/javascript" src="js/registerList.js"></script>
<script type="text/javascript">

var rtype = "<%=rtype%>";


$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	setPageStatus(rtype);
	initSearchCondition("con_registerid");
	
	initTable();
	reloadRoleList();
	
});

</script>
</head>

<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		
	<tr>	
		<td align="left" valign="middle" class="search_td fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox( table, 'user_id', 'activitingUserPage()')" value="开通帐号" />
				<input id="btn2" name="btn6" type="button" onclick="publicSelectCheckbox( table, 'user_id', 'stopUserPage()')" value="停用帐号" />
				<input id="btn4" name="btn4" type="button" onclick="updateRecord( table, 'user_id', 'updateUserPage()')" value="维护账号" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord( table, 'user_id', 'delUserPage()')" value="删除" />
			<span class="blank3"></span>
		</td>	
		<td align="right" valign="middle" id="dept_search" class="search_td fromTabs">
			<select id="searchFields" class="input_select width75">
				<option selected="selected" value="con_deptname">部门名称</option>
				<option value="con_registerid">帐号</option>
				<option value="con_realname">真实姓名</option>
			</select>
			<input id="searchkey" type="text" class="input_text" value=""  /><input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="userSearchHandl(this)"/>		
			<span class="blank3"></span>	
		</td>		
	</tr>
</table>
<span class="blank3"></span>

<div id="table"></div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox( table, 'user_id', 'activitingUserPage()')" value="开通帐号" />
				<input id="btn2" name="btn6" type="button" onclick="publicSelectCheckbox( table, 'user_id', 'stopUserPage()')" value="停用帐号" />
				<input id="btn4" name="btn4" type="button" onclick="updateRecord( table, 'user_id', 'updateUserPage()')" value="维护账号" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord( table, 'user_id', 'delUserPage()')" value="删除" />
		<span class="blank3"></span>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
