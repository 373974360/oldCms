<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>sql执行器</title>


<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src="js/sql.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
$(document).ready(function () {				
	initButtomStyle();
	init_input();
}); 	
//-->
</SCRIPT>	
</head>

<body>
<div id="">
<table class="table_option" border="0" cellpadding="0" cellspacing="0" >
	<tr>
		<td class="fromTabs">
			<input id="btn1" name="btn1" type="button" onclick="executeSearchSql();" value="查询" />
			<input id="btn1" name="btn1" type="button" onclick="executeSearchSql();" value="更新" />
			<span class="blank3"></span>
		</td>
	</tr>
</table>
<table id="" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>			
		<tr>			
			<td colspan="3">
				<textarea id="sql" name="sql" style="width:98%;height:80px;" ></textarea>		
			</td>
		</tr>
	</tbody>
</table>
<table id="result_talbe" class="table_border odd_even_list" border="0" cellpadding="0" cellspacing="0">
	<thead>
	  <tr id="result_title_tr"></tr>
	<thead>
	<tbody id="result_tbody">
	</tbody>
</table>
</div>
<span class="blank12"></span>
</body>
</html>
