<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//CN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>专题分类管理</title>


<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/ztCateList.js"></script>
<script type="text/javascript">
var site_id = "<%=site_id%>";

$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initTable();
	reloadZTCateList();
});

</script>
</head>
<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>	
		<td align="left" valign="middle" id="dept_search" class="search_td fromTabs" >
			<input id="btnAddCateClass" name="btnAddCateClass" type="button" onclick="addZTCate()" value="新建专题分类" />
			<input id="btnUpdateCateClass" 
				name="btnUpdateCateClass" type="button" onclick="updateRecord( table, 'id', 'updateZTCate()')" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'id','sortCategory()');" value="保存排序" />		
			<input id="btnDeleteCateClass" 
				name="btnDeleteCateClass" type="button" onclick="deleteRecord( table, 'id', 'deleteZTCate()')" value="删除" /><span class="blank3"></span>
		</td>		
	</tr>
</table>
<span class="blank3"></span>
</div>
<div id="cateClass_table"></div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>	
		<td align="left" valign="middle" id="dept_search" class="search_td" >
			<input id="btnAddCateClass" name="btnAddCateClass" type="button" onclick="addZTCate()" value="新建专题分类" />
			<input id="btnUpdateCateClass" 
				name="btnUpdateCateClass" type="button" onclick="updateRecord( table, 'id', 'updateZTCate()')" value="修改" />
			<input id="btn3" name="btn3" type="button" onclick="sortRecord(table,'id','sortCategory()');" value="保存排序" />		
			<input id="btnDeleteCateClass" 
				name="btnDeleteCateClass" type="button" onclick="publicSelectSinglCheckbox( table, 'id', 'deleteZTCate()')" value="删除" /><span class="blank3"></span>
		</td>		
	</tr>
</table>
</div>
</body>
</html>