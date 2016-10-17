<%@ page contentType="text/html; charset=utf-8"%>
<%
	String model_id = request.getParameter("model_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内容模型字段管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/fieldList.js"></script>
<script type="text/javascript">

var model_id = "<%=model_id%>";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadFieldList();
});


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td valign="middle" id="dept_search" class="search_td fromTabs" >
				<input id="btn1" name="btn1" type="button" onclick="openAddFieldPage();" value="添加字段" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'field_id','openUpdateFieldPage()');" value="修改" />
				<input id="btn2" name="btn2" type="button" onclick="deleteRecord(table,'field_id','deleteModelField()');" value="删除" />
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
			<input id="btn1" name="btn1" type="button" onclick="openAddFieldPage();" value="添加字段" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'field_id','openUpdateFieldPage()');" value="修改" />
			<input id="btn2" name="btn2" type="button" onclick="deleteRecord(table,'field_id','deleteModelField()');" value="删除" />
		</td>
	</tr>
   </table>	
</div>
</body>
</html>