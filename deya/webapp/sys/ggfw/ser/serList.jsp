<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>场景式服务主题管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/serList.js"></script>
<script type="text/javascript">
var serrch_cat_id = "";
var app_id = "ggfw";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadSerList();		
});

</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="openAddSerCategoryPage();" value="新建主题" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'ser_id','openUpdateSerCategoryPage()');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'ser_id','batchPublishSerCategory(1)')" value="发布" />	
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'ser_id','batchPublishSerCategory(0)')" value="撤销" />	
				<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'ser_id','sortSerCategory()');" value="保存排序" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'ser_id','deleteSerCategory()');" value="删除" />
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
			<input id="btn1" name="btn1" type="button" onclick="openAddSerCategoryPage();" value="新建主题" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'ser_id','openUpdateSerCategoryPage()');" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'ser_id','batchPublishSerCategory(1)')" value="发布" />	
				<input id="btn3" name="btn3" type="button" onclick="publicSelectCheckbox(table,'ser_id','batchPublishSerCategory(0)')" value="撤销" />
			<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'ser_id','sortSerCategory()');" value="保存排序" />
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'ser_id','deleteSerCategory()');" value="删除" />
		</td>
	</tr>
   </table>	
</div>
</body>
</html>