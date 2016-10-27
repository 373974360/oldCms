<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>服务器注册</title>


<jsp:include page="../../include/include_tools.jsp" />

<script type="text/javascript" src="js/serverList.js"></script>
<script type="text/javascript">

$(document).ready(function(){	
	initButtomStyle();
	init_FromTabsStyle();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	initTable();
	reloadServerDataList();
});

</script>
</head>

<body>
<div>
<div id="table"></div>
<div id="turn"></div>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" valign="middle">
				<input id="btn1" name="btn1" type="button" onclick="openAddPage()" value="新建服务器" />
				<input id="btn2" name="btn2" type="button" onclick="updateRecord(table,'server_id','openUpdatePage()');" value="修改" />
			    <input id="btn3" name="btn3" type="button" onclick="deleteRecord(table,'server_id','funDelete()');" value="删除" />
			</td>
		</tr>
	</table>
</div>
</body>
</html>
