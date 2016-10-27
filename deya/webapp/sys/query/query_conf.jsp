<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>自定义查询业务</title>


<jsp:include page="../include/include_tools.jsp"/>
<script type="text/javascript" src="js/queryConf.js"></script>
<script type="text/javascript">
var site_id ="<%=request.getParameter("site_id")%>";
$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 

	//得到业务列表
	initTable();
	reloadList();	
});
</script>
</head>
<body>
<div>
	<table id="" class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddPage(site_id);" value="新建业务"/>
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'conf_id','openUpdatePage(site_id)');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'conf_id','deleteQueryConf()');" value="删除" />			
			</td>
		</tr>
	</table>
	<span class="blank3"></span>
	<div class="line2h"></div>
	<span class="blank3"></span>
	<div id="table"></div>
	<div id="turn"></div>
	<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddPage(site_id);" value="新建业务"/>
				<input id="btn1" name="btn1" type="button" onclick="updateRecord(table,'conf_id','openUpdatePage(site_id)');" value="修改" />
				<input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'conf_id','deleteQueryConf()');" value="删除" />			
			</td>
		</tr>
   </table>	
</div>
</body>
</html>
