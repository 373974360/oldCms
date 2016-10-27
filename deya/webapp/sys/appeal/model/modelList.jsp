<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/modelList.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadModelList();	
});
</script>
</head>
<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>	
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="openAddModelPage();" value="新建业务" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'model_id','openUpdateModelPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'model_id','openSelectSQUserPage(\'选择用户\',\'saveModelUser\')')" value="关联用户" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'model_id','deleteModel()');" value="删除" />
				<input id="btn4" name="brn4" type="button" onclick="publicSelectCheckbox(table,'model_id','getexportDataId()')" value="信件导出" />
			    <input id="btn5" name="brn5" type="button" onclick="publicSelectCheckbox(table,'model_id','getbatchIsNotOpenID()')" value="批量不公开" />		
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
			<input id="btn1" name="btn1" type="button" onclick="openAddModelPage();" value="新建业务" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'model_id','openUpdateModelPage()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectSinglCheckbox(table,'model_id','openSelectSQUserPage(\'选择用户\',\'saveModelUser\')')" value="关联用户" />
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'model_id','deleteModel()');" value="删除" />	
			<input id="btn4" name="brn4" type="button" onclick="publicSelectCheckbox(table,'model_id','getexportDataId()')" value="信件导出" />
			<input id="btn5" name="brn5" type="button" onclick="publicSelectCheckbox(table,'model_id','getbatchIsNotOpenID()')" value="批量不公开" />		
		</td>
	</tr>
   </table>	
</div>
</body>
</html>
