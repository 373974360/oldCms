<%@ page contentType="text/html; charset=utf-8"%>
<%
String cid = request.getParameter("cat_id");
String siteid = request.getParameter("site_id");
String app_id = request.getParameter("app_id");
if(app_id == null || app_id.trim().equals("")){
	app_id = "cms";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息管理</title>
<link type="text/css" rel="stylesheet" href="../../../styles/themes/default/tree.css" />
<link type="text/css" rel="stylesheet" href="../../../styles/sq.css" />


<jsp:include page="../../../include/include_tools.jsp"/>
<script type="text/javascript" src="../../../js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../../js/indexjs/tools.js"></script>
<script type="text/javascript" src="js/info.js"></script>

<script type="text/javascript">

var site_id = "<%=siteid%>";
var app = "<%=app_id%>";
var cid = "<%=cid%>";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	initTable();
	reloadInfoDataList();	
});


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td align="left" valign="middle" id="dept_search" class="search_td fromTabs" >
				<input id="btn1" name="btn1" type="button" onclick="openAddInfoPage();" value="添加" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'info_id','openUpdateInfoDataPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />			
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btn3" name="btn3" type="button" onclick="publishInfo()" value="发布" />
				<input id="btn3" name="btn3" type="button" onclick="cancleInfo()" value="撤销" />
				<input id="btn3" name="btn3" type="button" onclick="backInfo()" value="归档" />
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>		
			<td align="left" valign="middle" id="dept_search" class="search_td fromTabs" >
				<input id="btn1" name="btn1" type="button" onclick="openAddInfoPage();" value="添加" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'info_id','openUpdateInfoDataPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'info_id','deleteInfoData()');" value="删除" />			
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="btn3" name="btn3" type="button" onclick="publishInfo()" value="发布" />
				<input id="btn3" name="btn3" type="button" onclick="cancleInfo()" value="撤销" />
				<input id="btn3" name="btn3" type="button" onclick="backInfo()" value="归档" />
				<span class="blank3"></span>
			</td>		
		</tr>
   </table>	
</div>
</body>
</html>