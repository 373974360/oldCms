<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//CN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>部门级别管理</title>


<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/deptLevelList.js"></script>
<script type="text/javascript">

$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initTable();
	reloadDeptLevelList();
});

</script>
</head>
<body>
<div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>	
		<td align="left" valign="middle" id="dept_search" class="search_td fromTabs" >
			<input id="btnAddLevel" name="btnAddLevel" type="button" onclick="addDeptLevel()" value="新建部门级别" />
			<input id="btnUpdateLevel" 
				name="btnUpdateLevel" type="button" onclick="updateRecord( table, 'deptlevel_id', 'updateLevel()')" value="修改" />
					
			<input id="btnDeleteLevel" 
				name="btnDeleteLevel" type="button" onclick="deleteRecord( table, 'deptlevel_id', 'deleteLevel()')" value="删除" /><span class="blank3"></span>
		</td>		
	</tr>
</table>
<span class="blank3"></span>
</div>
<div id="level_table"></div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btnAddLevel" name="btnAddLevel" type="button" onclick="addDeptLevel()" value="新建部门级别" />
				<input id="btnUpdateLevel" 
					name="btnUpdateLevel" type="button" onclick="updateRecord( table, 'deptlevel_id', 'updateLevel()')" value="修改" />
					
				<input id="btnDeleteLevel" 
					name="btnDeleteLevel" type="button" onclick="deleteRecord( table, 'deptlevel_id', 'deleteLevel()')" value="删除" />
			</td>
		</tr>
	</table>
</div>
</body>
</html>