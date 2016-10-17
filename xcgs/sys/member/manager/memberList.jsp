<%@ page contentType="text/html; charset=utf-8"%>
<%
	String appId = request.getParameter("appId");
	String siteId = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员管理</title>


<jsp:include page="../../include/include_tools.jsp" />

<script type="text/javascript" src="js/memberList.js"></script>
<script type="text/javascript">

	var appId = "<%=appId%>";
	var siteId = "<%=siteId%>";
	
	con_m.put("sort_name","me_id");
	con_m.put("sort_type","desc");
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initPageType();
	initTable();
	reloadMemberList();
	
});

// 分应用，站点
function initPageType()
{
	if(appId != "" && appId != null &&appId != "null")
	{
		con_m.put("con_appid",appId);
	}
	if(siteId != "" && siteId != null && siteId != "null")
	{
		con_m.put("con_siteid",siteId);
	}
}

function changeStatus()
{
	con_m.remove("me_status");
	con_m.remove("search_value");
	con_m.remove("search_name");
	var status = $(searchStatus).val();
	if(status == "normal")
	{
		con_m.put("me_status","1");
	}
	else if(status == "uncheck")
	{
		con_m.put("me_status","0");
	}
	else if(status == "forbidden")
	{
		con_m.put("me_status","-1");
	}
	else
	{}
	reloadMemberList();
}

</script>
</head>

<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >	
	<tr>
		<td align="left" valign="middle" class="search_td fromTabs">
		<label>请选择会员状态：</label>
			<select id="searchStatus" class="input_select width75" onchange="changeStatus()">
				<option selected="selected" value="All">全部</option>
				<option value="normal">正常</option>
				<option value="uncheck">待审</option>
				<option value="forbidden">禁用</option>
			</select>
			<span class="blank3"></span>
		</td>	
		<td align="right" valign="middle" id="dept_search" class="search_td fromTabs">
			<select id="searchFields" class="input_select width75">
				<option selected="selected" value="me_nickname">昵称</option>
				<option value="me_account">登录名</option>
				<option value="me_realname">真实姓名</option>
			</select>
			<input id="searchkey" type="text" class="input_text" value=""  /><input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="memberSearch(this)"/>		
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
				<!-- <input id="btn1" name="btn1" type="button" onclick="addMember()" value="添加会员" /> -->
				<input id="btn2" name="btn6" type="button" onclick="updateRecord( table, 'me_id', 'updateStatus()')" value="修改会员状态" />
				<input id="btn4" name="btn4" type="button" onclick="updateRecord( table, 'me_id', 'updateMember()')" value="修改" />
				<input id="btn301" name="btn3" type="button" onclick="deleteRecord(table,'me_id','deleteMember()');" value="删除" />
			</td>
		</tr>
	</table>
</div>
</body>
</html>
