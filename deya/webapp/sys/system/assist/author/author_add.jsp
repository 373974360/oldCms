<%@ page contentType="text/html; charset=utf-8"%>
<%
	String author_id = request.getParameter("author_id");
	String app_id = request.getParameter("app");
	String site_id = request.getParameter("site_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>作者维护</title>


<jsp:include page="../../../include/include_tools.jsp"/>

<script type="text/javascript" src="js/author.js"></script>
<script type="text/javascript">

var author_id = "<%=author_id%>";
var defaultBean;

$(document).ready(function(){
	initButtomStyle();
	init_input();

	if(author_id != "" && author_id != "null" && author_id != null)
	{		
		defaultBean = AssistRPC.getAuthorById(author_id);
		if(defaultBean)
		{
			$("#Author_table").autoFill(defaultBean);					
		}
		$("#author_id").val(author_id);
		$("#addButton").click(updateAuthorData);
	}
	else
	{
		$("#app_id").val("<%=app_id%>");
		$("#site_id").val("<%=site_id%>");
		$("#addButton").click(addAuthorData);
	}
});


</script>
</head>

<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="Author_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>姓名：</th>
			<td class="width250">
				<input id="author_name" name="author_name" type="text" class="width200" value="" onblur="checkInputValue('author_name',false,60,'姓名','')"/>
				<input type="hidden" name="author_id" id="author_id" value="0" />
			</td>
		</tr>
		<tr>
			<th>首字母：</th>
			<td >
				<input id="author_initial" name="author_initial" type="text" class="width200" value=""/>
			</td>
		</tr>	
		<tr>
			<th>URL：</th>
			<td >
				<input id="author_url" name="author_url" type="text" class="width200" value=""/>
				<input type="hidden" name="app_id" id="app_id" value="0"/>
				<input type="hidden" name="site_id" id="site_id" value="0"/>
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
			<input id="addButton" name="btn1" type="button" onclick="" value="保存" />	
			<input id="userAddReset" name="btn1" type="button" onclick="formReSet('Author_table',author_id)" value="重置" />	
			<input id="userAddCancel" name="btn1" type="button" onclick="closePage();" value="取消" />	
		</td>
	</tr>
</table>
<span class="blank3"></span>
</form>
</body>
</html>
