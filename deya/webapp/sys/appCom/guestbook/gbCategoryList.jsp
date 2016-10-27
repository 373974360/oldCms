<%@ page contentType="text/html; charset=utf-8"%>
<%
	String app_id = request.getParameter("app");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>留言分类</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript" src="js/gbCategoryList.js"></script>
<script type="text/javascript">

var app_id = "<%=app_id%>";
var site_id = "<%=site_id%>";
if(site_id == null || site_id == "null")
	site_id = "";

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	initTable();
	reloadList();		
});



</script>
</head>

<body>
<div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
		<tr>		
			<td class="fromTabs">
				<input id="btn1" name="btn1" type="button" onclick="openAddGuestBookCategoryPage();" value="新建分类" />
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'cat_id','openUpdateGuestBookCategoryPage()');" value="修改" />
				<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'cat_id','publishGuestBookCategory(1)')" value="发布" />
				<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'cat_id','publishGuestBookCategory(0)')" value="撤消" />
				<input id="btn3" name="btn3" type="button" onclick="sortGuestBookCategory()" value="保存排序" />
				<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'cat_id','deleteGuestBookCategory()');" value="删除" />	
				<span class="blank3"></span>
			</td>
			<td align="right" valign="middle" id="dept_search" class="search_td fromTabs" >				
				
			</td>		
		</tr>
	</table>
	<span class="blank3"></span>
	<div id="table"></div><!-- 列表DIV -->
	<div id="turn"></div><!-- 翻页DIV -->
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle">
			<input id="btn1" name="btn1" type="button" onclick="openAddGuestBookCategoryPage();" value="新建分类" />
			<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'cat_id','openUpdateGuestBookCategoryPage()');" value="修改" />
			<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'cat_id','publishGuestBookCategory(1)')" value="发布" />
			<input id="btn1" name="btn1" type="button" onclick="publicSelectCheckbox(table,'cat_id','publishGuestBookCategory(0)')" value="撤消" />
			<input id="btn3" name="btn3" type="button" onclick="sortGuestBookCategory()" value="保存排序" />
			<input id="btn3" name="btn3" type="button" onclick="deleteSinglRecord(table,'cat_id','deleteGuestBookCategory()');" value="删除" />			
		</td>
	</tr>
   </table>	
</div>
</body>
</html>