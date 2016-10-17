<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>接收配置</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/receiveConfList.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	reloadBefore();
	initButtomStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initTable();
	reloadList();
	reloadAfter();
});

</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="openAddReceiveConfigPage();" value="添加" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'site_id','openUpdateReceiveConfigPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'site_id','publishReceiveConfig(0)')" value="启用" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'site_id','publishReceiveConfig(-1)')" value="停用" />
				<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'site_id','deleteReceiveConfig()');" value="删除" />	
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				<span class="blank3"></span>
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	<div id="turn"></div><!-- 翻页DIV -->
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="openAddReceiveConfigPage();" value="添加" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'site_id','openUpdateReceiveConfigPage()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'site_id','publishReceiveConfig(0)')" value="启用" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'site_id','publishReceiveConfig(-1)')" value="停用" />
			<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'site_id','deleteReceiveConfig()');" value="删除" />
		</td>
	</tr>
   </table>	
</div>
</body>
</html>