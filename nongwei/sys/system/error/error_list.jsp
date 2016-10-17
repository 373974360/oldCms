<%@ page contentType="text/html; charset=utf-8"%>
<%
	String appId = request.getParameter("appId");
	String siteId = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>纠错管理</title>


<jsp:include page="../../include/include_tools.jsp" />
  
<script type="text/javascript" src="js/error_list.js"></script>
<script type="text/javascript">
    
	var appId = "<%=appId%>";
	var siteId = "<%=siteId%>";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadList();
	
});

function changeStatus()
{

	con_m.remove("err_state");
	//setCon_m();
	var searchStatus = $("#searchStatus").val();
	//alert(searchStatus);
	if(searchStatus!='5'){
		con_m.put("err_state",searchStatus);
	}else{
		con_m.remove("err_state");
	}
	reloadList();
}

</script>
</head>

<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >	
	<tr>
		<td align="left" valign="middle" class="search_td fromTabs">
		<label>请选择处理状态：</label>
			<select id="searchStatus" class="input_select width75" onchange="changeStatus()">
				<option selected="selected" value="5">全部</option>
				<option value="1">未处理</option>
				<option value="2">不予处理</option>
				<option value="3">已处理</option>
			</select>  
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
				<input id="btn301" name="btn3" type="button" onclick="deleteRecord(table,'id','deleteFun()');" value="删除" />
				<input id="btn304" name="btn4" type="button" onclick="updateRecord(table,'id','operateFun()');" value="操作" />
				<input id="btn305" name="btn5" type="button" onclick="updateRecord(table,'id','viewFun()');" value="查看" />
			</td>
		</tr>
	</table>
</div>
</body>
</html>
