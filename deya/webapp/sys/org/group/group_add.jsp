<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String appId = request.getParameter("appId");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加用户组</title>

<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/groupList.js"></script>
<script type="text/javascript">
	var appId = "<%=appId%>";
	var site_id = "<%=site_id%>";
	var val = new Validator();
	var group_id = "";

$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
});

function checkSave()
{
	val.check_result = true;
	$("#group_name").blur();
	
	if(!val.getResult()){		
		return;
	}
	savaGroup();
}

</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="addGroup_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>用户组名称：</th>
			<td >
				<input id="group_name" name="group_name" type="text" style="width:260px;" value="" 
						onblur="checkInputValue('group_name',false,83,'用户组名称','')" />
				<input type="hidden" id="app_id" name="app_id" value="<%=appId%>">
				<input type="hidden" id="site_id" name="site_id" value="<%=site_id%>">
			</td>
		</tr>
		<tr>
			<th>用户组说明：</th>
			<td >
				<textarea id="group_memo" name="group_memo" style="width:260px;;height:80px;"></textarea>
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
			<input id="addButton" name="btn1" type="submit" onclick="checkSave()" value="保存" />	
			<input id="userAddReset" name="btn2" type="button" onclick="formReSet('addGroup_table',group_id)" value="重置" />	
			<input id="userAddCancel" name="btn3" type="button" onclick="top.CloseModalWindow();" value="取消" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>

</body>
</html>
