<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%
	String class_id = request.getParameter("class_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分类方法信息</title>

<jsp:include page="../../include/include_tools.jsp" />
<script type="text/javascript" src="js/cateClassList.js"></script>
<script type="text/javascript">
	var class_id = "<%=class_id%>";
	var viewbean;
	
$(document).ready(function(){
	getAppList();
	initButtomStyle();
	init_FromTabsStyle();
	init_input();
	
	if($.browser.msie&&$.browser.version=="6.0"&&$("html")[0].scrollHeight>$("html").height()) $("html").css("overflowY","scroll"); 
	
	initGroupView();
	disabledWidget();
	initButtomStyle();
	init_input();
});

function initGroupView()
{
	viewbean = CategoryRPC.getCateClassBeanById(class_id);
		$("#view_table").autoFill(viewbean);
}

</script>
</head>
<body>
<span class="blank12"></span>
<form id="form1" name="form1" action="#" method="post">
<table id="view_table" class="table_form" border="0" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<th><span class="f_red">*</span>中文名称：</th>
			<td >
				<input id="class_cname" name="class_cname" type="text" class="width300" value="" />
			</td>
		</tr>
		<tr>
			<th><span class="f_red">*</span>英文名称：</th>
			<td >
				<input id="class_ename" name="class_ename" type="text" class="width300" value="" />
			</td>
		</tr>
		<tr>
			<th>域代码：</th>
			<td >
				<input id="class_codo" name="class_codo" type="text" class="width300" value="" />
			</td>
		</tr>
		<tr>
			<th>目录分类类型：</th>
			<td >
				<input id="class_type" name="class_type" type="radio" value="1"checked="true" /><label>共享目录</label>
				<input id="class_type" name="class_type" type="radio" value="0" /><label>基础分类法</label>
			</td>
		</tr>
		<tr>
			<th>所属应用：</th>
			<td >				
				<div style="border:1px solid #9DBFDD;width:303px;height:100px;overflow:auto;background:#FFFFFF;">
					<ul id="app_list" style="margin:10px">
					</ul>
				</div>
			</td>			
		</tr>
		<tr>
			<th>定义：</th>
			<td >
				<textarea id="class_define" name="class_define" style="width:300px;;height:50px;"></textarea>
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
