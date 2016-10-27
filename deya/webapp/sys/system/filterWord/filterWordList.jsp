<%@ page contentType="text/html; charset=utf-8"%>
<%
	String site_id = request.getParameter("site_id");
	if(site_id == null){
		site_id = "";
	}
	
	String app_id = request.getParameter("app_id");
	if(app_id == null){
		app_id = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>过滤词管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/filterword.js"></script>
<script type="text/javascript">

var site_id = "<%=site_id%>";
var app_id = "<%=app_id%>"

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	show();	
});


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	  <tr>
		<td class="fromTabs">
			<input id="btn1" name="btn1" type="button" onclick="openAddFilterWordPage(app_id,site_id);" value="新建过滤词" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'filterword_id','openUpdateFilterWordPage()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'filterword_id','deleteFilterWord()');" value="删除" />
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
			<input id="btn1" name="btn1" type="button" onclick="openAddFilterWordPage(app_id,site_id);" value="新建过滤词" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'filterword_id','openUpdateFilterWordPage()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'filterword_id','deleteFilterWord()');" value="删除" />			
		</td>
	  </tr>
    </table>
    <span class="blank3"></span>	
</div>
</body>
</html>