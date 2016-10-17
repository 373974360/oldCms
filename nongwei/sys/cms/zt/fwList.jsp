<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服务目录管理</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/fwList.js"></script>
<script type="text/javascript">
var serrch_cat_id = "";
var app_id = "ggfw";
var site_id = "ggfw";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initTable();
	reloadZTList();		
});

</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="openAddCategoryPage();" value="新建分类" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','openUpdateCategoryPage()');" value="修改" />
				<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'id','updateArchiveStatus(1)');" value="归档" />
				<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'id','updateArchiveStatus(0)');" value="取消归档" />
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >
				<select id="searchFields" class="input_select width70" >
					<option selected="selected" value="cat_cname">名称</option>
				</select>
				<input id="searchkey" type="text" class="input_text" value=""  /><input id="btnSearch" type="button" class="btn x2" value="搜索" onclick="searchHandl(this)"/>
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
			<input id="btn1" name="btn1" type="button" onclick="openAddCategoryPage();" value="新建分类" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'id','openUpdateCategoryPage()');" value="修改" />
			<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'id','updateArchiveStatus(1)');" value="归档" />
			<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'id','updateArchiveStatus(0)');" value="取消归档" />
			<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'cat_id','deleteCategory()');" value="删除" />
		</td>
	</tr>
   </table>	
</div>
</body>
</html>