<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>内容模型管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/modelList.js"></script>
<script type="text/javascript">


$(document).ready(function(){
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadModelList();
	$("#turn span").click(clickTurnEvent);
	$(".system").show();
})


</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td valign="middle" id="dept_search" class="search_td fromTabs" >
			<input id="btn1" name="btn1" type="button" onclick="openAddModelPage();" value="添加内容模型" class="system hidden"/>
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'model_id','openUpdateModelPage()');" value="修改"  class="system hidden"/>	
			<input id="btn2" name="btn6" type="button" onclick="publicSelectCheckbox( table, 'model_id', 'updateModelDisabled(1)')" value="停用" class="system hidden"/>
			<input id="btn2" name="btn6" type="button" onclick="publicSelectCheckbox( table, 'model_id', 'updateModelDisabled(0)')" value="启用" class="system hidden"/>
			<input id="btn303" name="btn3" type="button" onclick="deleteRecord(table,'model_id','deleteInfo()');" value="删除" />
			<input id="btn2" name="btn2" type="button" onclick="saveModelSort();" value="保存排序" />
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
			<input id="btn1" name="btn1" type="button" onclick="openAddModelPage();" value="添加内容模型"  class="system hidden"/>
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'model_id','openUpdateModelPage()');" value="修改"  class="system hidden"/>	
			<input id="btn2" name="btn6" type="button" onclick="publicSelectCheckbox( table, 'model_id', 'updateModelDisabled(1)')" value="停用" class="system hidden"/>
			<input id="btn2" name="btn6" type="button" onclick="publicSelectCheckbox( table, 'model_id', 'updateModelDisabled(0)')" value="启用" class="system hidden"/>
			<input id="btn303" name="btn3" type="button" onclick="deleteRecord(table,'model_id','deleteInfo()');" value="删除" />
			<input id="btn2" name="btn2" type="button" onclick="saveModelSort();" value="保存排序" />
		</td>
	</tr>
   </table>	
</div>
</body>
</html>