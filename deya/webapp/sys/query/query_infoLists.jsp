<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<title>自定义查询数据管理</title>
<meta name="generator" content="cicro-Builder"/>
<meta name="author" content="cicro"/>
<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src="js/queryItem.js"></script>
<script type="text/javascript">
var site_id ="<%=request.getParameter("site_id")%>";
var conf_id ="<%=request.getParameter("conf_id")%>";
$(document).ready(function(){
	initButtomStyle();
	init_input();
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	//得到业务列表
	initTable();
	reloadList();
});
</script>
</head>
<body>
<span class="blank6"></span>
<div>
	<table id="" class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddPage(site_id,conf_id);" value="添加"/>
				<input id="btn1" name="btn1" type="button" onclick="openUpdatePage(site_id,conf_id)" value="修改"/>
				<input id="btn3" name="btn3" type="button" onclick="deleteQueryItems(site_id,conf_id)" value="删除"/>
				<input id="btn4" name="btn4" type="button" onclick="deleteItemsByConfId(conf_id)" value="清除全部数据"/>
				<input id="btnCancel" name="btn1" type="button" onclick="window.history.go(-1)" value="返回"/>
			</td>
		</tr>
	</table>
	<span class="blank3"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>
	<table id="" class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddPage(site_id,conf_id);" value="添加"/>
				<input id="btn1" name="btn1" type="button" onclick="openUpdatePage(site_id,conf_id)" value="修改"/>
				<input id="btn3" name="btn3" type="button" onclick="deleteQueryItems(site_id,conf_id)" value="删除"/>
				<input id="btn4" name="btn4" type="button" onclick="deleteItemsByConfId(conf_id)" value="清除全部数据"/>
				<input id="btnCancel" name="btn1" type="button" onclick="window.history.go(-1)" value="返回"/>
			</td>
		</tr>
	</table>
</div>
</body>
</html>