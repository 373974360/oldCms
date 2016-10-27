<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>维护配置</title>


<jsp:include page="../../include/include_tools.jsp"/>
<script type="text/javascript">


$(document).ready(function(){
	initButtomStyle();
	init_input();
});

function setSiteControl()
{	
	jsonrpc.ConfigRPC.setGroupWebPageGreyNoGrey();
	top.msgAlert("站群置灰切换成功");
}
</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="config_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>		
		<tr>
			<th>网站群置灰：</th>
			<td>
				
			</td>
			<td><input id="addButton" name="btn1" type="button" value="保存" onclick="setSiteControl()"/>	</td>
		</tr>
	</tbody>
</table>
</form>
</body>
</html>
