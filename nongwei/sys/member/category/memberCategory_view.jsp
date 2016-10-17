<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String mcat_id = request.getParameter("mcat_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>û</title>

<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript">
	var mcat_id = "<%=mcat_id%>";
	var viewbean;
	var MemberManRPC = jsonrpc.MemberManRPC;
	
$(document).ready(function(){

	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initGroupView();
	disabledWidget();
});

function initGroupView()
{
	viewbean = MemberManRPC.getMemberCategoryByID(mcat_id);
	$("#cate_table").autoFill(viewbean);
}

</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="cate_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th>会员分类名称：</th>
			<td >
				<input id="mcat_name" name="mcat_name" type="text" class="width300" value="" />
			</td>
		</tr>
		<tr>
			<th>排序：</th>
			<td >
				<input id="sort_id" name="sort_id" type="text" class="width300" value="" />
			</td>
		</tr>
		<tr>
			<th>会员分类描述：</th>
			<td >
				<textarea id="mcat_memo" name="mcat_memo" style="width:300px;;height:80px;"></textarea>
			</td>
		</tr>
	</tbody>
</table>
<span class="blank12"></span>
<div class="line2h"></div>
<span class="blank3"></span>
<table class="table_option" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="middle" style="text-indent:100px;">
			<input id="viewCancel" name="btn1" type="button" onclick="top.CloseModalWindow()" value="关闭" />
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>

</body>
</html>
